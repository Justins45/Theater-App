package com.code.theaterapp.performance.dtos;

import com.code.theaterapp.shared.enums.PerformanceStatus;

import java.util.UUID;

public record PerformaceDetailsDTO(
        UUID id,
        String showTime,
        PerformanceStatus status,
        UUID event_id
) {
}
