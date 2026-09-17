package com.code.theaterapp.shoppingCart.cartItem.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CartItemDelete(
        @NotBlank 
        UUID itemId
) {
}
