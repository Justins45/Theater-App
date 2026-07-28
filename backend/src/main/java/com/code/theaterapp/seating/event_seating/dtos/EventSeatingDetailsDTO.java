package com.code.theaterapp.seating.event_seating.dtos;

import com.code.theaterapp.pricing.dto.PricingRulePrice;
import com.code.theaterapp.shared.enums.SeatStatus;

import java.time.Instant;
import java.util.UUID;

public record EventSeatingDetailsDTO(
        UUID id,
        SeatStatus seatStatus,
        UUID performanceId,
        Instant holdExpiry,
        Integer seatId,
        String row,
        Short seatNumber,
        String section,
        String uiIdentifier,
        PricingRulePrice price,
        Integer stageId
) {
}
