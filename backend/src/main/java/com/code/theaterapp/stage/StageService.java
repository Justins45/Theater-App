package com.code.theaterapp.stage;

import com.code.theaterapp.stage.dtos.CreateStageDTO;
import com.code.theaterapp.stage.dtos.StageDTO;
import com.code.theaterapp.venue.Venue;
import com.code.theaterapp.venue.VenueRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepo stageRepo;
    private final VenueRepo venueRepo;
    private final StageMapper stageMapper;


    public List<StageDTO> getAllStages() {
        return stageRepo.findAll().stream()
                .map(stageMapper::apply)
                .toList();
    }

    public Optional<StageDTO> getStageById(Long id) { return stageRepo.findById(id).map(stageMapper::apply); }

    public StageDTO createStage(CreateStageDTO createStageDTO) {
        Venue venue = venueRepo.findById(createStageDTO.venueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found"));

        Stage stage = new Stage();
        stage.setName(createStageDTO.name());
        stage.setCapacity(createStageDTO.capacity());
        stage.setDateCreated(Instant.now());
        stage.setVenue(venue);

        Stage savedStage = stageRepo.save(stage);
        return stageMapper.apply(savedStage);
    }
}
