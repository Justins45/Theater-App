package com.code.theaterapp.auth.dtos;


import com.code.theaterapp.shared.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StaffRegisterDTO(
        @NotBlank
        @Size(max = 254)
        @Email
        String email,

        @NotBlank
        @Size(min = 8,max = 72)
        String password,

        @NotBlank
        Role role
) {
}

