package com.example.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Data Transfer Object for Task.
 * Validates payloads for assigning tasks, updating task statuses, and fetching task summaries.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {

    private Long taskId;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotBlank(message = "Task title cannot be blank")
    @Size(min = 3, max = 150, message = "Task title must be between 3 and 150 characters")
    private String title;

    private String description;

    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "^(Low|Medium|High)$", message = "Priority must be either 'Low', 'Medium', or 'High'")
    private String priority;

    @NotNull(message = "Due date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    // Status: Todo, In Progress, Completed
    private String status = "Todo";
}
