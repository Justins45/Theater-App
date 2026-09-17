package com.code.theaterapp.venue.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateVenueDTO(
        @NotBlank 
        String name,

        @NotBlank 
        String timeZone,

        @NotBlank 
        String street,

        @NotBlank 
        String city,

        @NotBlank 
        String province,

        @NotBlank 
        String postalCode,

        @NotBlank 
        String country
) {
}
