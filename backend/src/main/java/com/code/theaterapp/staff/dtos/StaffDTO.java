package com.code.theaterapp.staff.dtos;


import com.code.theaterapp.shared.enums.Role;

public record StaffDTO(
        String username,
        String email,
        String firstName,
        String lastName,
        Role role
) {
}
