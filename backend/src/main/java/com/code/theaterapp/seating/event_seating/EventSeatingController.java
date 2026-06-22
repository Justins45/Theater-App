package com.code.theaterapp.seating.event_seating;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
// Use events URL to get the pathing ID for the Event
@RequestMapping(value = "/performances")
public class EventSeatingController {

    private final EventSeatingRepo eventSeatingRepo;
    private final String URL_PATH = "/{performanceId}/seating";

    // get performances for an event
    @GetMapping(URL_PATH)
    public List<EventSeating> findAllSeats(@PathVariable UUID performanceId) {
        return eventSeatingRepo.findAllByPerformanceId(performanceId);
    }
}
