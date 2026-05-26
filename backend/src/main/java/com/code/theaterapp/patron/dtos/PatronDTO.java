package com.code.theaterapp.patron.dtos;


public record PatronDTO(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
