package com.code.theaterapp.shoppingCart.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {


    /**
     * "cartId",
     * "PersonID",
     * "items" : [
     *   { asdasdasd },
     *   { ... }
     * ]
     */
    public String createCart() {
        // get Patrons Person UUID + validate

        // check if no active cart exists
        // else grab that cart + return

        // create cart

        return "";
    }

    public String populateCart(UUID cartId) {
        // find cart + validate its real

        // add each item to cart_items with cartId attached

        return "";
    }




}
