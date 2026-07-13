package com.code.theaterapp.shoppingCart.cartItem.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "itemType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CartTicketItem.class, name = "TICKET"),
        @JsonSubTypes.Type(value = CartMerchItem.class, name = "MERCH")
})
public sealed interface CartItemDetailsDTO permits CartTicketItem, CartMerchItem {
}
