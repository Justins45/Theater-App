package com.theaterapp.event;

public record EventDTO(
        String title,
        String description,
        String director,
        int capacity
) {
}
