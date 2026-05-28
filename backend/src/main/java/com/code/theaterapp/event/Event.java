package com.code.theaterapp.event;

import com.code.theaterapp.stage.Stage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private String title;
    @Column(nullable = false)
    private LocalTime showTime;

    @ManyToOne
    private Stage stage;

    @Column(nullable = false)
    private Instant eventCreated;

    public void setShowTime(String newShowTime) {
        this.showTime = LocalTime.parse(newShowTime);
    }

    public String getWallClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm a");
        return this.showTime.format(formatter);
    }

}
