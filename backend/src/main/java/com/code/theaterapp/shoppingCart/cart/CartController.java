package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import com.code.theaterapp.shoppingCart.cart.dtos.AddMerchandiseRequest;
import com.code.theaterapp.shoppingCart.cart.dtos.AddTicketRequest;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemAddItemDTO;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDelete;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDetailsDTO> getCart(
            @AuthenticationPrincipal PatronAccount patronAccount
    ) {

        if (patronAccount == null) {
            // used as temp for frontend before a login check is implemented there
            return ResponseEntity.ok(null);
            // TODO: use then check when frontend has a login checkpoint
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(cartService.getOrCreateCart(patronAccount.getId()));
    }

    // TODO: make for multiple items in cart, not singular
    @PostMapping
    public ResponseEntity<CartItemSummaryDTO> addItem(
            @AuthenticationPrincipal PatronAccount patronAccount,
            @RequestBody CartItemAddItemDTO cartItem
    ) {
        CartDetailsDTO cart = getCart(patronAccount).getBody();

        return switch (cartItem.itemType()) {
            case TICKET -> cartService.addItem(
                        cart.id(),
                        new AddTicketRequest(cartItem.itemId())
                );
            case MERCH -> cartService.addItem(
                        cart.id(),
                        new AddMerchandiseRequest(cartItem.itemId(), 1)
                );
        };
    }

    @DeleteMapping
    public ResponseEntity<Void> removeItem(
            @AuthenticationPrincipal PatronAccount patronAccount,
            @RequestBody CartItemDelete cartItemDelete
    ) {
        CartDetailsDTO cart = getCart(patronAccount).getBody();
        return cartService.removeItemFromCart(cartItemDelete.itemId(), cart.id());
    }
}
