package com.code.theaterapp.shoppingCart.cart.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record AddTicketRequest(
        @NotBlank 
        UUID itemId
) implements AddCartItemRequest {
}
