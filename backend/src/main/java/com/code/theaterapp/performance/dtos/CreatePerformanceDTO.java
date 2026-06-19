package com.code.theaterapp.performance.dtos;

import java.time.LocalTime;
import java.util.UUID;

public record CreatePerformanceDTO(
        LocalTime showTime,
        UUID event_id
) {
}
