package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.ApiResponse;
import com.example.employeemanagement.model.LeaveDto;
import com.example.employeemanagement.service.LeaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Leave Management endpoints.
 * Provides APIs for applying leaves, processing approvals/rejections, and querying leave history.
 */
@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    /**
     * Apply for a new leave.
     * @param leaveDto Leave details.
     * @return Saved leave record with 'Pending' status.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<LeaveDto>> applyLeave(@Valid @RequestBody LeaveDto leaveDto) {
        LeaveDto applied = leaveService.applyLeave(leaveDto);
        ApiResponse<LeaveDto> response = new ApiResponse<>("Leave applied successfully", 201, applied);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve all leaves in the system.
     * @return List of all leaves.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaveDto>>> getAllLeaves() {
        List<LeaveDto> leaves = leaveService.getAllLeaves();
        ApiResponse<List<LeaveDto>> response = new ApiResponse<>("Leave requests retrieved successfully", 200, leaves);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve details of a specific leave request.
     * @param id Leave ID.
     * @return Leave details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveDto>> getLeaveById(@PathVariable Long id) {
        LeaveDto leave = leaveService.getLeaveById(id);
        ApiResponse<LeaveDto> response = new ApiResponse<>("Leave request retrieved successfully", 200, leave);
        return ResponseEntity.ok(response);
    }

    /**
     * Update details of an existing leave request.
     * @param id Leave ID.
     * @param leaveDto Updated details.
     * @return Updated leave details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LeaveDto>> updateLeave(
            @PathVariable Long id, @Valid @RequestBody LeaveDto leaveDto) {
        LeaveDto updated = leaveService.updateLeave(id, leaveDto);
        ApiResponse<LeaveDto> response = new ApiResponse<>("Leave request updated successfully", 200, updated);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete/Cancel a leave request.
     * @param id Leave ID.
     * @return Status message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        ApiResponse<Void> response = new ApiResponse<>("Leave request deleted successfully", 200);
        return ResponseEntity.ok(response);
    }

    /**
     * Approve a pending leave request.
     * @param id Leave ID.
     * @return Approved leave request details.
     */
    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<LeaveDto>> approveLeave(@PathVariable Long id) {
        LeaveDto approved = leaveService.approveLeave(id);
        ApiResponse<LeaveDto> response = new ApiResponse<>("Leave approved successfully", 200, approved);
        return ResponseEntity.ok(response);
    }

    /**
     * Reject a pending leave request.
     * @param id Leave ID.
     * @return Rejected leave request details.
     */
    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiResponse<LeaveDto>> rejectLeave(@PathVariable Long id) {
        LeaveDto rejected = leaveService.rejectLeave(id);
        ApiResponse<LeaveDto> response = new ApiResponse<>("Leave rejected successfully", 200, rejected);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all leaves requested by a specific employee.
     * @param employeeId Employee ID.
     * @return List of leaves.
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<LeaveDto>>> getLeaveByEmployee(@PathVariable Long employeeId) {
        List<LeaveDto> leaves = leaveService.getLeavesByEmployee(employeeId);
        ApiResponse<List<LeaveDto>> response = new ApiResponse<>(
                "Leave requests for employee " + employeeId + " retrieved successfully", 200, leaves);
        return ResponseEntity.ok(response);
    }
}
