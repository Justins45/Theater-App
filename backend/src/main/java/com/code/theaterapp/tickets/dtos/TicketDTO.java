package com.code.theaterapp.tickets.dtos;

import com.code.theaterapp.shared.enums.TicketStatus;

import java.math.BigDecimal;

public record TicketDTO(
        Long id,
        BigDecimal price,
        Long patronId,
        Long eventId,
        Integer venueId,
        TicketStatus ticketStatus
) {
}
