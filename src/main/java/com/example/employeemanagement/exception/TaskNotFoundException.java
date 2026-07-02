package com.example.employeemanagement.exception;

/**
 * Custom exception thrown when a Task record is not found.
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
