package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.VenueDetailsDTO;
import com.code.theaterapp.venue.dtos.VenueSummaryDTO;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {
    public VenueDetailsDTO toDetails(Venue venue) {
        return new VenueDetailsDTO(
                venue.getId(),
                venue.getName(),
                venue.getTimeZone(),
                venue.getStreet(),
                venue.getCity(),
                venue.getProvince(),
                venue.getPostalCode(),
                venue.getCountry()
        );
    }

    public VenueSummaryDTO toSummary(Venue venue) {
        return new VenueSummaryDTO(
                venue.getId(),
                venue.getName(),
                venue.getTimeZone(),
                venue.getStreet(),
                venue.getCity()
        );
    }
}
