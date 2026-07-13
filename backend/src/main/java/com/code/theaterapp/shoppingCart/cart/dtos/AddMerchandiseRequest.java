package com.code.theaterapp.shoppingCart.cart.dtos;

import java.util.UUID;

public record AddMerchandiseRequest(
        UUID itemId,
        int quantity
) implements AddCartItemRequest {
}
