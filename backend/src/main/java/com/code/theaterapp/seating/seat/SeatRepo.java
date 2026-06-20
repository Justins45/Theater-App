package com.code.theaterapp.seating.seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepo extends JpaRepository<Seat, Integer> {
    List<Seat> findAllSeatsByStageID(Integer stageId);
}
