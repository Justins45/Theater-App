package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import com.code.theaterapp.shoppingCart.cart.dtos.CartSummaryDTO;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartSummaryDTO toSummary(Cart cart) {
        return new CartSummaryDTO(
                cart.getId(),
                cart.getPerson().getPersonId()
        );
    }

    public CartDetailsDTO toDetails(Cart cart, List<CartItemDetailsDTO> cartItems) {
        return new CartDetailsDTO(
                cart.getId(),
                cart.getPerson().getPersonId(),
                cartItems
        );
    }
}
