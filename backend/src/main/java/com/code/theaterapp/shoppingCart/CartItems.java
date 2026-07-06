package com.code.theaterapp.shoppingCart;

import com.code.theaterapp.seating.event_seating.EventSeating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "cart_items")
public class CartItems {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(nullable = false)
    private String itemType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_seating_id", nullable = true)
    private EventSeating eventSeating;

    // TODO: alternative columns to add (Merch, Gift Cards?, Donations)

    @Column(nullable = false)
    private String unitPrice;

    @Column(nullable = false)
    private Instant addedAt;

}
