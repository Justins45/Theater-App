package com.code.theaterapp.auth;

import com.code.theaterapp.auth.dtos.LoginRequestDTO;
import com.code.theaterapp.auth.dtos.LoginResponseDTO;
import com.code.theaterapp.auth.secruity.JWTservice;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.staff.StaffRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTservice jwTservice;
    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;

    @Value("${auth.token.auth.name}")
    private String authTokenName;

    @Value("${remember.token.remember.name}")
    private String rememberTokenName;

    /**
     * Authenticates a user and returns their profile with session cookies.
     * <ul>
     *   <li>Validates credentials via {@link AuthenticationManager}</li>
     *   <li>Generates a short-lived auth token and a remember-me token as {@link ResponseCookie}s</li>
     *   <li>Returns the authenticated user's DTO in the response body</li>
     * </ul>
     *
     * @param user the login request containing username and password
     * @return a {@link ResponseEntity} containing the {@link UsersDTO} with auth cookies set in headers
     * @throws ResponseStatusException with {@link HttpStatus#UNAUTHORIZED} if the user is not found
     */
    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO login, String userType) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.username(),
                        login.password()
                )
        );

        // Credentials passed — now verify they actually have profile
        Person person = personRepo.findByUsername(login.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // check for staff profile
        if ("STAFF".equals(userType)) {
            staffRepo.findByPerson(person)
                    .orElseThrow(() -> new AccessDeniedException("No staff profile for this user"));

        }

        ResponseCookie authCookie = jwTservice.generateToken(login.username(), userType, authTokenName);
        ResponseCookie rememberCookie = jwTservice.generateToken(login.username(), null, rememberTokenName);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .header(HttpHeaders.SET_COOKIE, rememberCookie.toString())
                .build();
    }

}