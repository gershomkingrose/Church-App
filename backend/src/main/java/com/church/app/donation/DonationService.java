package com.church.app.donation;

import com.church.app.common.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    public List<Donation> getAll() {
        return donationRepository.findByTenantId(TenantContext.getTenantId());
    }

    public Donation getById(Long id) {
        return donationRepository.findById(id)
                .filter(d -> d.getTenantId().equals(TenantContext.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }

    public Donation create(Donation donation) {
        if (donation.getMemberId() == null && donation.getDonorId() == null) {
            throw new RuntimeException("Either memberId or donorId must be provided");
        }
        donation.setId(null);
        donation.setTenantId(TenantContext.getTenantId());
        return donationRepository.save(donation);
    }

    public Donation update(Long id, Donation updated) {
        Donation existing = getById(id);
        existing.setMinistryId(updated.getMinistryId());
        existing.setMemberId(updated.getMemberId());
        existing.setDonorId(updated.getDonorId());
        existing.setAmount(updated.getAmount());
        existing.setPurpose(updated.getPurpose());
        existing.setDate(updated.getDate());
        return donationRepository.save(existing);
    }

    public void delete(Long id) {
        Donation donation = getById(id);
        donationRepository.delete(donation);
    }

    public BigDecimal getMonthlyTotal() {
        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());
        return donationRepository.sumByTenantAndDateRange(TenantContext.getTenantId(), start, end);
    }
}
