package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.EventDTO;


import java.util.function.Function;

public class EventMapper implements Function<Event, EventDTO> {
    @Override
    public EventDTO apply(Event event) {
        return new EventDTO(
                event.getTitle(),
                event.getWallClock()
        );
    }
}
