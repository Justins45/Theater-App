package com.code.theaterapp.tickets.dtos;

import com.code.theaterapp.shared.enums.TicketStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record TicketSummaryDTO(
        UUID id,
        BigDecimal price,
        UUID patronId,
        UUID eventId,
        Integer venueId,
        TicketStatus ticketStatus
) {
}
