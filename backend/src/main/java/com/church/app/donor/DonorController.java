package com.church.app.donor;

import com.church.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorService donorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Donor>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(donorService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Donor>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(donorService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Donor>> create(@RequestBody Donor donor) {
        return ResponseEntity.ok(ApiResponse.ok("Donor created", donorService.create(donor)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<Donor>> update(@PathVariable Long id, @RequestBody Donor donor) {
        return ResponseEntity.ok(ApiResponse.ok("Donor updated", donorService.update(id, donor)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        donorService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Donor deleted", null));
    }
}
