package com.code.theaterapp.performance;

import com.code.theaterapp.performance.dtos.PerformanceDetailsDTO;
import com.code.theaterapp.performance.dtos.PerformanceSummaryDTO;
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

    public PerformanceSummaryDTO toSummary(Performance performance) {
        return new PerformanceSummaryDTO(
                performance.getId(),
                performance.getWallClock(),
                performance.getPerformanceStatus(),
                performance.getEvent().getId()
        );
    }
}
