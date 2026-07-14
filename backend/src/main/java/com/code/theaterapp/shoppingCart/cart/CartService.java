package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.seating.event_seating.EventSeating;
import com.code.theaterapp.seating.event_seating.EventSeatingRepo;
import com.code.theaterapp.shared.enums.CartItemType;
import com.code.theaterapp.shared.enums.CartStatus;
import com.code.theaterapp.shared.enums.SeatStatus;
import com.code.theaterapp.shared.person.PersonRepo;
import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import com.code.theaterapp.shoppingCart.cart.dtos.AddCartItemRequest;
import com.code.theaterapp.shoppingCart.cart.dtos.AddMerchandiseRequest;
import com.code.theaterapp.shoppingCart.cart.dtos.AddTicketRequest;
import com.code.theaterapp.shoppingCart.cartItem.CartItem;
import com.code.theaterapp.shoppingCart.cartItem.CartItemMapper;
import com.code.theaterapp.shoppingCart.cartItem.CartItemRepo;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<CartItemSummaryDTO> addItem(UUID cartId, AddCartItemRequest item) {
        return switch (item) {
            case AddTicketRequest request -> addTicketToCart(cartId, request);
            case AddMerchandiseRequest request -> addMerchItemToCart(cartId, request);
        };
    }

    public ResponseEntity<CartItemSummaryDTO> addTicketToCart(UUID cartId, AddTicketRequest ticketRequest) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(
                () -> new EntityNotFoundException("Cart not found")
        );

        EventSeating eventSeating = eventSeatingRepo.findById(ticketRequest.itemId()).orElseThrow(
                () -> new EntityNotFoundException("Ticket not found")
        );

        if (eventSeating.getSeatStatus() != SeatStatus.AVAILABLE) {
            // TODO: make custom exception
            throw new RuntimeException("Seat is not available");
        }
        // TODO: UPDATE EVENT_SEATING SEAT TO BE HELD UNTIL SOLD

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItemType(CartItemType.TICKET);
        cartItem.setEventSeating(eventSeating);
        // TODO: get price from map NOT performance
        cartItem.setUnitPrice(eventSeating.getPerformance().getPrice());
        cartItem.setAddedAt(Instant.now());

        CartItem savedCartItem = cartItemRepo.save(cartItem);

        return ResponseEntity.ok(
                cartItemMapper.toSummary(savedCartItem)
        );
    }

    public ResponseEntity<CartItemSummaryDTO> addMerchItemToCart(UUID cartId, AddMerchandiseRequest merchandiseRequest) {
        // TODO: TEMP METHOD FOR OTHER STUFF BEFORE IT
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    // 204 - NO CONTENT (deleted success) | 404 NOT FOUND (deleted failed)
    public ResponseEntity<Void> removeItemFromCart(UUID cartId, UUID itemId) {
        return cartItemRepo.deleteByCartIdAndItemId(cartId, itemId) > 0
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
