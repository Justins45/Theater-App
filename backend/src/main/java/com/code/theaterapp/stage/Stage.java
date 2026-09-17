package com.code.theaterapp.stage;

import com.code.theaterapp.venue.Venue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @CreationTimestamp 
    @Column(nullable = false)
    private Instant dateCreated;

    // TODO: remove as SEAT has this information
    @Column(nullable = false)
    private Integer capacity;

}
