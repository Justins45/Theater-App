package com.code.theaterapp.staff.dtos;

import com.code.theaterapp.shared.enums.Role;

import java.util.UUID;

public record StaffSummaryDTO(
        UUID id,
        String firstName,
        String lastName,
        String displayname,
        Role role
) {
}
