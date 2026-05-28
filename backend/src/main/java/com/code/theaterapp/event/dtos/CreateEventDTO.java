package com.code.theaterapp.event.dtos;

public record CreateEventDTO(
        String title,
        String showTime,
        Long stageId,
        Long venueId
) {
}
