package com.code.theaterapp.auth.controllers;

import com.code.theaterapp.auth.dtos.StaffRegisterConfirmationDTO;
import com.code.theaterapp.auth.dtos.StaffRegisterDTO;
import com.code.theaterapp.staff.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    private final StaffService staffService;

    @GetMapping
    public String getAdmin() {
        return "Welcome to the admin root path";
    }

    @PostMapping("/register-new")
    public ResponseEntity<StaffRegisterConfirmationDTO> registerNew(@RequestBody StaffRegisterDTO request) {
        StaffRegisterConfirmationDTO dto = staffService.createStaffNoAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/register-existing")
    public ResponseEntity<StaffRegisterConfirmationDTO> registerExisting(@RequestBody StaffRegisterDTO request) {
        StaffRegisterConfirmationDTO dto = staffService.createStaffExistingAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}

