package com.code.theaterapp.stage.dtos;

public record StageDTO(
        String name,
        Integer capacity,
        Long venueId
) {
}
