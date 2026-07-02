package com.example.employeemanagement.exception;

/**
 * Custom exception thrown when an Employee record is not found.
 */
public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
