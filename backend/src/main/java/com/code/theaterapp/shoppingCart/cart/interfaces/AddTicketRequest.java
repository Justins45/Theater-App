package com.code.theaterapp.shoppingCart.cart.interfaces;

import java.util.UUID;

public record AddTicketRequest(
        UUID itemId
) implements AddCartItemRequest {
}
