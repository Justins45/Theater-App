package com.code.theaterapp.seating.event_seating.dtos;

import com.code.theaterapp.seating.seat.Seat;
import com.code.theaterapp.shared.enums.SeatStatus;

import java.time.Instant;
import java.util.UUID;

public record EventSeatingDetailsDTO(
        UUID id,
        SeatStatus seatStatus,
        UUID performanceId,
        Integer seatId,
        Instant holdExpiry,
        Seat seat
) {
}
