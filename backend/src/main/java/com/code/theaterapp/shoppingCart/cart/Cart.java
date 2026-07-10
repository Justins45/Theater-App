package com.code.theaterapp.shoppingCart.cart;

import com.code.theaterapp.shared.enums.CartStatus;
import com.code.theaterapp.shared.person.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @Column(nullable = false)
    private Instant cartCreated;


}
