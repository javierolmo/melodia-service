package com.javi.uned.pfgbackend.adapters.api.users;

import com.javi.uned.pfgbackend.adapters.api.users.model.NewPasswordRequest;
import com.javi.uned.pfgbackend.adapters.api.users.model.TokenResponse;
import com.javi.uned.pfgbackend.adapters.api.users.model.UserDTO;
import com.javi.uned.pfgbackend.domain.exceptions.AuthException;
import com.javi.uned.pfgbackend.domain.exceptions.EntityNotFound;
import com.javi.uned.pfgcommons.model.specs.GeneticSpecs;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface UserController {

    /**
     * TODO:
     * @return
     */
    @GetMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDTO> getUsers();

    /**
     * TODO:
     * @param id
     * @return
     */
    @GetMapping(value = "/api/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO getUser(@PathVariable Long id) throws EntityNotFound;

    /**
     * Generate a token for specified user. Only for own token
     *
     * @param id       identifier of user
     * @param duration token duration in millis (default 30 days)
     * @param request  complete request to retrieve headers
     * @return valid token for user
     */
    @PreAuthorize("true")
    @GetMapping(value = "/api/users/{id}/token", produces = MediaType.APPLICATION_JSON_VALUE)
    TokenResponse generateToken(
            @PathVariable Long id,
            @RequestParam(defaultValue = "2592000000", required = false) long duration,
            HttpServletRequest request) throws EntityNotFound, AuthException;

    @PutMapping(value = "/api/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO updateUser(@RequestBody UserDTO userDTO, @PathVariable Long userId) throws EntityNotFound;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/api/users/{userId}/reset-password")
    UserDTO resetPassword(
            @RequestBody NewPasswordRequest newPasswordRequest,
            @PathVariable Long userId,
            HttpServletRequest request) throws Exception;

    @PostMapping(value = "/api/users/reset-password/{token}")
    UserDTO resetPassword(@PathVariable String token, @RequestBody NewPasswordRequest newPasswordRequest);

}
