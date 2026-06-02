package com.code.theaterapp.patron.dtos;

import java.util.UUID;

public record PatronSummaryDTO(
        UUID id,
        String username,
        String firstName,
        String lastName
) {
}
