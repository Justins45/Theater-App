package com.code.backend.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventsController {

    @Autowired
    EventsRepository eventRepository;

    @GetMapping("/events")
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    public Events createBook(@RequestBody Events events) {
        return eventRepository.save(events);
    }

}
