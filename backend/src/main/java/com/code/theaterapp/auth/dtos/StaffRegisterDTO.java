package com.code.theaterapp.auth.dtos;


import com.code.theaterapp.shared.enums.Role;

public record StaffRegisterDTO(
        String username,
        String email,
        String password,
        Role role
) {
}

