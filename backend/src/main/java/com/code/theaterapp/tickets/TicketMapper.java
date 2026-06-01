package com.code.theaterapp.tickets;

import com.code.theaterapp.tickets.dtos.TicketDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TicketMapper implements Function<Ticket, TicketDetailsDTO> {
    @Override
    public TicketDetailsDTO apply(Ticket ticket) {
        return new TicketDetailsDTO(
                ticket.getId(),
                ticket.getPrice(),
                ticket.getPatron().getId(),
                ticket.getEvent().getId(),
                ticket.getVenue().getId(),
                ticket.getTicketStatus()
        );
    }
}
