package com.code.theaterapp.tickets;

import com.code.theaterapp.patron.Patron;
import com.code.theaterapp.performance.Performance;
import com.code.theaterapp.shared.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal price;

//    private Order orderId;
//    private Seat seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id")
    private Patron patron;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;

    @Column(nullable = false)
    private Instant createdAt;
}
