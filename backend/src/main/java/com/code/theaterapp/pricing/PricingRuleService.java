package com.code.theaterapp.pricing;

import com.code.theaterapp.pricing.dto.PricingRulePrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PricingRuleService {




    public PricingRulePrice findBestMatchingRule(UUID performanceId, UUID eventId, String section) {

        return BigDecimal.valueOf(0.00);
    }

}
