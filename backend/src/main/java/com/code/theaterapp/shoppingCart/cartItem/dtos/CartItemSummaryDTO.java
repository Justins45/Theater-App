package com.code.theaterapp.shoppingCart.cartItem.dtos;

import com.code.theaterapp.shared.enums.CartItemType;

import java.util.UUID;

public record CartItemSummaryDTO(
        UUID id,
        CartItemType itemType,
        String price
) {
}
