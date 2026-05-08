package com.theaterapp.secruity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secrete}")
    private String jwt_secrete;

    @Value("${jwt.expiration}")
    private int jwt_expiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key =
                Keys.hmacShaKeyFor(jwt_secrete.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates token for a user based on username, with current time +
     * expiration time
     * @param email String
     * @return JWT Token
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date((new Date().getTime() + jwt_expiration)))
                .signWith(key)
                .compact();
    }

    /**
     * Get user information from a JWT token,
     * @param token JWT Token
     * @return User info in token
     */
    public String getUserFromToken(String token) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Check if token given is valid
     * @param token JWT Token
     * @return true/false based on if token is valid
     */
    public boolean validateJWTtoken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("JWT validation error: {}", e.getMessage());
        }
        return false;
    }
}
