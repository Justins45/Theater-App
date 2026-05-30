package com.code.theaterapp.tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, UUID> {

    List<Ticket> findAllByPatronId(Long patronId);
    Optional<Ticket> findByIdAndPatronId(UUID ticketId, Long patronId);
}
