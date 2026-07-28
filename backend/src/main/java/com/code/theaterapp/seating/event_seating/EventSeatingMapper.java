package com.code.theaterapp.seating.event_seating;

import com.code.theaterapp.pricing.dto.PricingRulePrice;
import com.code.theaterapp.seating.event_seating.dtos.EventSeatingDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public class EventSeatingMapper {

    public EventSeatingDetailsDTO toDetails(EventSeating eventSeating, PricingRulePrice price) {
        return new EventSeatingDetailsDTO(
                eventSeating.getId(),
                eventSeating.getSeatStatus(),
                eventSeating.getPerformance().getId(),
                eventSeating.getHoldExpiry(),
                eventSeating.getSeat().getId(),
                eventSeating.getSeat().getRow(),
                eventSeating.getSeat().getSeatNumber(),
                eventSeating.getSeat().getSection(),
                eventSeating.getSeat().getUiIdentifier(),
                price,
                eventSeating.getSeat().getStage().getId()
        );
    }
}
