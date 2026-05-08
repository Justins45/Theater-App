package com.theaterapp.event;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EventMapper implements Function<Event, EventDTO> {
    @Override
    public EventDTO apply(Event event) {
        return new EventDTO(
                event.getTitle(),
                event.getDateTime()
        );
    }
}
