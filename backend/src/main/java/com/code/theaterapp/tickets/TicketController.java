package com.code.theaterapp.tickets;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.tickets.dtos.TicketDetailsDTO;
import com.code.theaterapp.tickets.dtos.TicketSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tickets")
public class TicketController {


    private final TicketService ticketService;

    // Show users tickets
    @GetMapping
    public ResponseEntity<List<TicketSummaryDTO>> getAllTickets(@AuthenticationPrincipal PatronAccount patronAccount) {
        return ResponseEntity.ok(ticketService.getAllTickets(patronAccount.getId()));
    }

    // show users ticket information on the web
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDetailsDTO> getTicket(
            @AuthenticationPrincipal PatronAccount patronAccount,
            @PathVariable("ticketId") UUID ticketId ) {
        return ResponseEntity.ok(ticketService.getByIdAndPatronId(ticketId, patronAccount.getId()));
    }
}
