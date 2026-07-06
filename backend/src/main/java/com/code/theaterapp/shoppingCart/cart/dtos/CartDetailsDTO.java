package com.code.theaterapp.shoppingCart.cart.dtos;

import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;

import java.util.List;
import java.util.UUID;

public record CartDetailsDTO(
        UUID id,
        UUID personId,
        List<CartItemDetailsDTO> cartItems
) {
}
