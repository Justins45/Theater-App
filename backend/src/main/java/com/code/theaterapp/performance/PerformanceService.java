package com.code.theaterapp.performance;

import com.code.theaterapp.event.Event;
import com.code.theaterapp.event.EventRepo;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.performance.dtos.CreatePerformanceDTO;
import com.code.theaterapp.performance.dtos.PerformanceDetailsDTO;
import com.code.theaterapp.performance.dtos.PerformanceSummaryDTO;
import com.code.theaterapp.shared.enums.PerformanceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepo performanceRepo;
    private final PerformanceMapper performanceMapper;
    private final EventRepo eventRepo;

    public PerformanceDetailsDTO createPerformance(CreatePerformanceDTO createPerformanceDTO, UUID eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));

        Performance performance = new Performance();
        performance.setShowTime(createPerformanceDTO.showTime());
        performance.setEvent(event);
        performance.setPerformanceStatus(PerformanceStatus.SCHEDULED);

        Performance savedPerformance = performanceRepo.save(performance);
        return performanceMapper.toDetails(savedPerformance);
    }

    public List<PerformanceSummaryDTO> getAllPerformancesByEvent(UUID eventId) {
        return performanceRepo.findAllByEventId(eventId).stream()
                .map(performanceMapper::toSummary)
                .toList();
    }
}
