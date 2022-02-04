package com.javi.uned.pfgbackend.adapters.api.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.pfgbackend.adapters.api.users.model.NewPasswordRequest;
import com.javi.uned.pfgbackend.adapters.api.users.model.TokenResponse;
import com.javi.uned.pfgbackend.adapters.api.users.model.UserDTO;
import com.javi.uned.pfgbackend.adapters.api.users.model.UserDTOTransformer;
import com.javi.uned.pfgbackend.adapters.filesystem.FileServiceImpl;
import com.javi.uned.pfgbackend.config.JWTAuthorizationFilter;
import com.javi.uned.pfgbackend.domain.exceptions.AuthException;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgbackend.domain.instrument.InstrumentService;
import com.javi.uned.pfgbackend.domain.ports.messagebroker.MessageBrokerGeneticComposer;
import com.javi.uned.pfgbackend.domain.sheet.SheetService;
import com.javi.uned.pfgbackend.domain.sheet.model.Sheet;
import com.javi.uned.pfgbackend.domain.user.CustomUserDetailsService;
import com.javi.uned.pfgbackend.domain.user.UserService;
import com.javi.uned.pfgbackend.domain.user.model.User;
import com.javi.uned.pfgcommons.model.Instrumento;
import com.javi.uned.pfgcommons.model.specs.GeneticSpecs;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Api(tags = "Users")
public class UserControllerImpl implements UserController {

    private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private SheetService sheetService;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private FileServiceImpl fileServiceImpl;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageBrokerGeneticComposer messageBrokerGeneticComposer;

    @Override
    public List<UserDTO> getUsers() {

        // Get users
        List<User> users = userService.findAll();

        // Transform to DTOs and return
        List<UserDTO> userDTOs = users.stream()
                .map(UserDTOTransformer::toTransferObject)
                .collect(Collectors.toList());
        return userDTOs;
    }

    public UserDTO getUser(Long id) throws EntityNotFound {

        User user = userService.findById(id);
        UserDTO userDTO = UserDTOTransformer.toTransferObject(user);
        return userDTO;

    }

    public TokenResponse generateToken(Long id, long duration, HttpServletRequest request) throws EntityNotFound, AuthException {

        // Check if requestor exists
        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter();
        Claims claims = jwtAuthorizationFilter.validateToken(request);
        Long requestorId = ((Integer) claims.get("id")).longValue();
        User user = userService.findById(requestorId);

        // Check if user is requesting his own token
        if (!requestorId.equals(id)) throw new AuthException("You cannot request a token from another user");

        // Generate token and return
        String token = userService.generateToken(id, duration);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
            return tokenResponse;

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) throws EntityNotFound {

        User user = UserDTOTransformer.toDomainObject(userDTO);
        user = userService.updateUser(userId, user);

        userDTO = UserDTOTransformer.toTransferObject(user);
        return userDTO;

    }

    @Override
    public UserDTO resetPassword(NewPasswordRequest newPasswordRequest, Long userId, HttpServletRequest request) throws Exception {

        //Reset password
        userService.resetPassword(
                userId,
                newPasswordRequest.getNewPassword());

        //Return user
        User user = userService.findById(userId);
        return UserDTOTransformer.toTransferObject(user);
    }

    @Override
    public UserDTO resetPassword(String token, NewPasswordRequest newPasswordRequest) {
        //TODO:
        return null;
    }


}
