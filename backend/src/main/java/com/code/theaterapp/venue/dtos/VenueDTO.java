package com.code.theaterapp.venue.dtos;

public record VenueDTO(
        Long id,
        String name,
        String timeZone,
        String street,
        String city,
        String province,
        String postalCode,
        String country
) {
}
