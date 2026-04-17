package com.church.app.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByTenantId(Long tenantId);
    boolean existsByIdAndTenantId(Long id, Long tenantId);
}
