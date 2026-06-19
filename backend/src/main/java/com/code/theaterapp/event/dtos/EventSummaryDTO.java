package com.code.theaterapp.event.dtos;

import java.util.UUID;

public record EventSummaryDTO(
        UUID id,
        String title,
        Integer stageId,
        String stageName
) {
}
