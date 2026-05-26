package com.code.theaterapp.auth.controllers;

import com.code.theaterapp.auth.AuthService;
import com.code.theaterapp.auth.dtos.LoginRequestDTO;
import com.code.theaterapp.auth.dtos.LoginResponseDTO;
import com.code.theaterapp.auth.dtos.PatronRegisterDTO;
import com.code.theaterapp.patron.PatronService;
import com.code.theaterapp.patron.dtos.PatronDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RootController {

    private final AuthService authService;
    private final PatronService patronService;

    @GetMapping("/")
    public String getRoot() {
        return "Welcome to the root path";
    }

    // TODO: move to another controller with other /auth stuff
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return authService.login(request, "PATRON");
    }

    @PostMapping("/register")
    public ResponseEntity<PatronDTO> register(@RequestBody PatronRegisterDTO request) {
        PatronDTO dto = patronService.createPatron(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}

