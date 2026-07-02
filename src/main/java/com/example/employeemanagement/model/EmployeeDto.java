package com.example.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Data Transfer Object for Employee.
 * Handles validation of request bodies for adding and updating employees.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    private Long id;

    @NotBlank(message = "Employee name cannot be blank")
    @Size(min = 2, max = 100, message = "Employee name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Employee email cannot be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Employee phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must contain between 10 and 15 digits and can optionally start with '+'")
    private String phone;

    @NotBlank(message = "Department cannot be blank")
    private String department;

    @NotBlank(message = "Designation cannot be blank")
    private String designation;

    @NotNull(message = "Joining date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    private Boolean isActive = true;
}
