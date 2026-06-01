package com.code.theaterapp.event.dtos;

import java.util.UUID;

public record EventDTO(
        UUID id,
        String title,
        String showTime,
        String stageName,
        Integer capacity
) {
}
