package com.code.theaterapp.pricing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PricingRuleService {




    public BigDecimal findBestMatchingRule(UUID performanceId, UUID eventId, String section) {
        
        return BigDecimal.valueOf(0.00);
    }

}
