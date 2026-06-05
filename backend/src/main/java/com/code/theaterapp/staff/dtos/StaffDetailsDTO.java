package com.code.theaterapp.staff.dtos;


import com.code.theaterapp.shared.enums.Role;

public record StaffDetailsDTO(
        String email,
        String firstName,
        String lastName,
        String displayname,
        Role role
) {
}
