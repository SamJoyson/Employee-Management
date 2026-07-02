package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.entity.Leave;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.LeaveNotFoundException;
import com.example.employeemanagement.model.LeaveDto;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.LeaveRepository;
import com.example.employeemanagement.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Leave requests and approval workflow.
 * Implements business rules such as:
 * - Validating leave start and end dates.
 * - Changing state of leave requests (Pending -> Approved/Rejected).
 */
@Service
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public LeaveDto applyLeave(LeaveDto leaveDto) {
        Employee employee = employeeRepository.findById(leaveDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + leaveDto.getEmployeeId()));

        // Validate leave dates
        if (leaveDto.getFromDate().isAfter(leaveDto.getToDate())) {
            throw new IllegalArgumentException("Leave start date (fromDate) cannot be after end date (toDate)");
        }

        Leave leave = Leave.builder()
                .employee(employee)
                .leaveType(leaveDto.getLeaveType())
                .fromDate(leaveDto.getFromDate())
                .toDate(leaveDto.getToDate())
                .reason(leaveDto.getReason())
                .status("Pending") // Always pending initially
                .build();

        Leave saved = leaveRepository.save(leave);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveDto> getAllLeaves() {
        return leaveRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public LeaveDto getLeaveById(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave request not found with id: " + leaveId));
        return mapToDto(leave);
    }

    @Override
    @Transactional
    public LeaveDto updateLeave(Long leaveId, LeaveDto leaveDto) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave request not found with id: " + leaveId));

        if (leaveDto.getFromDate() != null && leaveDto.getToDate() != null) {
            if (leaveDto.getFromDate().isAfter(leaveDto.getToDate())) {
                throw new IllegalArgumentException("Leave start date (fromDate) cannot be after end date (toDate)");
            }
            leave.setFromDate(leaveDto.getFromDate());
            leave.setToDate(leaveDto.getToDate());
        }

        leave.setLeaveType(leaveDto.getLeaveType());
        leave.setReason(leaveDto.getReason());
        
        // Allow updating status if explicitly passed and valid
        if (leaveDto.getStatus() != null && 
            (leaveDto.getStatus().equalsIgnoreCase("Pending") || 
             leaveDto.getStatus().equalsIgnoreCase("Approved") || 
             leaveDto.getStatus().equalsIgnoreCase("Rejected"))) {
            leave.setStatus(leaveDto.getStatus());
        }

        Leave updated = leaveRepository.save(leave);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave request not found with id: " + leaveId));
        leaveRepository.delete(leave);
    }

    @Override
    @Transactional
    public LeaveDto approveLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave request not found with id: " + leaveId));

        if (!leave.getStatus().equalsIgnoreCase("Pending")) {
            throw new IllegalStateException("Only pending leave requests can be approved. Current status: " + leave.getStatus());
        }

        leave.setStatus("Approved");
        Leave updated = leaveRepository.save(leave);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public LeaveDto rejectLeave(Long leaveId) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveNotFoundException("Leave request not found with id: " + leaveId));

        if (!leave.getStatus().equalsIgnoreCase("Pending")) {
            throw new IllegalStateException("Only pending leave requests can be rejected. Current status: " + leave.getStatus());
        }

        leave.setStatus("Rejected");
        Leave updated = leaveRepository.save(leave);
        return mapToDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveDto> getLeavesByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
        return leaveRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Helper methods for DTO mapping
    private LeaveDto mapToDto(Leave entity) {
        return LeaveDto.builder()
                .leaveId(entity.getLeaveId())
                .employeeId(entity.getEmployee().getId())
                .leaveType(entity.getLeaveType())
                .fromDate(entity.getFromDate())
                .toDate(entity.getToDate())
                .reason(entity.getReason())
                .status(entity.getStatus())
                .build();
    }
}
