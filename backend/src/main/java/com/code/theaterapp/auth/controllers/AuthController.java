package com.code.theaterapp.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.theaterapp.auth.AuthService;
import com.code.theaterapp.auth.dtos.LoginRequestDTO;
import com.code.theaterapp.auth.dtos.LoginResponseDTO;
import com.code.theaterapp.auth.dtos.PatronRegisterConfirmationDTO;
import com.code.theaterapp.auth.dtos.PatronRegisterDTO;
import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.patron.PatronService;
import com.code.theaterapp.patron.dtos.PatronMeResponse;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;
    private final PatronService patronService;

    @GetMapping("/me")
    public ResponseEntity<PatronMeResponse> me(@AuthenticationPrincipal PatronAccount account) {
        return patronService.getMe(account);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return authService.login(request, "PATRON");
    }

    @PostMapping("/register")
    public ResponseEntity<PatronRegisterConfirmationDTO> register(@RequestBody PatronRegisterDTO request) {
        PatronRegisterConfirmationDTO dto = patronService.createPatron(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // Controller
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {  // Void, HttpServletResponse
        ResponseCookie cookie = authService.logout();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());  
        return ResponseEntity.noContent().build();  
}
}
