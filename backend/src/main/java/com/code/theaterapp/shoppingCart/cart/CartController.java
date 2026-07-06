package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.shoppingCart.cart.dtos.CartDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {


    @GetMapping
    public List<CartDetailsDTO> getCart() {

    }



}
