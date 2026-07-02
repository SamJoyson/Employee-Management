package com.example.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Data Transfer Object for Leave.
 * Used for receiving leave requests and returning leave request details with current status (Pending, Approved, Rejected).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveDto {

    private Long leaveId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotBlank(message = "Leave type is required")
    private String leaveType;

    @NotNull(message = "Start date (fromDate) is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @NotNull(message = "End date (toDate) is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    @NotBlank(message = "Reason for leave cannot be blank")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;

    // Default status: Pending. Allowed values: Pending, Approved, Rejected
    private String status = "Pending";
}
