package com.code.theaterapp.shoppingCart.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepo extends JpaRepository<Cart, UUID> {
    @Query("SELECT c FROM Cart c WHERE c.person.id = :personId AND c.status = com.code.theaterapp.shared" +
            ".enums.CartStatus.ACTIVE")
    Optional<Cart> findCartByPersonIdAndActive(@Param("personId") UUID personId);
}
