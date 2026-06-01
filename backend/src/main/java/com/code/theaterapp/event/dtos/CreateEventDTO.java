package com.code.theaterapp.event.dtos;

public record CreateEventDTO(
        String title,
        String showTime,
        Integer stageId,
        Integer venueId
) {
}
