package com.code.theaterapp.performance.dtos;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;

public record CreatePerformanceDTO(

        @NotBlank 
        LocalTime showTime
) {
}
