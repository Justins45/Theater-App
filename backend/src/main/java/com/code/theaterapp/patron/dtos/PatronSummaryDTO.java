package com.code.theaterapp.patron.dtos;

import java.util.UUID;

public record PatronSummaryDTO(
        UUID id,
        String firstName,
        String lastName,
        String displayname
) {
}
