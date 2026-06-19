package com.code.theaterapp.seating.seat;

import com.code.theaterapp.stage.Stage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
// THIS BELONGS TO STAGE AND IS SET ONCE (unless the stage seating plan changes)
// event_seats HOLDS THE INFORMATION ABOUT THE SOLD, HELD, AVAILABLE + METADATA
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String row;          // AA, AB, AC...BG

    @Column(nullable = false)
    private Short seatNumber;    // 1, 2..19, 20

    @Column(nullable = false)
    private String section;      // (Orchestra, Mezzanine, Balcony, Left, Right, etc.)

    @Column(nullable = false)
    private String uiIdentifier; // orch-AA-6 | main-AG-19

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage;
}
