package com.theaterapp.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventRepository eventRepository;

    @Mock
    EventMapper eventMapper;

    @InjectMocks
    EventService eventService;

    @Test
    @DisplayName("Find all events should return mapped DTO's")
    void findAllEvents_shouldReturnMappedDTOs() {
        Event event = new Event("spiderman", "spiderman description", "jane " +
                "doe", 69);

        EventDTO eventDTO = new EventDTO("spiderman", "spiderman description", "jane " +
                "doe", 69);

        when(eventRepository.findAll()).thenReturn(List.of(event));
        when(eventMapper.apply(event)).thenReturn(eventDTO);

        List<EventDTO> result = eventService.findAll();

        assertEquals("Size is < or > 1", 1, result.size());
        assertEquals("spiderman not first in line", "spiderman",
                result.getFirst().title());
    }

    @Test
    @DisplayName("Save should persist and Return DTO")
    void save_shouldPersistAndReturnDTO() {
        EventDTO inputDTO = new EventDTO("spiderman", "spiderman description"
                , "jane doe", 69);
        Event savedEvent = new Event("spiderman", "spiderman description"
                , "jane doe", 69);
        EventDTO outputDTO = new EventDTO("spiderman", "spiderman description"
                , "jane doe", 69);

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);
        when(eventMapper.apply(savedEvent)).thenReturn(outputDTO);

        EventDTO result = eventService.save(inputDTO);

        assertEquals("Event not saved", "spiderman", result.title());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

}
