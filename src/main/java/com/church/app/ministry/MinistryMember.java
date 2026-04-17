package com.church.app.ministry;

import com.church.app.member.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ministry_members",
       uniqueConstraints = @UniqueConstraint(columnNames = {"ministry_id", "member_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinistryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ministry_id", nullable = false)
    private Ministry ministry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String role;
}
