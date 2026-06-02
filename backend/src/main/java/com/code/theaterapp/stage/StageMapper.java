package com.code.theaterapp.stage;

import com.code.theaterapp.stage.dtos.StageDetailsDTO;
import com.code.theaterapp.stage.dtos.StageSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class StageMapper {

    public StageDetailsDTO toDetails(Stage stage) {
        return new StageDetailsDTO(
                stage.getName(),
                stage.getCapacity(),
                stage.getVenue().getId()
        );
    }

    public StageSummaryDTO toSummary(Stage stage) {
        return new StageSummaryDTO(
                stage.getName(),
                stage.getCapacity(),
                stage.getVenue().getId()
        );
    }
}
