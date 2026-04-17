package com.church.app.attendance;

import com.church.app.common.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public List<Attendance> getAll() {
        return attendanceRepository.findByTenantId(TenantContext.getTenantId());
    }

    public List<Attendance> getByMinistry(Long ministryId) {
        return attendanceRepository.findByTenantIdAndMinistryId(TenantContext.getTenantId(), ministryId);
    }

    @Transactional
    public List<Attendance> markAttendance(List<Attendance> records) {
        Long tenantId = TenantContext.getTenantId();
        records.forEach(a -> {
            a.setId(null);
            a.setTenantId(tenantId);
        });
        return attendanceRepository.saveAll(records);
    }

    public long countTodayPresent() {
        return attendanceRepository.countPresentByTenantAndDate(
                TenantContext.getTenantId(), LocalDate.now());
    }
}
