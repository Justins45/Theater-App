package com.code.theaterapp.patron.dtos;

public record PatronNamingRemovalPatch(
        Boolean firstName,
        Boolean lastName,
        Boolean displayName
) {
}
