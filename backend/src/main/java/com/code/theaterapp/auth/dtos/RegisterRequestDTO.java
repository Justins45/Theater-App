package com.code.theaterapp.auth.dtos;


public record RegisterRequestDTO(
        String username,
        String email,
        String password
) {
}
// TODO: add min size check here and maybe frontend too