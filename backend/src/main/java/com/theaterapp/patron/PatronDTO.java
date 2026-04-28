package com.theaterapp.patron;

// only expose what we want to the API
public record PatronDTO(
        String first_name,
        String last_name,
        String email
) {
}
