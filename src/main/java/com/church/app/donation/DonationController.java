package com.church.app.donation;

import com.church.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Donation>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(donationService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Donation>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(donationService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Donation>> create(@RequestBody Donation donation) {
        return ResponseEntity.ok(ApiResponse.ok("Donation recorded", donationService.create(donation)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Donation>> update(@PathVariable Long id, @RequestBody Donation donation) {
        return ResponseEntity.ok(ApiResponse.ok("Donation updated", donationService.update(id, donation)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        donationService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Donation deleted", null));
    }
}
