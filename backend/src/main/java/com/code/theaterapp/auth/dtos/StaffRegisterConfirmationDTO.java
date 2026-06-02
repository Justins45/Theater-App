package com.code.theaterapp.auth.dtos;

import com.code.theaterapp.shared.enums.Role;

public record StaffRegisterConfirmationDTO(
        String username,
        String email,
        Role role
) {
}
