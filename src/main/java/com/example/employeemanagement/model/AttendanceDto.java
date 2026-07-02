package com.example.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Transfer Object for Attendance.
 * Used for receiving check-in/check-out requests and returning detailed attendance data including calculated working hours.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDto {

    private Long attendanceId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Attendance date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime checkIn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime checkOut;

    @NotBlank(message = "Attendance status is required")
    @Pattern(regexp = "^(Present|Absent|Late)$", message = "Status must be either 'Present', 'Absent', or 'Late'")
    private String status;

    // Calculated read-only property showing duration between check-in and check-out
    private String workingHours;
}
