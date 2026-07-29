package com.code.theaterapp.pricing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PricingRuleRepo extends JpaRepository<PricingRule, UUID> {
    @Query("""
            SELECT p FROM PricingRule p
                    WHERE (p.performance IS NULL OR p.performance.id = :performanceId)
                    AND (p.sectionId IS NULL OR p.sectionId = :sectionId)
                    AND (p.event IS NULL OR p.event.id = :eventId)
                    ORDER BY (
                        CASE WHEN p.performance IS NOT NULL THEN 1 ELSE 0 END +
                        CASE WHEN p.sectionId IS NOT NULL THEN 1 ELSE 0 END +
                        CASE WHEN p.event IS NOT NULL THEN 1 ELSE 0 END
                    ) DESC
                    LIMIT 1
            """)
    Optional<PricingRule> findBestMatchingRule(
            @Param("performanceId") UUID performance,
            @Param("sectionId") String sectionId,
            @Param("eventId") UUID event
    );
}
