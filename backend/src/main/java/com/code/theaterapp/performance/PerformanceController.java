package com.code.theaterapp.performance;

import com.code.theaterapp.performance.dtos.CreatePerformanceDTO;
import com.code.theaterapp.performance.dtos.PerformaceDetailsDTO;
import com.code.theaterapp.performance.dtos.PerformanceSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
// Use events URL to get the pathing ID for the Event
@RequestMapping(value = "/events")
public class PerformanceController {

    private final PerformanceService performanceService;
    private final String URL_PATH = "/{eventId}/performances";

    // get performances for an event
    @GetMapping(URL_PATH)
    public List<PerformanceSummaryDTO> getAllPerformances(@PathVariable UUID eventId) {
        return performanceService.getAllPerformancesByEvent(eventId);
    }

    // create performance
    @PostMapping(URL_PATH)
    public ResponseEntity<PerformaceDetailsDTO> createPerformance(
            @RequestBody CreatePerformanceDTO request,
            @PathVariable UUID eventId
    ) {
        PerformaceDetailsDTO dto = performanceService.createPerformance(request, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
