package com.code.theaterapp.stage;

import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.stage.dtos.CreateStageDTO;
import com.code.theaterapp.stage.dtos.StageDetailsDTO;
import com.code.theaterapp.stage.dtos.StageSummaryDTO;
import com.code.theaterapp.venue.Venue;
import com.code.theaterapp.venue.VenueRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepo stageRepo;
    private final VenueRepo venueRepo;
    private final StageMapper stageMapper;

    public List<StageSummaryDTO> getAllStages(Integer venueId) {
        return stageRepo.findAllStagesByVenueId(venueId).stream()
                .map(stageMapper::toSummary)
                .toList();
    }

    public StageDetailsDTO findByIdAndVenueId(Integer stageId, Integer venueId) {
        Stage stage = stageRepo.findByIdAndVenueId(stageId, venueId)
                .orElseThrow(() -> new EntityNotFoundException("Stage not found"));

        return stageMapper.toDetails(stage);
    }

    public StageDetailsDTO createStage(CreateStageDTO createStageDTO) {
        Venue venue = venueRepo.findById(createStageDTO.venueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found"));

        Stage stage = new Stage();
        stage.setName(createStageDTO.name());
        stage.setCapacity(createStageDTO.capacity());
        stage.setDateCreated(Instant.now());
        stage.setVenue(venue);

        Stage savedStage = stageRepo.save(stage);
        return stageMapper.toDetails(savedStage);
    }
}
