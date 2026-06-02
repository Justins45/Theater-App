package com.code.theaterapp.shared.person.dtos;

import com.code.theaterapp.shared.enums.Role;

public record PersonDetailsDTO(
        String username,
        String email,
        String firstname,
        String lastname,
        Role role
) {
}
