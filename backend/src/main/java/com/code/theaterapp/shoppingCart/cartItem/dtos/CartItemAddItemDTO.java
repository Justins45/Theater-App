package com.code.theaterapp.shoppingCart.cartItem.dtos;

import com.code.theaterapp.shared.enums.CartItemType;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CartItemAddItemDTO(
        @NotBlank 
        CartItemType itemType,
        
        @NotBlank 
        UUID itemId
) {
}
