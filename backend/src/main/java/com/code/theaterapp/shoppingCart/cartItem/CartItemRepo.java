package com.code.theaterapp.shoppingCart.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, UUID> {
    List<CartItem> findAllByCartId(UUID cartId);

    @Transactional
    int deleteByCartIdAndItemId(UUID cartId, UUID itemId);
}
