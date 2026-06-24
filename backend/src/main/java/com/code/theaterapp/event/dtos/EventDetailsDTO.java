package com.code.theaterapp.event.dtos;

import com.code.theaterapp.performance.dtos.PerformanceSummaryDTO;

import java.util.List;
import java.util.UUID;

public record EventDetailsDTO(
        UUID id,
        String title,
        String stageName,
        Integer capacity,
        List<PerformanceSummaryDTO> performances
) {
}
