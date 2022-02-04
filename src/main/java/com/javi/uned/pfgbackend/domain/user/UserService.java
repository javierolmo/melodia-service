package com.javi.uned.pfgbackend.domain.user;

import com.javi.uned.pfgbackend.config.WebSecurityConfig;
import com.javi.uned.pfgbackend.domain.exceptions.AuthException;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.exceptions.ExistingUserException;
import com.javi.uned.pfgbackend.domain.exceptions.ValidationException;
import com.javi.uned.pfgbackend.domain.ports.database.UserDAO;
import com.javi.uned.pfgbackend.domain.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements AuthenticationProvider {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private WebSecurityConfig webSecurityConfig;


    public User registerUser(User user) throws ValidationException, ExistingUserException {

        // Check user is valid
        checkValid(user);

        user = new User(
                user.getId(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getName(),
                user.getSurname(),
                true,
                user.getRoles()
        );

        // Save user
        user = userDAO.save(user);
        return user;

    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {

            // Unwrap authentication
            String email = authentication.getName();
            String password = authentication.getCredentials().toString();

            // Check credentials
            User user = userDAO.findByEmail(email);
            if (passwordEncoder.matches(password, user.getPassword())) {
                Set<GrantedAuthority> authorities = new HashSet<>();
                user.getRoles().forEach(role -> authorities.add(role));
                return new UsernamePasswordAuthenticationToken(email, password, authorities);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }

        } catch (EntityNotFound entityNotFound) {
            throw new UsernameNotFoundException(entityNotFound.getMessage());
        }

    }

    public User findByEmail(String email) throws EntityNotFound {
        return userDAO.findByEmail(email);
    }

    public User findById(Long id) throws EntityNotFound {
        return userDAO.findById(id);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void checkValid(User user) throws ValidationException, ExistingUserException {

        // Check email format
        if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Invalid email");
        }

        // Check password length
        if (user.getPassword().length() < 4) {
            throw new ValidationException("Invalid password. Must contain at least 4 characters");
        }

        // Check existing email user
        if (userDAO.existsByEmail(user.getEmail())) {
            throw new ExistingUserException("Email already registered!");
        }

    }

    public String generateToken(Long id, long duration) throws EntityNotFound {

        User tokenOwner = userDAO.findById(id);


        // Generate personal token
        String personalToken = TokenFactory.personalToken(tokenOwner, duration);
        logger.info("Token has been generated for user {}", tokenOwner.getEmail());
        return personalToken;

    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User updateUser(Long id, User userModified) throws EntityNotFound {

        User oldUser = findById(id);
        User newUser = new User(
                id,
                userModified.getEmail(),
                oldUser.getPassword(),
                userModified.getName(),
                userModified.getSurname(),
                userModified.getEnabled(),
                oldUser.getRoles());

        return userDAO.save(newUser);

    }

    /**
     *
     * @param idUsuario
     * @param oldPassword
     * @param newPassword
     * @throws Exception
     */
    public void changePassword(long idUsuario, String oldPassword, String newPassword) throws Exception {

        User user = userDAO.findById(idUsuario);

        try {
            AuthenticationManager authenticationManager = webSecurityConfig.authenticationManagerBean();
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            if(authentication.isAuthenticated()) throw new Exception();
        } catch (Exception e) {
            throw new AuthException("Invalid password for user " + user.getEmail());
        }

        user = new User(user.getId(), user.getEmail(), passwordEncoder.encode(newPassword), user.getName(), user.getSurname(), user.getEnabled(), user.getRoles());
        userDAO.save(user);

    }


    /**
     * INSECURE METHOD, use only for development purposes, changes the password of an user by id without any security
     * checks
     *
     * @param idUsuario id of the target user
     * @param newPassword new password for the user
     * @throws EntityNotFound if user does not exist
     */
    @Deprecated
    public void resetPassword(long idUsuario, String newPassword) throws EntityNotFound {

        User user = userDAO.findById(idUsuario);

        user = new User(user.getId(), user.getEmail(), passwordEncoder.encode(newPassword), user.getName(), user.getSurname(), user.getEnabled(), user.getRoles());
        userDAO.save(user);

    }
}
