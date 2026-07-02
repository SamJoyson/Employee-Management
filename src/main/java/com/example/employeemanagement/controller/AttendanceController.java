package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.ApiResponse;
import com.example.employeemanagement.model.AttendanceDto;
import com.example.employeemanagement.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Attendance Management endpoints.
 * Provides APIs for logging attendance, getting records, and executing check-in/check-out events.
 */
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Add a manual attendance record.
     * @param attendanceDto Attendance details.
     * @return Saved attendance record.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceDto>> addAttendance(@Valid @RequestBody AttendanceDto attendanceDto) {
        AttendanceDto saved = attendanceService.addAttendance(attendanceDto);
        ApiResponse<AttendanceDto> response = new ApiResponse<>("Attendance logged successfully", 201, saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve all attendance records.
     * @return List of all attendance records.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceDto>>> getAllAttendance() {
        List<AttendanceDto> records = attendanceService.getAllAttendance();
        ApiResponse<List<AttendanceDto>> response = new ApiResponse<>("Attendance records retrieved successfully", 200, records);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve a specific attendance record by ID.
     * @param id The attendance record ID.
     * @return Attendance details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceDto>> getAttendanceById(@PathVariable Long id) {
        AttendanceDto attendance = attendanceService.getAttendanceById(id);
        ApiResponse<AttendanceDto> response = new ApiResponse<>("Attendance record retrieved successfully", 200, attendance);
        return ResponseEntity.ok(response);
    }

    /**
     * Update an attendance record.
     * @param id The attendance record ID.
     * @param attendanceDto Updated details.
     * @return Updated attendance record.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceDto>> updateAttendance(
            @PathVariable Long id, @Valid @RequestBody AttendanceDto attendanceDto) {
        AttendanceDto updated = attendanceService.updateAttendance(id, attendanceDto);
        ApiResponse<AttendanceDto> response = new ApiResponse<>("Attendance record updated successfully", 200, updated);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an attendance record.
     * @param id The attendance record ID.
     * @return Status message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        ApiResponse<Void> response = new ApiResponse<>("Attendance record deleted successfully", 200);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all attendance records for a specific employee.
     * @param employeeId Employee ID.
     * @return List of employee attendance records.
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<AttendanceDto>>> getAttendanceByEmployee(@PathVariable Long employeeId) {
        List<AttendanceDto> records = attendanceService.getAttendanceByEmployee(employeeId);
        ApiResponse<List<AttendanceDto>> response = new ApiResponse<>(
                "Attendance records for employee " + employeeId + " retrieved successfully", 200, records);
        return ResponseEntity.ok(response);
    }

    /**
     * Check-in an employee for today.
     * @param employeeId Employee ID.
     * @return Logged attendance record.
     */
    @PostMapping("/checkin/{employeeId}")
    public ResponseEntity<ApiResponse<AttendanceDto>> checkIn(@PathVariable Long employeeId) {
        AttendanceDto record = attendanceService.checkIn(employeeId);
        ApiResponse<AttendanceDto> response = new ApiResponse<>("Checked in successfully", 200, record);
        return ResponseEntity.ok(response);
    }

    /**
     * Check-out an employee for today.
     * @param employeeId Employee ID.
     * @return Updated attendance record with check-out time and working hours calculated.
     */
    @PostMapping("/checkout/{employeeId}")
    public ResponseEntity<ApiResponse<AttendanceDto>> checkOut(@PathVariable Long employeeId) {
        AttendanceDto record = attendanceService.checkOut(employeeId);
        ApiResponse<AttendanceDto> response = new ApiResponse<>("Checked out successfully", 200, record);
        return ResponseEntity.ok(response);
    }
}
