package com.code.theaterapp.shoppingCart.cart.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record AddMerchandiseRequest(
        @NotBlank 
        UUID itemId,
        
        @NotBlank 
        int quantity

) implements AddCartItemRequest {
}
