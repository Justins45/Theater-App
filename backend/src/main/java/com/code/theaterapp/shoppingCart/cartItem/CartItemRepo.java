package com.code.theaterapp.shoppingCart.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepo extends JpaRepository<CartItem, UUID> {
    List<CartItem> findAllByCartId(UUID cartId);
}
