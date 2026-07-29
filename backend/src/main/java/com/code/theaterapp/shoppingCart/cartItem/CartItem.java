package com.code.theaterapp.shoppingCart.cartItem;

import com.code.theaterapp.seating.event_seating.EventSeating;
import com.code.theaterapp.shared.enums.CartItemType;
import com.code.theaterapp.shoppingCart.cart.Cart;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CartItemType itemType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_seating_id", nullable = true)
    private EventSeating eventSeating;

    // TODO: alternative columns to add (Merch, Gift Cards?, Donations)

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Instant addedAt;

}
