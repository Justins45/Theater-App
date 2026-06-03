package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.CreateEventDTO;
import com.code.theaterapp.event.dtos.EventDetailsDTO;
import com.code.theaterapp.event.dtos.EventSummaryDTO;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventSummaryDTO> getAlLEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDetailsDTO getEvent(@PathVariable("id") UUID id) {
        return eventService.getEventById(id).orElseThrow(
                () -> new EntityNotFoundException("Event not found")
        );
    }

    @PostMapping
    public ResponseEntity<EventDetailsDTO> createEvent(@RequestBody CreateEventDTO request) {
        EventDetailsDTO dto = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // TODO: PATCH (patrial update not a replacement) for an event by ID


}
