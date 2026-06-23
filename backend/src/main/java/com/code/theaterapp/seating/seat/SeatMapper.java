package com.code.theaterapp.seating.seat;

import com.code.theaterapp.seating.seat.dtos.SeatDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public SeatDetailsDTO toDetails(Seat seat) {
        return new SeatDetailsDTO(
                seat.getId(),
                seat.getRow(),
                seat.getSeatNumber(),
                seat.getSection(),
                seat.getUiIdentifier(),
                seat.getStage().getId()
        );
    }
}
