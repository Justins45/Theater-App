package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.EventDetailsDTO;
import com.code.theaterapp.event.dtos.EventSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDetailsDTO toDetails(Event event) {
        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getStage().getName(),
                event.getStage().getCapacity()
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
