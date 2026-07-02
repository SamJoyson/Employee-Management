package com.example.employeemanagement.exception;

/**
 * Custom exception thrown when a Leave request record is not found.
 */
public class LeaveNotFoundException extends RuntimeException {
    public LeaveNotFoundException(String message) {
        super(message);
    }
}
