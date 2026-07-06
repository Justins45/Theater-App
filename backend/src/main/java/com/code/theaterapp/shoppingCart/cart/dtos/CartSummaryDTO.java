package com.code.theaterapp.shoppingCart.cart.dtos;

import java.util.UUID;

public record CartSummaryDTO(
        UUID id,
        UUID personId
) {
}
