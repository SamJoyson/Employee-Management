package com.example.employeemanagement.exception;

/**
 * Custom exception thrown when an Attendance record is not found.
 */
public class AttendanceNotFoundException extends RuntimeException {
    public AttendanceNotFoundException(String message) {
        super(message);
    }
}
