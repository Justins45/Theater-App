package com.code.theaterapp.seating.seat.dtos;

public record SeatDetailsDTO(
        Integer id,
        String row,
        Short seatNumber,
        String section,
        String uiIdentifier,
        Integer stageId
) {
}
