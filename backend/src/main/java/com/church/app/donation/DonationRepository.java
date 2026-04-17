package com.church.app.donation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByTenantId(Long tenantId);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.tenantId = :tenantId AND d.date >= :startDate AND d.date <= :endDate")
    BigDecimal sumByTenantAndDateRange(Long tenantId, LocalDate startDate, LocalDate endDate);
}
