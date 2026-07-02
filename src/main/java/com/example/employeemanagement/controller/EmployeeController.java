package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.ApiResponse;
import com.example.employeemanagement.model.EmployeeDto;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee Management endpoints.
 * Handles adding, retrieving, updating, deleting, and searching employees.
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Add a new employee.
     * @param employeeDto The employee details to save.
     * @return Standardized ApiResponse wrapping the saved employee.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> addEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.addEmployee(employeeDto);
        ApiResponse<EmployeeDto> response = new ApiResponse<>("Employee added successfully", 201, savedEmployee);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve all employees.
     * @return Standardized ApiResponse wrapping the list of all employees.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        ApiResponse<List<EmployeeDto>> response = new ApiResponse<>("Employees retrieved successfully", 200, employees);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve an employee by ID.
     * @param id The employee ID.
     * @return Standardized ApiResponse wrapping the employee.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        ApiResponse<EmployeeDto> response = new ApiResponse<>("Employee retrieved successfully", 200, employee);
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing employee's details.
     * @param id The employee ID.
     * @param employeeDto The updated details.
     * @return Standardized ApiResponse wrapping the updated employee.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(
            @PathVariable Long id, @Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updated = employeeService.updateEmployee(id, employeeDto);
        ApiResponse<EmployeeDto> response = new ApiResponse<>("Employee updated successfully", 200, updated);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an employee.
     * @param id The employee ID.
     * @return Standardized ApiResponse message indicating success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        ApiResponse<Void> response = new ApiResponse<>("Employee deleted successfully", 200);
        return ResponseEntity.ok(response);
    }

    /**
     * Search employees by name (case-insensitive substring match).
     * @param name The search term.
     * @return Standardized ApiResponse wrapping the matched list.
     */
    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> searchByName(@RequestParam String name) {
        List<EmployeeDto> employees = employeeService.searchByName(name);
        ApiResponse<List<EmployeeDto>> response = new ApiResponse<>("Search results for name: " + name, 200, employees);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all employees in a specific department.
     * @param department The department name.
     * @return Standardized ApiResponse wrapping the employee list.
     */
    @GetMapping("/search/department")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> searchByDepartment(@RequestParam String department) {
        List<EmployeeDto> employees = employeeService.searchByDepartment(department);
        ApiResponse<List<EmployeeDto>> response = new ApiResponse<>("Employees in department: " + department, 200, employees);
        return ResponseEntity.ok(response);
    }
}
