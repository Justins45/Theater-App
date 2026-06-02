package com.code.theaterapp.patron.dtos;


public record PatronDetailsDTO(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
