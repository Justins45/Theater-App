package com.code.theaterapp.event.dtos;

public record EventDTO(
        Long id,
        String title,
        String showTime,
        String stageName,
        Integer capacity
) {
}
