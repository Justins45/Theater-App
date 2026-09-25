package com.code.theaterapp.performance.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record CreatePerformanceDTO(

        @NotBlank 
        LocalDateTime showTime
) {
}
