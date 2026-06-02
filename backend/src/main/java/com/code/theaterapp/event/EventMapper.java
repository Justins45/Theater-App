package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.EventDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class EventMapper implements Function<Event, EventDetailsDTO> {
    @Override
    public EventDetailsDTO apply(Event event) {
        return new EventDetailsDTO(
                event.getId(),
                event.getTitle(),
                event.getWallClock(),
                event.getStage().getName(),
                event.getStage().getCapacity()
        );
    }
}
