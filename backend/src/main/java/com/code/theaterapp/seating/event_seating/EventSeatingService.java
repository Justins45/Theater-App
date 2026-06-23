package com.code.theaterapp.seating.event_seating;

import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.performance.Performance;
import com.code.theaterapp.performance.PerformanceRepo;
import com.code.theaterapp.seating.event_seating.dtos.EventSeatingDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventSeatingService {

    private final PerformanceRepo performanceRepo;
    private final EventSeatingRepo eventSeatingRepo;
    private final EventSeatingMapper eventSeatingMapper;


    public List<EventSeatingDetailsDTO> getAllEventSeatsByPerformanceId(UUID performanceId) {

        Performance performance = performanceRepo.findById(performanceId).orElseThrow(
                () -> new EntityNotFoundException("Performance not found")
        );

        List<EventSeating> list = eventSeatingRepo.findAllByPerformanceId(performance.getId());

        return list.stream()
                .map(eventSeatingMapper::toDetails)
                .toList();
    }
}
