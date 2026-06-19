package com.code.theaterapp.tickets;

import com.code.theaterapp.tickets.dtos.TicketDetailsDTO;
import com.code.theaterapp.tickets.dtos.TicketSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketDetailsDTO toDetails(Ticket ticket) {
        return new TicketDetailsDTO(
                ticket.getId(),
                ticket.getPrice(),
                ticket.getPatron().getId(),
                ticket.getPerformance().getId(),
                ticket.getTicketStatus()
        );
    }

    public TicketSummaryDTO toSummary(Ticket ticket) {
        return new TicketSummaryDTO(
                ticket.getId(),
                ticket.getPrice(),
                ticket.getPatron().getId(),
                ticket.getPerformance().getId(),
                ticket.getTicketStatus()
        );
    }

}
