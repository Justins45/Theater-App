package com.code.theaterapp.shared.person;

import com.code.theaterapp.shared.enums.Role;

public record PersonDTO(
        String username,
        Role role
) {
}
