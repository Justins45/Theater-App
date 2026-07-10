package com.code.theaterapp.shoppingCart.cartItem.dtos;

import com.code.theaterapp.shared.enums.CartItemType;

import java.util.UUID;

public record CartItemAddItemDTO(
        CartItemType itemType,
        UUID itemId
) {
}
