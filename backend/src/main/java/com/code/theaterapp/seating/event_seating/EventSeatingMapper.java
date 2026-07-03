package com.code.theaterapp.seating.event_seating;

import com.code.theaterapp.seating.event_seating.dtos.EventSeatingDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class EventSeatingMapper {

    public EventSeatingDetailsDTO toDetails(EventSeating eventSeating) {
        return new EventSeatingDetailsDTO(
                eventSeating.getId(),
                eventSeating.getSeatStatus(),
                eventSeating.getPerformance().getId(),
                eventSeating.getHoldExpiry(),
                eventSeating.getSeat().getId(),
                eventSeating.getSeat().getRow(),
                eventSeating.getSeat().getSeatNumber(),
                eventSeating.getSeat().getSection(),
                eventSeating.getSeat().getUiIdentifier(),
                eventSeating.getPerformance().getPrice(),
                eventSeating.getSeat().getStage().getId()
        );
    }
}
