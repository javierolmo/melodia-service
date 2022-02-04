package com.javi.uned.pfgbackend.config;

import com.javi.uned.pfgbackend.domain.exceptions.AuthException;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "jUgnlLgD";
    public static final String SECRET = "gjN12sSF";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {

            Claims claims = validateToken(request);

            if (claims.get("authorities") != null) {
                setUpSpringAuthentication(claims);
            } else {
                SecurityContextHolder.clearContext();
            }

            chain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (AuthException authException) {
            SecurityContextHolder.clearContext();
            chain.doFilter(request, response);
        }
    }

    /**
     * Validates JWT token obtaining it's claims
     * @param request http request with token in header
     * @return claims
     * @throws AuthException
     */
    public Claims validateToken(HttpServletRequest request) throws AuthException {
        try {

            // Check header
            String authenticationHeader = request.getHeader(HEADER);
            if(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
                throw new IOException("Error in checkJWTToken(request)");
            }

            // Parse token
            String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
            Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
            claims.getId();

            return claims;

        } catch (ExpiredJwtException expiredJwtException) {
            throw new AuthException("Token has expired! Please, log in again");
        } catch (Exception e) {
            throw new AuthException("Cannot parse JWT Token", e);
        }

    }

    /**
     * Authentication method in Spring flow
     *
     * @param claims
     */
    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        String authorities = claims.get("authorities", String.class);
        Set<GrantedAuthority> grantedAuthorities = Arrays.stream(authorities.split(","))
                .filter(authority -> authority.length() > 0)
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toSet());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

}
