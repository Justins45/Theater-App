package com.code.theaterapp.shared.person.dtos;

import com.code.theaterapp.shared.enums.Role;

public record PersonDetailsDTO(
        String email,
        String firstname,
        String lastname,
        String displayname,
        Role role
) {
}
