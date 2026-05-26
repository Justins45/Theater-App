package com.code.theaterapp.auth.secruity;

import com.code.theaterapp.patron.PatronDetailsService;
import com.code.theaterapp.staff.StaffDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

// TODO: Refactor into single purpose methods (not all in one method - OOD 🤮)
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTservice jwTservice;
    private final StaffDetailsService staffDetailsService;
    private final PatronDetailsService patronDetailsService;

    @Value("${auth.token.name}")
    private String authTokenName;

    @Value("${remember.token.name}")
    private String rememberTokenName;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        String authToken = jwTservice.getJWTfromCookie(request, authTokenName);
        String rememberToken = jwTservice.getJWTfromCookie(request, rememberTokenName);


        // early exit if no cookies exist
        if (authToken == null && rememberToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        /*
         * Check if the authToken exists and if it does not check the rememberToken for username
         */
        String username = null;
        if (authToken != null) {
            username = jwTservice.extractUsername(authToken);
        } else if (rememberToken != null && jwTservice.validateToken(rememberToken)) {
            username = jwTservice.extractUsername(rememberToken);
        }

        // Catch all if the rememberToken is invalid (no username from valid token saved)
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // set new authToken if authToken is within 10 minutes of expiring
        if (authToken != null && rememberToken != null && jwTservice.validateToken(rememberToken)) {
            Date now = new Date();
            Date authTokenExp = jwTservice.extractExpiration(authToken);
            long millisUntilExpiry = authTokenExp.getTime() - now.getTime();
            String userType = jwTservice.extractUserType(authToken);

            if (millisUntilExpiry < TimeUnit.MINUTES.toMillis(10)) {
                ResponseCookie newAuthCookie = jwTservice.generateToken(username, userType, authTokenName);
                response.addHeader(HttpHeaders.SET_COOKIE, newAuthCookie.toString());
                authToken = newAuthCookie.getValue();
            }
        }

        // set authentication for user
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            String userType = jwTservice.extractUserType(authToken);

            UserDetails userDetails;
            if ("STAFF".equals(userType)) {
                userDetails = staffDetailsService.loadUserByUsername(username);
            } else {
                userDetails = patronDetailsService.loadUserByUsername(username);
            }

            if (jwTservice.validateToken(authToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // proceed to subsequent filters
        filterChain.doFilter(request, response);
    }
}