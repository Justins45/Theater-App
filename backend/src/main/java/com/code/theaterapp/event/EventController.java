package com.code.theaterapp.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/event")
public class EventController {

    @GetMapping("/")
    public String getAlLEvents() {
        return "Welcome to the Event root path";
    }

    @GetMapping("/{id}")
    public String getEvent(@PathVariable("id") int id) {
        return "Welcome to Event with id: " + id;
    }

    @PostMapping("/")
    public String createEvent() {
        return "Silly gaf, there's no posting yet for events";
    }

    // TODO: PATCH (patrial update not a replacement) for an event by ID


}
