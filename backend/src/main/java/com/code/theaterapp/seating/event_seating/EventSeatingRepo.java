package com.code.theaterapp.seating.event_seating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventSeatingRepo extends JpaRepository<EventSeating, UUID> {
    List<EventSeating> findAllByPerformanceId(UUID performanceId);
    // EventSeatingRepository
    @Query("SELECT es FROM EventSeating es JOIN FETCH es.seat WHERE es.performance.id = :performanceId")
    List<EventSeating> findAllByPerformanceIdWithSeat(@Param("performanceId") UUID performanceId);
}
