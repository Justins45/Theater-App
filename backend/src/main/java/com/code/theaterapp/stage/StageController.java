package com.code.theaterapp.stage;

import com.code.theaterapp.stage.dtos.StageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/venue/{venueId}/stages")
public class StageController {

    private final StageService stageService;

    @GetMapping("/{stageId}")
    public StageDTO getStageById(@PathVariable Long venueId, @PathVariable Long stageId) {
        return stageService.findByIdAndVenueId(stageId, venueId);
    }

}
