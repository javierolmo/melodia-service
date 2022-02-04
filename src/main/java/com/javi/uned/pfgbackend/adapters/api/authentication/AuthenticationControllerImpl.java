package com.javi.uned.pfgbackend.adapters.api.authentication;

import com.javi.uned.pfgbackend.adapters.api.authentication.model.LoginDTO;
import com.javi.uned.pfgbackend.adapters.api.authentication.model.LoginResponse;
import com.javi.uned.pfgbackend.adapters.api.authentication.model.LoginResponseData;
import com.javi.uned.pfgbackend.adapters.api.authentication.model.RegistrationRequest;
import com.javi.uned.pfgbackend.adapters.api.users.model.UserDTOTransformer;
import com.javi.uned.pfgbackend.config.WebSecurityConfig;
import com.javi.uned.pfgbackend.domain.user.TokenFactory;
import com.javi.uned.pfgbackend.domain.user.UserService;
import com.javi.uned.pfgbackend.domain.user.model.User;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Api(tags = "Authentication")
public class AuthenticationControllerImpl implements AuthenticationController {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationControllerImpl.class);

    @Autowired
    private WebSecurityConfig webSecurityConfig;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;


    //TODO: Ver este throws Exception. Es demasiado genérico
    public LoginResponse login(LoginDTO user) throws Exception {

        AuthenticationManager authenticationManager = webSecurityConfig.authenticationManagerBean();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        logger.info("Login successful: {}", user.getEmail());

        LoginResponse loginResponse = new LoginResponse();
        LoginResponseData data = new LoginResponseData();
        data.setToken(TokenFactory.authToken(authentication, userService.findByEmail(user.getEmail())));
        loginResponse.setData(data);

        return loginResponse;
    }

    public LoginResponse register(RegistrationRequest registrationRequest) throws Exception {

        User user = UserDTOTransformer.toDomainObject(registrationRequest);
        user = userService.registerUser(user);

        AuthenticationManager authenticationManager = webSecurityConfig.authenticationManagerBean();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registrationRequest.getEmail(), registrationRequest.getPassword()));
        String token = TokenFactory.authToken(authentication, userService.findById(user.getId()));

        LoginResponse loginResponse = new LoginResponse();
        LoginResponseData data = new LoginResponseData();
        data.setToken(token);
        loginResponse.setData(data);

        return loginResponse;

    }

}
