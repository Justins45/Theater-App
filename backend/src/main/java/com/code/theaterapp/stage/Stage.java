package com.code.theaterapp.stage;

import com.code.theaterapp.venue.Venue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Venue venue;

    @Column(nullable = false)
    private Instant dateCreated;

    @Column(nullable = false)
    private Integer capacity;



    // TODO: add stage dimensions


}
