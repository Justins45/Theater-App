package com.code.theaterapp.shoppingCart.cart.dtos;

import java.util.UUID;

public record AddTicketRequest(
        UUID itemId
) implements AddCartItemRequest {
}
