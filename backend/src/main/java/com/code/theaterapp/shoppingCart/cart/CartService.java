package com.code.theaterapp.shoppingCart.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    // return CartSummaryDTO
    public String createCart() {
        // get person UUID + validate

        // get cart -> early exit

        // create cart

        return "";
    }

    // return CartDetailsDTO
    public String populateCart(UUID cartId) {
        // get cart

        // add each item to cart_items with cartId attached

        return "";
    }

    // return CartDetailsDTO
    public String getCart(UUID personId) {
        // get cart by personId

        // if not real -> 404

        // if not active -> 404

        // return cart

        return "";
    }
    
}
