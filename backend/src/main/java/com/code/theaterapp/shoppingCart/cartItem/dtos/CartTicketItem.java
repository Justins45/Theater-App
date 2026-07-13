package com.code.theaterapp.shoppingCart.cartItem.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record CartTicketItem(
    UUID cartItemId,
    String eventName,
    String stageName,
    String performanceTime,
    BigDecimal price,
    String row,
    String Section,
    Short seatNumber
) implements CartItemDetailsDTO {
}