package com.code.theaterapp.tickets.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record CreateTicketDTO(
        
        @NotBlank 
        @DecimalMin(value = "0.00")
        BigDecimal price,

        @NotBlank 
        UUID patronId,

        @NotBlank 
        UUID performanceID
) {
}
