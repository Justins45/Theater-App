package com.code.theaterapp.performance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PerformanceRepo extends JpaRepository<Performance, UUID> {
//    List<Performance> findAllByPatronId(UUID patronId);
    List<Performance> findAllByEventId(UUID eventId);
//    List<Performance> findAllByVenueId(UUID venueId);
}
