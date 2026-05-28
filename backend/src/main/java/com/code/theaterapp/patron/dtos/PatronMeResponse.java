package com.code.theaterapp.patron.dtos;

public record PatronMeResponse(
        String username,
        String email,
        String firstName,
        String lastName
) {
}
