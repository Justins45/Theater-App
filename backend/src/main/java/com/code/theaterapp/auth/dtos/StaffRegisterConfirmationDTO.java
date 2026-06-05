package com.code.theaterapp.auth.dtos;

import com.code.theaterapp.shared.enums.Role;

public record StaffRegisterConfirmationDTO(
        String email,
        Role role
) {
}
