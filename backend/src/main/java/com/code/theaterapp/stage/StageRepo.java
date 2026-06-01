package com.code.theaterapp.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StageRepo extends JpaRepository<Stage, Integer> {

    List<Stage> findAllStagesByVenueId(Integer venueId);
    Optional<Stage> findByIdAndVenueId(Integer stageId, Integer venueId);
}
