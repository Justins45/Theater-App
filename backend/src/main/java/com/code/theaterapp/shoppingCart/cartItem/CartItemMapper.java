package com.code.theaterapp.shoppingCart.cartItem;

import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemSummaryDTO toSummary(CartItem cartItem) {
        return new CartItemSummaryDTO(
                cartItem.getId(),
                cartItem.getItemType(),
                cartItem.getUnitPrice()
        );
    }

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
