package com.church.app.ministry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinistryRepository extends JpaRepository<Ministry, Long> {
    List<Ministry> findByTenantId(Long tenantId);
}
