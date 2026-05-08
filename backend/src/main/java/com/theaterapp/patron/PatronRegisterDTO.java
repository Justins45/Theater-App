package com.theaterapp.patron;

public record PatronRegisterDTO(
        String userName,
        String password,
        String email,
        String role
) {
}
