package com.code.theaterapp.stage;

import com.code.theaterapp.stage.dtos.StageDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class StageMapper implements Function<Stage, StageDTO> {
    @Override
    public StageDTO apply(Stage stage) {
        return new StageDTO(
                stage.getName(),
                stage.getCapacity(),
                stage.getVenue().getId()
        );
    }
}
