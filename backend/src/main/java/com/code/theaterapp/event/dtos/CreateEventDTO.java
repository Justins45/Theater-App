package com.code.theaterapp.event.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateEventDTO(
        @NotBlank 
        String title,
        
        @NotBlank 
        Integer stageId,
        
        @NotBlank 
        Integer venueId
) {
}
