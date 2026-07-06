package com.code.theaterapp.shoppingCart.cartItem.dtos;

import java.util.UUID;

public record CartItemSummaryDTO(
        UUID id,
        UUID cartId,
        String cartItemType,
        UUID eventSeatingId,
        String unitPrice
) {
}
