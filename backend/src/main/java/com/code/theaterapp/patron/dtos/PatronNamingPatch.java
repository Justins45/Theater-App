package com.code.theaterapp.patron.dtos;

import org.jspecify.annotations.Nullable;

public record PatronNamingPatch(

        @Nullable
        String firstName,

        @Nullable
        String lastName,

        @Nullable
        String displayName
) {
}
