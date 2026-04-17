package com.church.app.attendance;

import com.church.app.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Attendance>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(attendanceService.getAll()));
    }

    @GetMapping("/ministry/{ministryId}")
    public ResponseEntity<ApiResponse<List<Attendance>>> getByMinistry(@PathVariable Long ministryId) {
        return ResponseEntity.ok(ApiResponse.ok(attendanceService.getByMinistry(ministryId)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ApiResponse<List<Attendance>>> markAttendance(@RequestBody List<Attendance> records) {
        return ResponseEntity.ok(ApiResponse.ok("Attendance marked", attendanceService.markAttendance(records)));
    }
}
