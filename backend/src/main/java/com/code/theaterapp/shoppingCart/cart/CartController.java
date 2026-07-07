package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.auth.secruity.accounts.PatronAccount;
import com.code.theaterapp.shared.enums.CartItemType;
import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import com.code.theaterapp.shoppingCart.cart.interfaces.AddMerchandiseRequest;
import com.code.theaterapp.shoppingCart.cart.interfaces.AddTicketRequest;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemAddItemDTO;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<CartItemSummaryDTO> addItem(
            @AuthenticationPrincipal PatronAccount patronAccount,
            CartItemAddItemDTO cartItem
    ) {
        CartDetailsDTO cart = getCart(patronAccount).getBody();

        return switch (cartItem.itemType()) {
            case CartItemType.TICKET -> cartService.addItem(
                        cart.id(),
                        new AddTicketRequest(cartItem.itemId())
                );
            case CartItemType.MERCH -> cartService.addItem(
                        cart.id(),
                        new AddMerchandiseRequest(cartItem.itemId(), cartItem.quantity())
                );
        };
    }
}
