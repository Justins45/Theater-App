package com.code.theaterapp.venue.dtos;

public record VenueSummaryDTO(
        Integer id,
        String name,
        String timeZone,
        String street,
        String city
) {
}
