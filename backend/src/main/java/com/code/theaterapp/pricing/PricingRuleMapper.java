package com.code.theaterapp.pricing;

import com.code.theaterapp.pricing.dto.PricingRulePrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PricingRuleMapper {
    public PricingRulePrice toPrice(BigDecimal price) {
        return new PricingRulePrice(price);
    }
}
