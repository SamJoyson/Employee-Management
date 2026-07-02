package com.example.employeemanagement.service;

import com.example.employeemanagement.model.AttendanceDto;
import java.util.List;

/**
 * Service interface defining CRUD operations and custom actions for Attendance.
 */
public interface AttendanceService {

    /**
     * Log a new attendance record.
     * @param attendanceDto Attendance details.
     * @return Saved attendance record.
     */
    AttendanceDto addAttendance(AttendanceDto attendanceDto);

    /**
     * Get all attendance records in the system.
     * @return List of all attendance records.
     */
    List<AttendanceDto> getAllAttendance();

    /**
     * Retrieve a specific attendance record by ID.
     * @param attendanceId Attendance ID.
     * @return Attendance details.
     */
    AttendanceDto getAttendanceById(Long attendanceId);

    /**
     * Update an existing attendance record (e.g. check-out or status change).
     * @param attendanceId Attendance ID.
     * @param attendanceDto Updated details.
     * @return Updated attendance record.
     */
    AttendanceDto updateAttendance(Long attendanceId, AttendanceDto attendanceDto);

    /**
     * Delete an attendance record.
     * @param attendanceId Attendance ID.
     */
    void deleteAttendance(Long attendanceId);

    /**
     * Retrieve all attendance records for a specific employee.
     * @param employeeId Employee ID.
     * @return List of attendance records for the employee.
     */
    List<AttendanceDto> getAttendanceByEmployee(Long employeeId);

    /**
     * Record a check-in action for an employee today.
     * @param employeeId Employee ID.
     * @return Logged attendance record.
     */
    AttendanceDto checkIn(Long employeeId);

    /**
     * Record a check-out action for an employee today.
     * @param employeeId Employee ID.
     * @return Updated attendance record.
     */
    AttendanceDto checkOut(Long employeeId);
}
