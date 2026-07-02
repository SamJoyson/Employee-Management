package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Task entity operations.
 * Provides custom finders such as by employee ID and by task status (Todo, In Progress, Completed).
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find tasks assigned to a specific employee.
     * @param employeeId The ID of the employee.
     * @return List of tasks.
     */
    List<Task> findByEmployeeId(Long employeeId);

    /**
     * Find tasks by their status.
     * @param status The status (Todo, In Progress, Completed).
     * @return List of matching tasks.
     */
    List<Task> findByStatus(String status);
}
