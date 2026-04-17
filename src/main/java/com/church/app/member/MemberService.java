package com.church.app.member;

import com.church.app.common.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getAll() {
        return memberRepository.findByTenantId(TenantContext.getTenantId());
    }

    public Member getById(Long id) {
        return memberRepository.findById(id)
                .filter(m -> m.getTenantId().equals(TenantContext.getTenantId()))
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public Member create(Member member) {
        member.setId(null);
        member.setTenantId(TenantContext.getTenantId());
        return memberRepository.save(member);
    }

    public Member update(Long id, Member updated) {
        Member existing = getById(id);
        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setBaptismDate(updated.getBaptismDate());
        existing.setMarriageDate(updated.getMarriageDate());
        existing.setStatus(updated.getStatus());
        return memberRepository.save(existing);
    }

    public void delete(Long id) {
        Member member = getById(id);
        memberRepository.delete(member);
    }

    public long countByTenant() {
        return memberRepository.findByTenantId(TenantContext.getTenantId()).size();
    }
}
