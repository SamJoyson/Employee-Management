package com.example.employeemanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Reusable API Response wrapper class to standardize responses across all endpoints.
 * Includes a status code, a message, and optional response data.
 *
 * @param <T> The type of the payload data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Omit the 'data' field in JSON when it is null
public class ApiResponse<T> {

    private String message;
    private int status;
    private T data;

    /**
     * Constructor for responses without data (e.g., error responses or simple status updates).
     * @param message Description message of the response.
     * @param status HTTP status code.
     */
    public ApiResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }
}
