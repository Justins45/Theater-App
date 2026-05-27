package com.theaterapp.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class EventMapperTest {

    EventMapper eventMapper = new EventMapper();

    @Test
    @DisplayName("Event Maps to all DTO fields")
    void shouldMapToAllDTOFields() {
        Event event = new Event("spiderman", LocalDateTime.parse("2024-05" +
                "-20T10:15:30"));

        EventDTO eventDTO = eventMapper.apply(event);

        assertEquals("Missing Title", "spiderman", eventDTO.title());
        assertEquals("Missing Date and Time", LocalDateTime.parse("2024-05-20T10:15:30"),
                eventDTO.dateTime());
    }


    // needs a non exposure test
}
