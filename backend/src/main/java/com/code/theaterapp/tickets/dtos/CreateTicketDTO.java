package com.code.theaterapp.tickets.dtos;

import java.math.BigDecimal;

public record CreateTicketDTO(
        BigDecimal price,
        Long patronId,
        Long eventId,
        Long venueId

) {
}
