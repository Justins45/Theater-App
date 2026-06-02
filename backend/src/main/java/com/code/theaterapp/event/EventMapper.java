package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.EventDetailsDTO;
import com.code.theaterapp.event.dtos.EventSummaryDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EventMapper {

    public EventDetailsDTO toDetails(Event event) {
        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getWallClock(),
                event.getStage().getName(),
                event.getStage().getCapacity()
        );
    }

    public EventSummaryDTO toSummary(Event event) {
        return new EventSummaryDTO(
                event.getId(),
                event.getTitle(),
                event.getWallClock(),
                event.getStage().getId(),
                event.getStage().getName()


        );
    }
}
