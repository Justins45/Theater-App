package com.code.theaterapp.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepo extends JpaRepository<Stage, Long> {

    List<Stage> findAllStagesByVenueId(Long venueId);
    Optional<Stage> findByIdAndVenueId(Long stageId, Long venueId);
}
