package com.code.theaterapp.patron.dtos;

public record PatronAccountDetails(
        String email,
        String firstName,
        String lastName,
        String displayName
) {
}
