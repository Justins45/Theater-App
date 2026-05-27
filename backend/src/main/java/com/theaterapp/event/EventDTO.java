package com.theaterapp.event;

import java.time.LocalDateTime;

public record EventDTO(
        String title,
        LocalDateTime dateTime
) {
}
