package com.church.app.donor;

import com.church.app.common.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DonorService {

    private final DonorRepository donorRepository;

    public List<Donor> getAll() {
        return donorRepository.findByTenantId(TenantContext.getTenantId());
    }

    public Donor getById(Long id) {
        return donorRepository.findById(id)
                .filter(d -> d.getTenantId().equals(TenantContext.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Donor not found"));
    }

    public Donor create(Donor donor) {
        donor.setId(null);
        donor.setTenantId(TenantContext.getTenantId());
        return donorRepository.save(donor);
    }

    public Donor update(Long id, Donor updated) {
        Donor existing = getById(id);
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        return donorRepository.save(existing);
    }

    public void delete(Long id) {
        Donor donor = getById(id);
        donorRepository.delete(donor);
    }
}
