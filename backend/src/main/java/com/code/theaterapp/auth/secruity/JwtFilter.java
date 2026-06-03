package com.code.theaterapp.auth.secruity;

import com.code.theaterapp.patron.PatronDetailsService;
import com.code.theaterapp.staff.StaffDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JWT authentication filter that processes auth and remember-me tokens from cookies.
 *
 * <p>Token resolution order:
 * <ol>
 *   <li>If neither token is present, the request proceeds as anonymous.</li>
 *   <li>If the auth token is within 10 minutes of expiry and the remember token is valid,
 *       a new auth token is issued and written to the response.</li>
 *   <li>If the auth token is valid, the user is authenticated in the security context.</li>
 *   <li>If neither token is valid, the request moves to manual login again</li>
 * </ol>
 */

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwTservice;
    private final StaffDetailsService staffDetailsService;
    private final PatronDetailsService patronDetailsService;

    @Value("${auth.token.name}")
    private String authTokenName;

    @Value("${remember.token.name}")
    private String rememberTokenName;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        String authToken = jwTservice.getJWTfromCookie(request, authTokenName);
        String rememberToken = jwTservice.getJWTfromCookie(request, rememberTokenName);


        // early exit if no cookies exist
        if (authToken == null && rememberToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = getUsernameFromToken(authToken, rememberToken);
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        authToken = refreshTokenOrSetNew(authToken, rememberToken, username, response);

        setAuthentication(authToken, username, request);

        // proceed to subsequent filters
        filterChain.doFilter(request, response);
    }

    private String getUsernameFromToken(String authToken, String rememberToken) {
        try {
            if (authToken != null) {
                return jwTservice.extractUsername(authToken);
            } else if (jwTservice.validateToken(rememberToken)) {
                return jwTservice.extractUsername(rememberToken);
            }
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token, treating as unauthenticated: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Extract a username from whichever token is usable.
     * Returns null if neither token yields a valid username.
     */
    private UserDetails loadUserDetails(String username, String userType) {
        if ("STAFF".equals(userType)) {
            return staffDetailsService.loadUserByUsername(username);
        }
        return patronDetailsService.loadUserByUsername(username);
    }

    /**
     * If the auth token is within 10 minutes of expiry and a valid remember token exists,
     * issue a new auth token cookie and return its value. Otherwise, return the original.
     */
    private String refreshTokenOrSetNew(
            String authToken,
            String rememberToken,
            String username,
            HttpServletResponse response
    ) {

        if (authToken == null || rememberToken == null) return null;
        if (!jwTservice.validateToken(rememberToken)) return authToken;

        try {
            Date authTokenExp = jwTservice.extractExpiration(authToken);
            long millisUntilExpiry = authTokenExp.getTime() - new Date().getTime();
            String userType = jwTservice.extractUserType(authToken);

            if (millisUntilExpiry < TimeUnit.MINUTES.toMillis(10)) {
                ResponseCookie newAuthCookie = jwTservice.generateToken(username, userType, authTokenName);
                response.addHeader(HttpHeaders.SET_COOKIE, newAuthCookie.toString());
                return newAuthCookie.getValue();
            }
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT refresh attempt failed for user '{}': {}", username, e.getMessage());
        }

        return authToken;
    }

    /**
     * Populate the SecurityContext if it isn't already authenticated.
     */
    private void setAuthentication(
            String authToken,
            String username,
            HttpServletRequest request
    ) {
        if (authToken == null) return;
        if (SecurityContextHolder.getContext().getAuthentication() != null) return;

        try {
            String userType = jwTservice.extractUserType(authToken);
            UserDetails userDetails = loadUserDetails(username, userType);

            if (jwTservice.validateToken(authToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT auth context setup failed for user '{}' on {}: {}",
                    username, request.getRequestURI(), e.getMessage());
        }
    }
}