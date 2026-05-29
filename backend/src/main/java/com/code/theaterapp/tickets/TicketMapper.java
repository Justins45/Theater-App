package com.code.theaterapp.tickets;

import com.code.theaterapp.tickets.dtos.TicketDTO;

import java.util.function.Function;

public class TicketMapper implements Function<Ticket, TicketDTO> {
    @Override
    public TicketDTO apply(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getPrice(),
                ticket.getPatron(),
                ticket.getEvent(),
                ticket.getVenue(),
                ticket.getTicketStatus().toString()
        );
    }
}
