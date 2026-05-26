package com.code.theaterapp.auth.dtos;

public record PatronRegisterDTO(
        String username,
        String password,
        String email
) {
}