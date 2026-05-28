package com.code.theaterapp.stage.dtos;

public record CreateStageDTO(
        String name,
        Integer capacity,
        Long venueId
) {
}
