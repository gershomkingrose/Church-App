package com.church.app.config;

import com.church.app.attendance.AttendanceService;
import com.church.app.common.ApiResponse;
import com.church.app.donation.DonationService;
import com.church.app.member.MemberService;
import com.church.app.ministry.MinistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final MemberService memberService;
    private final MinistryService ministryService;
    private final AttendanceService attendanceService;
    private final DonationService donationService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        long totalMembers = memberService.countByTenant();
        long totalMinistries = ministryService.countByTenant();
        long todayAttendance = attendanceService.countTodayPresent();
        BigDecimal monthlyDonations = donationService.getMonthlyTotal();

        Map<String, Object> data = Map.of(
                "totalMembers", totalMembers,
                "totalMinistries", totalMinistries,
                "todayAttendance", todayAttendance,
                "monthlyDonations", monthlyDonations
        );

        return ResponseEntity.ok(ApiResponse.ok(data));
    }
}
