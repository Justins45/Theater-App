package com.code.theaterapp.event.dtos;

public record CreateEventDTO(
        String title,
        Integer stageId,
        Integer venueId
) {
}
