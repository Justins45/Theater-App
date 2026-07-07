package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.seating.event_seating.EventSeatingRepo;
import com.code.theaterapp.shared.enums.CartStatus;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import com.code.theaterapp.shoppingCart.cartItem.CartItem;
import com.code.theaterapp.shoppingCart.cartItem.CartItemMapper;
import com.code.theaterapp.shoppingCart.cartItem.CartItemRepo;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final PersonRepo personRepo;
    private final EventSeatingRepo eventSeatingRepo;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;

    public CartDetailsDTO getOrCreateCart(UUID personId) {
        return cartRepo.findCartByPersonIdAndActive(personId)
                .map(cart -> {
                    List<CartItemDetailsDTO> cartItems = cartItemRepo.findAllByCartId(cart.getId())
                            .stream()
                            .map(cartItemMapper::toDetails)
                            .toList();
                    return cartMapper.toDetails(cart, cartItems);
                })
                .orElseGet(() -> createCart(personId));
    }

    private CartDetailsDTO createCart(UUID personId) {
        Cart cart = new Cart();
        cart.setPerson(personRepo.getReferenceById(personId));
        cart.setCartCreated(Instant.now());
        cart.setStatus(CartStatus.ACTIVE);

        Cart saved = cartRepo.save(cart);
        // Empty list as cart is created empty
        return cartMapper.toDetails(saved, List.of());
    }

    public CartDetailsDTO populateCart(UUID cartId, List<CartItemDetailsDTO> items) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(
                () -> new EntityNotFoundException("Cart not found")
        );

        List<CartItem> cartItems = items.stream()
                .map(item -> {
                    CartItem ci = new CartItem();
                    ci.setCart(cart);
                    ci.setItemType(item.cartItemType());
                    ci.setEventSeating(
                            eventSeatingRepo.findById(item.eventSeatingId())
                                    .orElse(null)
                    );
                    ci.setUnitPrice(item.unitPrice());
                    return ci;
                }).toList();

        List<CartItem> savedCartItems = cartItemRepo.saveAll(cartItems);
        return cartMapper.toDetails(
                cart,
                savedCartItems.stream()
                        .map(cartItemMapper::toDetails)
                        .toList());
    }
}
