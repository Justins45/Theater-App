package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.VenueDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VenueMapper implements Function<Venue, VenueDetailsDTO> {
    @Override
    public VenueDetailsDTO apply(Venue venue) {
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
}
