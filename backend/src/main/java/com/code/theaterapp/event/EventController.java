package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.CreateEventDTO;
import com.code.theaterapp.event.dtos.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventDTO> getAlLEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventDTO getEvent(@PathVariable("id") UUID id) {
        // TODO: add checks for published and not expired
        //  .filter(e -> e.isPublished())
        //  .filter(e -> !e.isExpired())
        return eventService.getEventById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody CreateEventDTO request) {
        EventDTO dto = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // TODO: PATCH (patrial update not a replacement) for an event by ID


}
