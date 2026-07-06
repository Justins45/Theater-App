package com.code.theaterapp.shoppingCart.cart.dtos;

import java.util.UUID;

public record CartDetailsDTO(
        UUID id,
        UUID personId
) {
}
