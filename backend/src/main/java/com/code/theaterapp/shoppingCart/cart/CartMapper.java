package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartDetailsDTO toDetails(Cart cart) {
        return new CartDetailsDTO(
                cart.getId(),
                cart.getPerson().getPersonId()
        );
    }
}
