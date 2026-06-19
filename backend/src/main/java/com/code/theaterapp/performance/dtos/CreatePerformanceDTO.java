package com.code.theaterapp.performance.dtos;

import java.time.LocalTime;

public record CreatePerformanceDTO(
        LocalTime showTime
) {
}
