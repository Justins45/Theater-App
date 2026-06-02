package com.code.theaterapp.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        // TODO: Change to email based login (removes Username - seperate branch + PR)

        // TODO: make branch for adding custom annotations

        @NotBlank
        @Size(min = 3, max = 50)
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username may only contain letters, numbers, underscores, and hyphens")
        String username,

        @NotBlank
        @Size(min = 8,max = 72)
        String password
) {
}
