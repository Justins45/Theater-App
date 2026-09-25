package com.code.theaterapp.event.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record CreateEventDTO(
        @NotBlank 
        String title,
        
        @Nullable 
        String description,
        
        @NotBlank 
        Integer stageId,
        
        @NotBlank 
        Integer venueId
) {
}
