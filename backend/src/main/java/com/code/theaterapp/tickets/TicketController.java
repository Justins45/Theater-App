package com.code.theaterapp.tickets;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/ticket")
public class TicketController {

    @GetMapping("/")
    public String getAllTickets() {
        return "Welcome to the ticket root path";
    }

    @GetMapping("/{id}")
    public String getTicket(@PathVariable("id") int id) {
        return "Welcome to Event with id: " + id;
    }
}
