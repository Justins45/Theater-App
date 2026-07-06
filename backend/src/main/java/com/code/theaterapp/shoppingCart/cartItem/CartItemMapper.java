package com.code.theaterapp.shoppingCart.cartItem;

import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemDetailsDTO toDetails(CartItem cartItem) {
        return new CartItemDetailsDTO(
                cartItem.getId(),
                cartItem.getCart().getId(),
                cartItem.getItemType(),
                cartItem.getEventSeating().getId(),
                cartItem.getUnitPrice()
        );
    }
}
