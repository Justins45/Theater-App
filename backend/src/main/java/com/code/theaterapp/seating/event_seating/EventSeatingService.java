package com.code.theaterapp.seating.event_seating;

import com.code.theaterapp.exceptions.EntityNotFoundException;
import com.code.theaterapp.performance.Performance;
import com.code.theaterapp.performance.PerformanceRepo;
import com.code.theaterapp.seating.event_seating.dtos.EventSeatingDetailsDTO;
import com.code.theaterapp.seating.seat.Seat;
import com.code.theaterapp.seating.seat.SeatRepo;
import com.code.theaterapp.shared.enums.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventSeatingService {

    private final PerformanceRepo performanceRepo;
    private final SeatRepo seatRepo;
    private final EventSeatingRepo eventSeatingRepo;
    private final EventSeatingMapper eventSeatingMapper;


    public List<EventSeatingDetailsDTO> createEventSeating(Integer stageId, UUID performanceId) {
        // get all seats from stage
        List<Seat> allSeats = seatRepo.findAllSeatsByStageId(stageId);
        Performance performance = performanceRepo.findById(performanceId).orElseThrow(
                () -> new EntityNotFoundException("Performance not found")
        );

        // create all event_seating for each seat inside of stage
        List<EventSeating> eventSeatingList = allSeats.stream()
                .map(seat -> {
                    EventSeating e = new EventSeating();
                    e.setSeat(seat);
                    e.setSeatStatus(SeatStatus.AVAILABLE);
                    e.setPerformance(performance);
                    e.setHoldExpiry(null);
                    e.setSeat(seat);
                    return e;
                }).toList();

        // write all seats to event_seating
        List<EventSeating> savedEventSeating = eventSeatingRepo.saveAll(eventSeatingList);

        // convert saved event seating into DTO's
        return savedEventSeating.stream()
                .map(es -> new EventSeatingDetailsDTO(
                        es.getId(),
                        es.getSeatStatus(),
                        performanceId,
                        es.getSeat().getId(),
                        null,
                        es.getSeat()
                ))
                .toList();
    }

    public List<EventSeatingDetailsDTO> getAllEventSeatsByPerformanceId(UUID performanceId) {

        Performance performance = performanceRepo.findById(performanceId).orElseThrow(
                () -> new EntityNotFoundException("Performance not found")
        );
        return eventSeatingRepo.findAllByPerformanceIdWithSeat(performance.getId())
                .stream()
                .map(eventSeatingMapper::toDetails)
                .toList();
    }
}
