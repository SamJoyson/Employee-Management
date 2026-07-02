package com.example.employeemanagement.utility;

/**
 * Utility class containing application constants.
 * Centralizes success messages, default values, validation regex, and status values.
 */
public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    // Attendance Statuses
    public static final String STATUS_PRESENT = "Present";
    public static final String STATUS_ABSENT = "Absent";
    public static final String STATUS_LATE = "Late";

    // Leave Statuses
    public static final String STATUS_LEAVE_PENDING = "Pending";
    public static final String STATUS_LEAVE_APPROVED = "Approved";
    public static final String STATUS_LEAVE_REJECTED = "Rejected";

    // Task Statuses
    public static final String STATUS_TASK_TODO = "Todo";
    public static final String STATUS_TASK_IN_PROGRESS = "In Progress";
    public static final String STATUS_TASK_COMPLETED = "Completed";

    // Task Priorities
    public static final String PRIORITY_LOW = "Low";
    public static final String PRIORITY_MEDIUM = "Medium";
    public static final String PRIORITY_HIGH = "High";

    // API Response Messages
    public static final String MSG_SUCCESS = "Success";
    public static final String MSG_NOT_FOUND = "Resource Not Found";
}
