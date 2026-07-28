package com.code.theaterapp.pricing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PricingRuleRepo extends JpaRepository<PricingRule, UUID> {
    @Query("""
            SELECT p FROM PricingRule p
            WHERE (p.performance = :performance OR p.performance IS NULL)
            AND (p.sectionId = :sectionId OR p.sectionId IS NULL)
            AND (p.event = :event OR p.event IS NULL)
            ORDER BY (
                CASE WHEN p.performance IS NOT NULL THEN 1 ELSE 0 END +
                CASE WHEN p.sectionId IS NOT NULL THEN 1 ELSE 0 END +
                CASE WHEN p.event IS NOT NULL THEN 1 ELSE 0 END
            ) DESC
            LIMIT 1
            """)
    Optional<PricingRule> findBestMatchingRule(
            @Param("performance") UUID performance,
            @Param("sectionId") String sectionId,
            @Param("event") UUID event
    );
}
