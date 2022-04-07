package com.javi.uned.pfgbackend.domain.user;

import com.javi.uned.pfgbackend.domain.user.model.Role;
import com.javi.uned.pfgbackend.domain.user.model.User;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TokenFactoryTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void authToken() {
        // TODO
    }

    @Test
    void personalToken() {
        List<Role> roles = Arrays.asList(new Role(1L, "ROLE_1"), new Role(2L, "ROLE_2"));
        User user = new User(1L, "email", "password", "name", "surname", true, roles);
        long duration = 60000L;

        String token = TokenFactory.personalToken(user, duration);

        String[] parts = token.split("\\.");
        try {
            //JSONObject header = new JSONObject(new String(Base64.getUrlDecoder().decode(parts[0])));
            JSONObject payload = new JSONObject(new String(Base64.getUrlDecoder().decode(parts[1])));
            String signature = new String(Base64.getUrlDecoder().decode(parts[2]));

            assertEquals(payload.getLong("id"), user.getId());
            assertTrue(payload.getLong("exp") - payload.getLong("iat") <= duration);
            assertEquals(
                    payload.get("authorities"),
                    roles.stream().map(role -> role.getAuthority()).collect(Collectors.joining(","))
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}