package com.code.theaterapp.tickets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByPatronId(Long patronId);
    Optional<Ticket> findByIdAndPatronId(Long ticketId, Long patronId);
}
