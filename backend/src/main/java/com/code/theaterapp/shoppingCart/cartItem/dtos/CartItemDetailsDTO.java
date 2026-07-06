package com.code.theaterapp.shoppingCart.cartItem.dtos;

import com.code.theaterapp.shared.enums.CartItemType;

import java.util.UUID;

public record CartItemDetailsDTO(
        UUID id,
        UUID cartId,
        CartItemType cartItemType,
        UUID eventSeatingId,
        String unitPrice
) {
}
