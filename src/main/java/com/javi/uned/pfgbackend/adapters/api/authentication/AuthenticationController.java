package com.javi.uned.pfgbackend.adapters.api.authentication;

import com.javi.uned.pfgbackend.adapters.api.authentication.model.LoginDTO;
import com.javi.uned.pfgbackend.adapters.api.authentication.model.LoginResponse;
import com.javi.uned.pfgbackend.adapters.api.authentication.model.RegistrationRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationController {

    /**
     * Logs in the system
     * @param login object containing user and password
     * @return token
     */
    @PostMapping(value= "/api/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    LoginResponse login(@RequestBody LoginDTO login) throws Exception;

    @PostMapping(value= "/api/auth/register", produces = MediaType.APPLICATION_JSON_VALUE)
    LoginResponse register(@RequestBody RegistrationRequest registrationRequest) throws Exception;
}
