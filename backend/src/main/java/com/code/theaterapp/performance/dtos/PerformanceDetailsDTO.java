package com.code.theaterapp.performance.dtos;

import com.code.theaterapp.shared.enums.PerformanceStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record PerformanceDetailsDTO(
        UUID id,
        LocalDateTime showTime,
        PerformanceStatus status,
        UUID event_id
) {
}
