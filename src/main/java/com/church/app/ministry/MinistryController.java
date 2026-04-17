package com.church.app.ministry;

import com.church.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ministries")
@RequiredArgsConstructor
public class MinistryController {

    private final MinistryService ministryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Ministry>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(ministryService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Ministry>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(ministryService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Ministry>> create(@RequestBody Ministry ministry) {
        return ResponseEntity.ok(ApiResponse.ok("Ministry created", ministryService.create(ministry)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Ministry>> update(@PathVariable Long id, @RequestBody Ministry ministry) {
        return ResponseEntity.ok(ApiResponse.ok("Ministry updated", ministryService.update(id, ministry)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ministryService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Ministry deleted", null));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<ApiResponse<List<MinistryMember>>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(ministryService.getMembers(id)));
    }

    @PostMapping("/{id}/members")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<MinistryMember>> addMember(
            @PathVariable Long id, @RequestBody Map<String, Object> body) {
        Long memberId = Long.valueOf(body.get("memberId").toString());
        String role = body.getOrDefault("role", "").toString();
        return ResponseEntity.ok(ApiResponse.ok("Member added", ministryService.addMember(id, memberId, role)));
    }

    @DeleteMapping("/{id}/members/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> removeMember(@PathVariable Long id, @PathVariable Long memberId) {
        ministryService.removeMember(id, memberId);
        return ResponseEntity.ok(ApiResponse.ok("Member removed", null));
    }
}
