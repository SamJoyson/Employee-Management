package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.entity.Attendance;
import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.AttendanceNotFoundException;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.model.AttendanceDto;
import com.example.employeemanagement.repository.AttendanceRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.AttendanceService;
import com.example.employeemanagement.utility.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Attendance operations.
 * Implements business rules such as:
 * - One attendance record per employee per day.
 * - Auto-determining status (Present/Late) during check-in.
 * - Calculating working hours on check-out.
 */
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public AttendanceDto addAttendance(AttendanceDto attendanceDto) {
        Employee employee = employeeRepository.findById(attendanceDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + attendanceDto.getEmployeeId()));

        // Check if attendance already marked for the date
        Optional<Attendance> existing = attendanceRepository.findByEmployeeIdAndDate(
                attendanceDto.getEmployeeId(), attendanceDto.getDate());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Attendance already marked for employee on date: " + attendanceDto.getDate());
        }

        // Validate check-in and check-out times
        if (attendanceDto.getCheckIn() != null && attendanceDto.getCheckOut() != null) {
            if (attendanceDto.getCheckIn().isAfter(attendanceDto.getCheckOut())) {
                throw new IllegalArgumentException("Check-in time cannot be after check-out time");
            }
        }

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(attendanceDto.getDate())
                .checkIn(attendanceDto.getCheckIn())
                .checkOut(attendanceDto.getCheckOut())
                .status(attendanceDto.getStatus())
                .build();

        Attendance saved = attendanceRepository.save(attendance);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAllAttendance() {
        return attendanceRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDto getAttendanceById(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance record not found with id: " + attendanceId));
        return mapToDto(attendance);
    }

    @Override
    @Transactional
    public AttendanceDto updateAttendance(Long attendanceId, AttendanceDto attendanceDto) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance record not found with id: " + attendanceId));

        // Validate check-in and check-out times
        if (attendanceDto.getCheckIn() != null && attendanceDto.getCheckOut() != null) {
            if (attendanceDto.getCheckIn().isAfter(attendanceDto.getCheckOut())) {
                throw new IllegalArgumentException("Check-in time cannot be after check-out time");
            }
        }

        attendance.setCheckIn(attendanceDto.getCheckIn());
        attendance.setCheckOut(attendanceDto.getCheckOut());
        attendance.setStatus(attendanceDto.getStatus());
        if (attendanceDto.getDate() != null) {
            attendance.setDate(attendanceDto.getDate());
        }

        Attendance updated = attendanceRepository.save(attendance);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteAttendance(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance record not found with id: " + attendanceId));
        attendanceRepository.delete(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDto> getAttendanceByEmployee(Long employeeId) {
        // Check if employee exists
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
        return attendanceRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AttendanceDto checkIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        LocalDate today = LocalDate.now();
        Optional<Attendance> existing = attendanceRepository.findByEmployeeIdAndDate(employeeId, today);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Employee has already checked in today");
        }

        LocalTime now = LocalTime.now();
        // Determine status (Late if after 09:15:00)
        LocalTime lateLimit = LocalTime.of(9, 15);
        String status = now.isAfter(lateLimit) ? "Late" : "Present";

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(today)
                .checkIn(now)
                .status(status)
                .build();

        Attendance saved = attendanceRepository.save(attendance);
        return mapToDto(saved);
    }

    @Override
    @Transactional
    public AttendanceDto checkOut(Long employeeId) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, today)
                .orElseThrow(() -> new AttendanceNotFoundException("No check-in record found for today. Check-in first."));

        if (attendance.getCheckOut() != null) {
            throw new IllegalArgumentException("Employee has already checked out today");
        }

        LocalTime now = LocalTime.now();
        if (attendance.getCheckIn() != null && now.isBefore(attendance.getCheckIn())) {
            throw new IllegalArgumentException("Check-out time cannot be before check-in time");
        }

        attendance.setCheckOut(now);
        Attendance saved = attendanceRepository.save(attendance);
        return mapToDto(saved);
    }

    // Helper methods for DTO mapping
    private AttendanceDto mapToDto(Attendance entity) {
        String workingHours = null;
        if (entity.getCheckIn() != null && entity.getCheckOut() != null) {
            workingHours = DateUtils.calculateWorkingHours(entity.getCheckIn(), entity.getCheckOut());
        }

        return AttendanceDto.builder()
                .attendanceId(entity.getAttendanceId())
                .employeeId(entity.getEmployee().getId())
                .date(entity.getDate())
                .checkIn(entity.getCheckIn())
                .checkOut(entity.getCheckOut())
                .status(entity.getStatus())
                .workingHours(workingHours)
                .build();
    }
}
