package com.code.theaterapp.stage;

import com.code.theaterapp.stage.dtos.CreateStageDTO;
import com.code.theaterapp.stage.dtos.StageDTO;
import com.code.theaterapp.venue.Venue;
import com.code.theaterapp.venue.VenueRepo;
import com.code.theaterapp.venue.VenueService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepo stageRepo;
    private final VenueRepo venueRepo;
    private final StageMapper stageMapper;


    public List<StageDTO> getAllStages(Long venueId) {
        return stageRepo.findAllStagesByVenueId(venueId).stream()
                .map(stageMapper::apply)
                .toList();
    }

    public StageDTO findByIdAndVenueId(Long stageId, Long venueId) {

        Stage stage = stageRepo.findByIdAndVenueId(stageId, venueId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stage not found"));

        return stageMapper.apply(stage);
    }

    public StageDTO createStage(CreateStageDTO createStageDTO) {
        Venue venue = venueRepo.findById(createStageDTO.venueId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venue not found"));

        Stage stage = new Stage();
        stage.setName(createStageDTO.name());
        stage.setCapacity(createStageDTO.capacity());
        stage.setDateCreated(Instant.now());
        stage.setVenue(venue);

        Stage savedStage = stageRepo.save(stage);
        return stageMapper.apply(savedStage);
    }
}
