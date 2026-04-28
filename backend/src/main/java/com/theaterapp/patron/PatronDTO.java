package com.theaterapp.patron;

// only expose what we want to the API
public record PatronDTO(
        String firstName,
        String lastName,
        String email
) {
}
