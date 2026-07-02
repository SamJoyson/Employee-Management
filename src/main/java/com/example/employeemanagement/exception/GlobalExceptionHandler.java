package com.example.employeemanagement.exception;

import com.example.employeemanagement.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

/**
 * Global Exception Handler to capture all system-wide exceptions
 * and return them in the standardized ApiResponse format.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle cases where a requested resource is not found (Employee, Attendance, Leave, Task).
     */
    @ExceptionHandler({
            EmployeeNotFoundException.class,
            AttendanceNotFoundException.class,
            LeaveNotFoundException.class,
            TaskNotFoundException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleNotFoundExceptions(RuntimeException ex) {
        ApiResponse<Void> response = new ApiResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle business validation violations (e.g. invalid status, date order violations, double check-in).
     */
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ApiResponse<Void>> handleBadRequestExceptions(RuntimeException ex) {
        ApiResponse<Void> response = new ApiResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle input validation errors from Jakarta Validation annotations (e.g., @NotBlank, @Email, @Pattern).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ApiResponse<Void> response = new ApiResponse<>("Validation Failed: " + errorMessage, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback handler for all other unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
