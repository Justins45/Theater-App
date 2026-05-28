package com.code.theaterapp.stage;

import com.code.theaterapp.stage.dtos.CreateStageDTO;
import com.code.theaterapp.stage.dtos.StageDTO;
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
    public List<StageDTO> getAllStages(@PathVariable Long venueId) {
        return stageService.getAllStages(venueId);
    }

    @GetMapping("/{stageId}")
    public StageDTO getStageById(@PathVariable Long venueId, @PathVariable Long stageId) {
        return stageService.findByIdAndVenueId(stageId, venueId);
    }

    @PostMapping
    public ResponseEntity<StageDTO> createStage(@RequestBody CreateStageDTO request) {
        StageDTO dto = stageService.createStage(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
