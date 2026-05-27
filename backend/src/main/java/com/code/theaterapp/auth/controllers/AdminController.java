package com.code.theaterapp.auth.controllers;

import com.code.theaterapp.auth.dtos.StaffRegisterDTO;
import com.code.theaterapp.staff.StaffService;
import com.code.theaterapp.staff.dtos.StaffDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
public class AdminController {

    private final StaffService staffService;

    @GetMapping("/")
    public String getAdmin() {
        return "Welcome to the admin root path";
    }

    @PostMapping("/register")
    public ResponseEntity<StaffDTO> register(@RequestBody StaffRegisterDTO request) {
        StaffDTO dto = staffService.createStaff(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}

