package com.code.theaterapp.performance.dtos;

import com.code.theaterapp.shared.enums.PerformanceStatus;

import java.util.UUID;

public record PerformanceInformationDTO(
        UUID id,
        String eventName,
        String stageName,
        String performanceTime,
        PerformanceStatus performanceStatus
) {
}
