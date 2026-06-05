package com.code.theaterapp.patron.dtos;

public record PatronMeResponse(
        String email,
        String firstName,
        String lastName,
        String displayname
) {
}
