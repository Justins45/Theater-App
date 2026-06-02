package com.code.theaterapp.stage.dtos;

public record StageSummaryDTO(
        String name,
        Integer capacity,
        Integer venueId
) {
}
