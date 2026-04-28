package com.theaterapp.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class EventMapperTest {

    EventMapper eventMapper = new EventMapper();

    @Test
    @DisplayName("Event Maps to all DTO fields")
    void shouldMapToAllDTOFields() {
        Event event = new Event("spiderman", "spiderman description", "jane " +
                "doe", 69);

        EventDTO eventDTO = eventMapper.apply(event);

        assertEquals("Missing Title", "spiderman", eventDTO.title());
        assertEquals("Missing Description", "spiderman description",
                eventDTO.description());
        assertEquals("Missing Director", "jane doe", eventDTO.director());
        assertEquals("Missing Capacity", 69, eventDTO.capacity());
    }


    // needs a non exposure test
}
