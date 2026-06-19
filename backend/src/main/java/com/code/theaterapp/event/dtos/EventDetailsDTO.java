package com.code.theaterapp.event.dtos;

import java.util.UUID;

public record EventDetailsDTO(
        UUID id,
        String title,
        String stageName,
        Integer capacity
) {
}
