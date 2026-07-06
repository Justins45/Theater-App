package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import com.code.theaterapp.shoppingCart.cartItem.CartItemMapper;
import com.code.theaterapp.shoppingCart.cartItem.CartItemRepo;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

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
    public CartDetailsDTO getCart(@AuthenticationPrincipal PatronAccount patronAccount) {
        Cart cart = cartRepo.findCartByPersonIdAndActive(patronAccount.getId()).orElseThrow(
                () -> new EntityNotFoundException("No Carts found")
        );

        List<CartItemDetailsDTO> cartItems = cartItemRepo.findAllByCartId(cart.getId())
                .stream()
                .map(cartItemMapper::toDetails)
                .toList();

        return cartMapper.toDetails(cart, cartItems);
    }

}
