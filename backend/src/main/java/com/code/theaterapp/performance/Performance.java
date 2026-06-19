package com.code.theaterapp.performance;

import com.code.theaterapp.event.Event;
import com.code.theaterapp.shared.enums.PerformanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "performances")
public class Performance {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    private UUID id;

    @Column(nullable = false)
    private LocalTime showTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PerformanceStatus performanceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    public String getWallClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return this.showTime.format(formatter);
    }

}
