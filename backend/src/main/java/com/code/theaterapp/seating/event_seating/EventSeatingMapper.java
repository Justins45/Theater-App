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
               eventSeating.getSeat().getId(),
               eventSeating.getHoldExpiry()
        );
    }
}
