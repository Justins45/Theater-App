package com.code.theaterapp.seating.event_seating;

import com.code.theaterapp.seating.event_seating.dtos.EventSeatingDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
// Use events URL to get the pathing ID for the Event
@RequestMapping(value = "/events")
public class EventSeatingController {

    private final EventSeatingService eventSeatingService;
    private final String URL_PATH = "/{eventId}/performances/{performanceId}/seating";

    // get performances for an event
    @GetMapping(URL_PATH)
    public List<EventSeatingDetailsDTO> findAllSeats(@PathVariable UUID performanceId) {
        return eventSeatingService.getAllEventSeatsByPerformanceId(performanceId);
    }
}
