package com.code.theaterapp.event.dtos;

import java.time.LocalTime;

// TODO: add fields from Stage when added
public record EventDTO(
        String title,
        LocalTime showTime
) {
}
