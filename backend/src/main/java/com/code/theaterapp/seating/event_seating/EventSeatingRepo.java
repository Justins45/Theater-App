package com.code.theaterapp.seating.event_seating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventSeatingRepo extends JpaRepository<EventSeating, UUID> {
    Optional<EventSeating> findBySeatIdAndEventId(Integer seatId, UUID eventId);
    List<EventSeating> findAllByEventAndStageId(UUID eventId, Integer stageId);
    List<EventSeating> findAllByStageId(Integer stageId);
}
