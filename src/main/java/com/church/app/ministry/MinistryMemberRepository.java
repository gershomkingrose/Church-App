package com.church.app.ministry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MinistryMemberRepository extends JpaRepository<MinistryMember, Long> {
    List<MinistryMember> findByMinistryId(Long ministryId);
    void deleteByMinistryIdAndMemberId(Long ministryId, Long memberId);
}
