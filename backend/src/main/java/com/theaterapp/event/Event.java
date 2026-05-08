package com.theaterapp.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Future
    @Column(nullable = false)
    private LocalDateTime dateTime;

    public Event() {
    }

    public Event(String title, LocalDateTime dateTime) {
        this.title = title;
        this.dateTime = dateTime;
    }
}
