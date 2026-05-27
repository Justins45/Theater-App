package com.code.theaterapp.auth;

import com.code.theaterapp.auth.dtos.LoginRequestDTO;
import com.code.theaterapp.auth.dtos.LoginResponseDTO;
import com.code.theaterapp.auth.secruity.JwtService;
import com.code.theaterapp.shared.person.Person;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.staff.StaffRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwTservice;
    private final PersonRepo personRepo;
    private final StaffRepo staffRepo;

    @Value("${auth.token.name}")
    private String authTokenName;

    @Value("${remember.token.name}")
    private String rememberTokenName;

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