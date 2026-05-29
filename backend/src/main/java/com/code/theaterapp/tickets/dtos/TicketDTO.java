package com.code.theaterapp.tickets.dtos;

import com.code.theaterapp.event.Event;
import com.code.theaterapp.patron.Patron;
import com.code.theaterapp.venue.Venue;

import java.math.BigDecimal;

public record TicketDTO(
        Long id,
        BigDecimal price,
        Patron patron,
        Event event,
        Venue venue,
        String ticketStatus
) {
}
