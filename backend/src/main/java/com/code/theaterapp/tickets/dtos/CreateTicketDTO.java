package com.code.theaterapp.tickets.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateTicketDTO(
        BigDecimal price,
        UUID patronId,
        UUID performanceID
) {
}
