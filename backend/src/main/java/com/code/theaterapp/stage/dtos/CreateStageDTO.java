package com.code.theaterapp.stage.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CreateStageDTO(
        @NotBlank 
        String name,

        @Nullable 
        Integer capacity,

        @NotBlank 
        Integer venueId
) {
}
