package com.code.theaterapp.auth.secruity;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.secret.auth.key}")
    private String secreteKey;

    @Value("${remember.token.name}")
    private String rememberTokenName;

    private final String BASE_URL = "http://localhost:8080";

    /**
     * Generates a signed JWT and packages it as a {@link ResponseCookie}.
     * Defaults to a 1-hour token unless the {@link #rememberTokenName} cookie is requested, which extends to 24 hours.
     *
     * <ul>
     *   <li>{@code from} — cookie name and its JWT value</li>
     *   <li>{@code httpOnly} — prevents JavaScript access, reducing XSS risk</li>
     *   <li>{@code secure} — {@code false} for local HTTP development; <b>must be {@code true} in production (HTTPS)</b></li>
     *   <li>{@code maxAge} — <u>must be in seconds</u>: {@code hours * 60 (mins) * 60 (secs)}</li>
     *   <li>{@code sameSite} — {@code "Strict"} prevents the cookie being sent on cross-site requests; localhost counts as same-site</li>
     *   <li>{@code path} — {@code "/"} makes the cookie available across the whole domain</li>
     * </ul>
     *
     * @param username  the subject to encode into the JWT
     * @param tokenName the cookie name; determines token duration
     * @return a {@link ResponseCookie} containing the signed JWT
     */
    public ResponseCookie generateToken(String username, String userType, String tokenName) {

        // Durations are in HOURS — tokenDuration controls JWT expiry, cookieMaxAge controls browser lifetime
        // Defaults to 1 hour; rememberMe extends both to 24 hours
        // Adjust durations or units independently if cookie/token requirements change
        int tokenDuration = 1;
        int cookieMaxAge = 1;

        if (Objects.equals(tokenName, rememberTokenName)) {
            tokenDuration = 24;
            cookieMaxAge = 24;
        }


        JwtBuilder builder = Jwts.builder()
                .subject(username)
                .issuer(BASE_URL)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(tokenDuration)))
                .signWith(getKey());

        if (userType != null) {
            builder.claim("userType", userType);
        }
        String jwtToken = builder.compact();

        return ResponseCookie
                .from(tokenName, jwtToken)
                .httpOnly(true)
                .secure(false)
                .maxAge(cookieMaxAge * 60 * 60)
                .sameSite("Strict")
                .path("/")
                .build();
    }


    /**
     * <p>Takes in the request and the cookie name to find, and returns out the JWT as a string associated with the
     * cookie</p>
     *
     * @param cookieName Cookie name to find
     * @return {@link String} JWT from the found cookie
     */
    public String getJWTfromCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;

        return Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    /*
     * NOTE: Add token - Token revocation / blocklist - when needed
     *      also Audience (aud) validation when frontend end backend come into play
     */

    /**
     * Validates a JWT token
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token passes all currently implemented checks
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Silly extra check for places where username is added (will be fixed with Token only validate)
     */
    public boolean validateToken(String token, String username) {
        return (username != null && !isTokenExpired(token));
    }

    /**
     * Decodes the Base64 secret key into a {@link SecretKey} for signing and verification.
     */
    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    /**
     * Extracts the username (subject) from a JWT token.
     *
     * @param token the JWT token
     * @return the username stored in the token's subject claim
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * @return {@code true} if the token's expiration date is in the past
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token the JWT token
     * @return the {@link Date} the token expires
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public String extractUserType(String token) {
        return extractClaim(token, claims -> claims.get("userType", String.class));
    }

    /**
     * Extracts a specific claim from a JWT token using the provided resolver function.
     *
     * @param token          the JWT token to extract from
     * @param claimsResolver a function that maps {@link Claims} to the desired value (e.g. {@code Claims::getSubject})
     * @return the extracted claim value of type {@code T}
     */
    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses and validates the JWT token, verifying its signature, issuer, and
     * required claims, then returns the contained {@link Claims}.
     *
     * @param token the signed JWT token to parse
     * @return the {@link Claims} payload from the verified token
     * @throws JwtException if the token is invalid or missing required claims
     */
    private Claims extractAllClaims(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getKey())
                .requireIssuer(BASE_URL)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (claims.getSubject() == null || claims.getExpiration() == null || claims.getIssuer() == null) {
            throw new JwtException("Token is missing required claims");
        }

        return claims;
    }
}

