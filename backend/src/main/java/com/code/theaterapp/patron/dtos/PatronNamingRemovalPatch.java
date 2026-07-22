package com.code.theaterapp.patron.dtos;

public record PatronNamingRemovalPatch(
        boolean fistName,
        boolean lastName,
        boolean displayName
) {
}
