package com.code.theaterapp.shoppingCart.cartItems;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemsRepo extends JpaRepository<CartItems, UUID> {
    List<CartItems> findAllByCartId(UUID cartId);
}
