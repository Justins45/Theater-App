package com.code.theaterapp.shoppingCart.cartItem;

import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemDetailsDTO;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartItemSummaryDTO;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartMerchItem;
import com.code.theaterapp.shoppingCart.cartItem.dtos.CartTicketItem;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    public CartItemSummaryDTO toSummary(CartItem cartItem) {
        return new CartItemSummaryDTO(
                cartItem.getId(),
                cartItem.getItemType(),
                cartItem.getUnitPrice()
        );
    }

    public CartItemDetailsDTO toDetails(CartItem cartItem) {
        return switch (cartItem.getItemType()) {
            case TICKET -> new CartTicketItem(
                        cartItem.getId(),
                        cartItem.getCart().getId(),
                        cartItem.getEventSeating().getPerformance().getEvent().getTitle(),
                        cartItem.getEventSeating().getPerformance().getEvent().getStage().getName(),
                        cartItem.getEventSeating().getPerformance().getWallClock(),
                        cartItem.getUnitPrice(),
                        cartItem.getEventSeating().getSeat().getRow(),
                        cartItem.getEventSeating().getSeat().getSection(),
                        cartItem.getEventSeating().getSeat().getSeatNumber()
                );
            // TODO: TEMP METHOD FOR OTHER STUFF BEFORE IT
            case MERCH -> new CartMerchItem("Merch item OWO");
        };
    }
}
