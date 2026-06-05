package com.code.theaterapp.patron.dtos;


public record PatronDetailsDTO(
        String email,
        String firstName,
        String lastName,
        String displayname
) {
}
