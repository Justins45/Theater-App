package com.code.theaterapp.venue;

import com.code.theaterapp.venue.dtos.VenueDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VenueMapper implements Function<Venue, VenueDTO> {
    @Override
    public VenueDTO apply(Venue venue) {
        return new VenueDTO(
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
