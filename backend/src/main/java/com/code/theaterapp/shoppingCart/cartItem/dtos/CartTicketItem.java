package com.code.theaterapp.shoppingCart.cartItem.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record CartTicketItem(
    UUID id,
    String eventName,
    String stageName,
    String performanceTime,
    BigDecimal price,
    String row,
    String section,
    Short seatNumber
) implements CartItemDetailsDTO {
}