package com.code.theaterapp.event.dtos;

import java.time.LocalTime;
import java.util.UUID;

public record EventSummaryDTO(
        UUID id,
        String title,
        LocalTime showTime,
        UUID stageId,
        String stageName
) {
}
