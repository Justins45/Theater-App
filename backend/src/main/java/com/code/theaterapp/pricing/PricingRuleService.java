package com.code.theaterapp.pricing;

import com.code.theaterapp.pricing.dto.PricingRulePrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingRuleService {

    private final PricingRuleRepo pricingRuleRepo;
    private final PricingRuleMapper pricingRuleMapper;

    public PricingRulePrice findBestMatchingRule(UUID performanceId, String section, UUID eventId) {
        PricingRule price = pricingRuleRepo.findBestMatchingRule(performanceId, section, eventId).orElse(null);
        if (price == null) {
            return null;
        }
        return pricingRuleMapper.toPrice(price.getPrice());
    }

}
