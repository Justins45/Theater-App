package com.code.theaterapp.performance;

import com.code.theaterapp.performance.dtos.PerformanceDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class PerformanceMapper {

    public PerformanceDetailsDTO toDetails(Performance performance) {
        return new PerformanceDetailsDTO(
                performance.getId(),
                performance.getWallClock(),
                performance.getPerformanceStatus(),
                performance.getEvent().getId()
        );
    }
}
