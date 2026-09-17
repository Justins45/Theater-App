package com.code.theaterapp.pricing.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

public record PricingRulePrice(
        @NotBlank 
        BigDecimal price
) {
}
