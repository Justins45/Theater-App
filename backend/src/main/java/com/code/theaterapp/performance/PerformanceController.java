package com.code.theaterapp.performance;

import com.code.theaterapp.performance.dtos.CreatePerformanceDTO;
import com.code.theaterapp.performance.dtos.PerformanceDetailsDTO;
import com.code.theaterapp.performance.dtos.PerformanceSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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

    @GetMapping(URL_PATH + "/{performanceId}/info")
    public ResponseEntity<PerformanceDetailsDTO> getPerformanceInformation(@PathVariable UUID performanceId) {
        return performanceService.getPerformanceById(performanceId);
    }

    // create performance
    @PostMapping(URL_PATH)
    public ResponseEntity<PerformanceDetailsDTO> createPerformance(
            @RequestBody CreatePerformanceDTO request,
            @PathVariable UUID eventId
    ) {
        PerformanceDetailsDTO dto = performanceService.createPerformance(request, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
