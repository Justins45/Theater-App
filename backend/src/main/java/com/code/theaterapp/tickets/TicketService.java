package com.code.theaterapp.tickets;

import com.code.theaterapp.event.Event;
import com.code.theaterapp.event.EventRepo;
import com.code.theaterapp.patron.Patron;
import com.code.theaterapp.patron.PatronRepo;
import com.code.theaterapp.shared.enums.TicketStatus;
import com.code.theaterapp.tickets.dtos.CreateTicketDTO;
import com.code.theaterapp.tickets.dtos.TicketDTO;
import com.code.theaterapp.venue.Venue;
import com.code.theaterapp.venue.VenueRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepo ticketRepo;
    private final TicketMapper ticketMapper;
    private final PatronRepo patronRepo;
    private final EventRepo eventRepo;
    private final VenueRepo venueRepo;

    public List<TicketDTO> getAllTickets(UUID patronId) {
        return ticketRepo.findAllByPatronId(patronId)
                .stream()
                .map(ticketMapper::apply)
                .toList();
    }

    public TicketDTO getByIdAndPatronId(UUID ticketId, UUID patronId) {
      Ticket ticket = ticketRepo.findByIdAndPatronId(ticketId, patronId)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron not found"));

      return ticketMapper.apply(ticket);
    }


    public TicketDTO createTicket(CreateTicketDTO createTicketDTO) {

        Patron patron = patronRepo.findById(createTicketDTO.patronId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patron not found"));

        Event event = eventRepo.findById(createTicketDTO.eventId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));

        Venue venue = venueRepo.findById(createTicketDTO.venueId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));



        Ticket ticket = new Ticket();
        ticket.setPrice(createTicketDTO.price());
        ticket.setPatron(patron);
        ticket.setVenue(venue);
        ticket.setEvent(event);
        ticket.setTicketStatus(TicketStatus.ISSUED);
        ticket.setCreatedAt(Instant.now());

        Ticket savedTicket = ticketRepo.save(ticket);
        return ticketMapper.apply(savedTicket);
    }



}
