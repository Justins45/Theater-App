package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.EventDetailsDTO;
import com.code.theaterapp.event.dtos.EventSummaryDTO;
import com.code.theaterapp.performance.dtos.PerformanceSummaryDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventMapper {

    public EventDetailsDTO toDetails(Event event, List<PerformanceSummaryDTO> performances) {
        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getStage().getName(),
                event.getStage().getCapacity(),
                performances
        );
    }

    public EventSummaryDTO toSummary(Event event) {
        return new EventSummaryDTO(
                event.getId(),
                event.getTitle(),
                event.getStage().getId(),
                event.getStage().getName()


        );
    }
}
