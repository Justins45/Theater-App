package com.code.theaterapp.staff;

import com.code.theaterapp.auth.AuthService;
import com.code.theaterapp.auth.dtos.LoginRequestDTO;
import com.code.theaterapp.auth.dtos.LoginResponseDTO;
import com.code.theaterapp.auth.secruity.accounts.StaffAccount;
import com.code.theaterapp.staff.dtos.StaffMeResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Getter
@Setter
@RequestMapping(value = "/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final AuthService authService;

    @GetMapping
    public String getStaff() {
        return "Welcome to the Staff root path";
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return authService.login(request, "STAFF");
    }

    @GetMapping("/me")
    public StaffMeResponse me(@AuthenticationPrincipal StaffAccount account) {
        return staffService.getMe(account);
    }

}
