package com.code.theaterapp.auth.controllers;

import com.code.theaterapp.auth.AuthService;
import com.code.theaterapp.auth.dtos.LoginRequestDTO;
import com.code.theaterapp.auth.dtos.LoginResponseDTO;
import com.code.theaterapp.auth.dtos.PatronRegisterDTO;
import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.patron.PatronService;
import com.code.theaterapp.patron.dtos.PatronDetailsDTO;
import com.code.theaterapp.patron.dtos.PatronMeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;
    private final PatronService patronService;

    @GetMapping("/me")
    public PatronMeResponse me(@AuthenticationPrincipal PatronAccount account) {
        return patronService.getMe(account);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return authService.login(request, "PATRON");
    }

    @PostMapping("/register")
    public ResponseEntity<PatronDetailsDTO> register(@RequestBody PatronRegisterDTO request) {
        PatronDetailsDTO dto = patronService.createPatron(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
