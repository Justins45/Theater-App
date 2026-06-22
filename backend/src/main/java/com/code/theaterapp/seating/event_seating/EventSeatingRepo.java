package com.code.theaterapp.seating.event_seating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventSeatingRepo extends JpaRepository<EventSeating, UUID> {
    List<EventSeating> findAllByPerformanceId(UUID performanceId);
}
