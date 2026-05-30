package com.code.theaterapp.venue.dtos;

public record VenueDTO(
        Integer id,
        String name,
        String timeZone,
        String street,
        String city,
        String province,
        String postalCode,
        String country
) {
}
