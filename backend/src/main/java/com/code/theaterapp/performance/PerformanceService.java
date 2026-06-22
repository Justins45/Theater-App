package com.code.theaterapp.performance;

import com.code.theaterapp.event.Event;
import com.code.theaterapp.event.EventRepo;
import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.performance.dtos.CreatePerformanceDTO;
import com.code.theaterapp.performance.dtos.PerformanceDetailsDTO;
import com.code.theaterapp.performance.dtos.PerformanceSummaryDTO;
import com.code.theaterapp.seating.event_seating.EventSeating;
import com.code.theaterapp.seating.event_seating.EventSeatingRepo;
import com.code.theaterapp.seating.event_seating.dtos.EventSeatingDetailsDTO;
import com.code.theaterapp.seating.seat.Seat;
import com.code.theaterapp.seating.seat.SeatRepo;
import com.code.theaterapp.shared.enums.PerformanceStatus;
import com.code.theaterapp.shared.enums.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final SeatRepo seatRepo;
    private final EventSeatingRepo eventSeatingRepo;
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

        // get all seats from stage
        List<Seat> allSeats = seatRepo.findAllSeatsByStageId(event.getStage().getId());

        // create all event_seating for each seat inside of stage
        List<EventSeating> eventSeatingList = allSeats.stream()
                .map(seat -> {
                    EventSeating e = new EventSeating();
                    e.setSeat(seat);
                    e.setSeatStatus(SeatStatus.AVAILABLE);
                    e.setPerformance(savedPerformance);
                    e.setHoldExpiry(null);
                    return e;
                }).toList();

        // write all seats to event_seating
        List<EventSeating> savedEventSeating = eventSeatingRepo.saveAll(eventSeatingList);

        // convert saved event seating into DTO's
        List<EventSeatingDetailsDTO> savedEventSeatingDTOList = savedEventSeating.stream()
                .map(es -> new EventSeatingDetailsDTO(
                        es.getId(),
                        es.getSeatStatus(),
                        savedPerformance.getId(),   // same for all — no need to call es.getPerformance().getId()
                        es.getSeat().getId(),
                        null
                ))
                .toList();

        return performanceMapper.toDetails(savedPerformance);
    }

    public List<PerformanceSummaryDTO> getAllPerformancesByEvent(UUID eventId) {
        return performanceRepo.findAllByEventId(eventId).stream()
                .map(performanceMapper::toSummary)
                .toList();
    }
}
