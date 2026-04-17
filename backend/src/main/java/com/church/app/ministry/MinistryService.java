package com.church.app.ministry;

import com.church.app.common.TenantContext;
import com.church.app.member.Member;
import com.church.app.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MinistryService {

    private final MinistryRepository ministryRepository;
    private final MinistryMemberRepository ministryMemberRepository;
    private final MemberRepository memberRepository;

    public List<Ministry> getAll() {
        return ministryRepository.findByTenantId(TenantContext.getTenantId());
    }

    public Ministry getById(Long id) {
        return ministryRepository.findById(id)
                .filter(m -> m.getTenantId().equals(TenantContext.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Ministry not found"));
    }

    public Ministry create(Ministry ministry) {
        ministry.setId(null);
        ministry.setTenantId(TenantContext.getTenantId());
        return ministryRepository.save(ministry);
    }

    public Ministry update(Long id, Ministry updated) {
        Ministry existing = getById(id);
        existing.setName(updated.getName());
        return ministryRepository.save(existing);
    }

    public void delete(Long id) {
        Ministry ministry = getById(id);
        ministryRepository.delete(ministry);
    }

    public List<MinistryMember> getMembers(Long ministryId) {
        getById(ministryId); // validates tenant
        return ministryMemberRepository.findByMinistryId(ministryId);
    }

    public MinistryMember addMember(Long ministryId, Long memberId, String role) {
        Ministry ministry = getById(ministryId);
        Member member = memberRepository.findById(memberId)
                .filter(m -> m.getTenantId().equals(TenantContext.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MinistryMember mm = new MinistryMember();
        mm.setMinistry(ministry);
        mm.setMember(member);
        mm.setRole(role);
        return ministryMemberRepository.save(mm);
    }

    @Transactional
    public void removeMember(Long ministryId, Long memberId) {
        getById(ministryId); // validates tenant
        ministryMemberRepository.deleteByMinistryIdAndMemberId(ministryId, memberId);
    }

    public long countByTenant() {
        return ministryRepository.findByTenantId(TenantContext.getTenantId()).size();
    }
}
