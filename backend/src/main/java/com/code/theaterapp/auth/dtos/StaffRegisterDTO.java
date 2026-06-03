package com.code.theaterapp.auth.dtos;


import com.code.theaterapp.shared.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StaffRegisterDTO(
        @NotBlank
        @Size(min = 3, max = 50)
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username may only contain letters, numbers, underscores, and hyphens")
        String username,

        @NotBlank
        @Size(min = 8,max = 72)
        String password,

        @NotBlank
        @Size(max = 254)
        @Email
        String email,

        @NotBlank
        Role role
) {
}

