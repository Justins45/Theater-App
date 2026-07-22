package com.code.theaterapp.patron.dtos;

public record PatronNamingRemovalPatch(
        boolean firstName,
        boolean lastName,
        boolean displayName
) {
}
