package com.example.employeemanagement.repository;

import com.example.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee entity operations.
 * Provides custom finders such as by department, by name (containing search), and by email.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employees by their exact department name.
     * @param department The department to search.
     * @return List of employees in that department.
     */
    List<Employee> findByDepartment(String department);

    /**
     * Search employees whose name contains the specified search term (case-insensitive contains).
     * @param name The search term.
     * @return List of matching employees.
     */
    List<Employee> findByNameContainingIgnoreCase(String name);

    /**
     * Find an employee by email address.
     * @param email The email address.
     * @return Optional containing the employee if found.
     */
    Optional<Employee> findByEmail(String email);
}
