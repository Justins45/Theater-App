package com.code.theaterapp.event;

import com.code.theaterapp.event.dtos.CreateEventDTO;
import com.code.theaterapp.event.dtos.EventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/events")
public class EventController {

    private final EventService eventService;

    @GetMapping("/")
    public List<EventDTO> getAlLEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<EventDTO> getEvent(@PathVariable("id") Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping("/")
    public ResponseEntity<EventDTO> createEvent(@RequestBody CreateEventDTO request) {
        EventDTO dto = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    // TODO: PATCH (patrial update not a replacement) for an event by ID


}
