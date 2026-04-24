package com.code.backend.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventsRepository eventRepository;

    @GetMapping("/events")
    public List<Events> getAllEvent() {
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    public Events createEvent(@RequestBody Events events) {
        return eventRepository.save(events);
    }

}
