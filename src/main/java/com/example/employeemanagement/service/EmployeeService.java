package com.example.employeemanagement.service;

import com.example.employeemanagement.model.EmployeeDto;
import java.util.List;

/**
 * Service interface defining CRUD operations and custom searches for Employees.
 */
public interface EmployeeService {

    /**
     * Create a new employee record.
     * @param employeeDto DTO containing employee details.
     * @return Saved employee details as a DTO.
     */
    EmployeeDto addEmployee(EmployeeDto employeeDto);

    /**
     * Retrieve all employee records.
     * @return List of all employees.
     */
    List<EmployeeDto> getAllEmployees();

    /**
     * Retrieve a specific employee by their ID.
     * @param id Employee ID.
     * @return Employee details.
     */
    EmployeeDto getEmployeeById(Long id);

    /**
     * Update details of an existing employee.
     * @param id Employee ID.
     * @param employeeDto Updated details.
     * @return Updated employee details.
     */
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    /**
     * Delete an employee record.
     * @param id Employee ID.
     */
    void deleteEmployee(Long id);

    /**
     * Search employees by name (contains query).
     * @param name Name search term.
     * @return List of matching employees.
     */
    List<EmployeeDto> searchByName(String name);

    /**
     * Get all employees belonging to a specific department.
     * @param department Department name.
     * @return List of employees.
     */
    List<EmployeeDto> searchByDepartment(String department);
}
