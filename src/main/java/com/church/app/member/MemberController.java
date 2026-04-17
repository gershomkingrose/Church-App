package com.church.app.member;

import com.church.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Member>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(memberService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Member>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Member>> create(@RequestBody Member member) {
        return ResponseEntity.ok(ApiResponse.ok("Member created", memberService.create(member)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Member>> update(@PathVariable Long id, @RequestBody Member member) {
        return ResponseEntity.ok(ApiResponse.ok("Member updated", memberService.update(id, member)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Member deleted", null));
    }
}
