package com.code.theaterapp.shoppingCart.cart.dtos;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "itemType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddTicketRequest.class, name = "TICKET"),
        @JsonSubTypes.Type(value = AddMerchandiseRequest.class, name = "MERCHANDISE"),
})
public sealed interface AddCartItemRequest
    permits AddTicketRequest, AddMerchandiseRequest {
}
