package com.code.theaterapp.seating.seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat, Integer> {
    List<Seat> findAllSeatsByStageId(Integer stageId);
}
