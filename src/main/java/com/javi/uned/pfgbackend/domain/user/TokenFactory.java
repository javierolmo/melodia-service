package com.javi.uned.pfgbackend.domain.user;

import com.javi.uned.pfgbackend.config.JWTAuthorizationFilter;
import com.javi.uned.pfgbackend.domain.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TokenFactory {

    private TokenFactory() {

    }

    public static String authToken(Authentication authentication, User user) {

        String tokenId = "auth-token";

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("surname", user.getSurname());
        claims.put("email", user.getEmail());

        long duration = 600000;

        return generateToken(user, tokenId, claims, duration);
    }

    public static String personalToken(User user, long duration) {

        String tokenId = "personal-token";

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());

        return generateToken(user, tokenId, claims, duration);


    }

    private static String generateToken(User user, String tokenId, Map<String, Object> claims, long duration) {

        String authorities = user.getRoles().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setId(tokenId)
                .setClaims(claims)
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(SignatureAlgorithm.HS512, JWTAuthorizationFilter.SECRET.getBytes())
                .compact();

        return JWTAuthorizationFilter.PREFIX + token;
    }
}
