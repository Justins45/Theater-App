package com.code.theaterapp.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Used for getting Stage event is on + capacity and other stuff
//    private Stage stage;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalTime showTime;

    @Column(nullable = false)
    private Instant eventCreated;

}
