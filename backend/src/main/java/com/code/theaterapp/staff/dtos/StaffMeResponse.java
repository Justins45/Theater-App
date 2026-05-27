package com.code.theaterapp.staff.dtos;

import com.code.theaterapp.shared.enums.Role;

import java.time.OffsetDateTime;

public record StaffMeResponse(
        String username,
        String email,
        String firstName,
        String lastName,
        Role role,
        OffsetDateTime staffAccountCreation
) {
}
