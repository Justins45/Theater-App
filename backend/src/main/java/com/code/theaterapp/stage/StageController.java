package com.code.theaterapp.stage;

import com.code.theaterapp.stage.dtos.CreateStageDTO;
import com.code.theaterapp.stage.dtos.StageDetailsDTO;
import com.code.theaterapp.stage.dtos.StageSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/venue/{venueId}/stages")
public class StageController {

    private final StageService stageService;

    @GetMapping
    public List<StageSummaryDTO> getAllStages(@PathVariable Integer venueId) {
        return stageService.getAllStages(venueId);
    }

    @GetMapping("/{stageId}")
    public StageDetailsDTO getStageById(@PathVariable Integer venueId, @PathVariable Integer stageId) {
        return stageService.findByIdAndVenueId(stageId, venueId);
    }

    @PostMapping
    public ResponseEntity<StageDetailsDTO> createStage(@RequestBody CreateStageDTO request) {
        StageDetailsDTO dto = stageService.createStage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
