package com.church.app.attendance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByTenantId(Long tenantId);
    List<Attendance> findByTenantIdAndMinistryId(Long tenantId, Long ministryId);
    List<Attendance> findByTenantIdAndDate(Long tenantId, LocalDate date);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.tenantId = :tenantId AND a.date = :date AND a.status = 'PRESENT'")
    long countPresentByTenantAndDate(Long tenantId, LocalDate date);
}
