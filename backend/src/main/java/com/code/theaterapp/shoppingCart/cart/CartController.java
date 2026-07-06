package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDetailsDTO> getCart(
            @AuthenticationPrincipal PatronAccount patronAccount
    ) {
        return ResponseEntity.ok(cartService.getOrCreateCart(patronAccount.getId()));
    }



}
