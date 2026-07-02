package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Leave entity operations.
 * Provides custom finders such as by employee ID and by leave status (Pending, Approved, Rejected).
 */
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    /**
     * Find leave requests submitted by a specific employee.
     * @param employeeId The ID of the employee.
     * @return List of leave requests.
     */
    List<Leave> findByEmployeeId(Long employeeId);

    /**
     * Find leave requests by their status.
     * @param status The leave status (Pending, Approved, Rejected).
     * @return List of matching leave requests.
     */
    List<Leave> findByStatus(String status);
}
