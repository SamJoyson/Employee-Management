package com.example.employeemanagement.service;

import com.example.employeemanagement.model.LeaveDto;
import java.util.List;

/**
 * Service interface defining CRUD operations and approval workflows for Leaves.
 */
public interface LeaveService {

    /**
     * Submit a new leave request.
     * @param leaveDto Leave request details.
     * @return Saved leave request.
     */
    LeaveDto applyLeave(LeaveDto leaveDto);

    /**
     * Retrieve all leave requests.
     * @return List of all leave requests.
     */
    List<LeaveDto> getAllLeaves();

    /**
     * Retrieve a specific leave request by ID.
     * @param leaveId Leave ID.
     * @return Leave details.
     */
    LeaveDto getLeaveById(Long leaveId);

    /**
     * Update details of a leave request.
     * @param leaveId Leave ID.
     * @param leaveDto Updated leave details.
     * @return Updated leave request.
     */
    LeaveDto updateLeave(Long leaveId, LeaveDto leaveDto);

    /**
     * Delete a leave request.
     * @param leaveId Leave ID.
     */
    void deleteLeave(Long leaveId);

    /**
     * Approve a pending leave request.
     * @param leaveId Leave ID.
     * @return Updated leave request with status Approved.
     */
    LeaveDto approveLeave(Long leaveId);

    /**
     * Reject a pending leave request.
     * @param leaveId Leave ID.
     * @return Updated leave request with status Rejected.
     */
    LeaveDto rejectLeave(Long leaveId);

    /**
     * Retrieve all leave requests submitted by a specific employee.
     * @param employeeId Employee ID.
     * @return List of leave requests.
     */
    List<LeaveDto> getLeavesByEmployee(Long employeeId);
}
