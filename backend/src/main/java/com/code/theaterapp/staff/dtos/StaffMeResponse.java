package com.code.theaterapp.staff.dtos;

import com.code.theaterapp.shared.enums.Role;

import java.time.OffsetDateTime;

public record StaffMeResponse(
        String email,
        String firstName,
        String lastName,
        String displayname,
        Role role,
        OffsetDateTime staffAccountCreation
) {
}
