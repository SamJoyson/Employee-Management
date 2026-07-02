package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Attendance entity operations.
 * Provides custom finders such as by employee ID, by status, and checking for a record on a specific date.
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Find attendance records for a specific employee.
     * @param employeeId The ID of the employee.
     * @return List of attendance records.
     */
    List<Attendance> findByEmployeeId(Long employeeId);

    /**
     * Find attendance records by status (Present, Absent, Late).
     * @param status The status to search.
     * @return List of matching attendance records.
     */
    List<Attendance> findByStatus(String status);

    /**
     * Find an attendance record for a specific employee on a specific date.
     * Used to enforce "one attendance per employee per day" business rule.
     * @param employeeId The ID of the employee.
     * @param date The attendance date.
     * @return Optional containing the attendance record if it exists.
     */
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
}
