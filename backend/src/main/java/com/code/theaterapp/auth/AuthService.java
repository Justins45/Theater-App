package com.code.theaterapp.auth;

import com.code.theaterapp.auth.dtos.LoginRequestDTO;
import com.code.theaterapp.auth.dtos.LoginResponseDTO;
import com.code.theaterapp.auth.secruity.JwtService;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.staff.StaffRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwTservice;
    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;

    @Qualifier("staffAuthManager")
    AuthenticationManager staffAuthManager;

    @Qualifier("patronAuthManager")
    AuthenticationManager patronAuthManager;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${auth.token.name}")
    private String authTokenName;

    @Value("${remember.token.name}")
    private String rememberTokenName;

    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO login, String userType) {

        AuthenticationManager manager = switch (userType) {
            case "STAFF"  -> staffAuthManager;
            case "PATRON" -> patronAuthManager;
            default -> throw new IllegalStateException("Unexpected value: " + userType);
        };

        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.email(),
                        login.password()
        ));

        log.debug("userType claim: '{}'", userType);
        log.debug("Attempting patron login for: '{}'", login.email());

        // Credentials passed — now verify they actually have profile
        Person person = personRepo.findByEmail(login.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        log.debug("Before Staff Check");

        // check for staff profile
        if ("STAFF".equals(userType)) {
            staffRepo.findByPerson(person)
                    .orElseThrow(() -> new AccessDeniedException("No staff profile for this user"));
        }

        log.debug("Passed staff check, proceeding with patron auth");

        ResponseCookie authCookie = jwTservice.generateToken(login.email(), userType, authTokenName);
        ResponseCookie rememberCookie = jwTservice.generateToken(login.email(), null, rememberTokenName);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                .header(HttpHeaders.SET_COOKIE, rememberCookie.toString())
                .build();
    }

}