package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.ApiResponse;
import com.example.employeemanagement.model.TaskDto;
import com.example.employeemanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Task Management endpoints.
 * Provides APIs for creating, updating, assigning, and tracking tasks.
 */
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * Create a new task and assign it to an employee.
     * @param taskDto Task details.
     * @return Saved task record.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@Valid @RequestBody TaskDto taskDto) {
        TaskDto created = taskService.createTask(taskDto);
        ApiResponse<TaskDto> response = new ApiResponse<>("Task created successfully", 201, created);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieve all tasks.
     * @return List of all tasks.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskDto>>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();
        ApiResponse<List<TaskDto>> response = new ApiResponse<>("Tasks retrieved successfully", 200, tasks);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve details of a specific task.
     * @param id Task ID.
     * @return Task details.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTaskById(@PathVariable Long id) {
        TaskDto task = taskService.getTaskById(id);
        ApiResponse<TaskDto> response = new ApiResponse<>("Task retrieved successfully", 200, task);
        return ResponseEntity.ok(response);
    }

    /**
     * Update details of an existing task.
     * @param id Task ID.
     * @param taskDto Updated details.
     * @return Updated task details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(
            @PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        TaskDto updated = taskService.updateTask(id, taskDto);
        ApiResponse<TaskDto> response = new ApiResponse<>("Task updated successfully", 200, updated);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a task.
     * @param id Task ID.
     * @return Status message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        ApiResponse<Void> response = new ApiResponse<>("Task deleted successfully", 200);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve tasks assigned to a specific employee.
     * @param employeeId Employee ID.
     * @return List of tasks.
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByEmployee(@PathVariable Long employeeId) {
        List<TaskDto> tasks = taskService.getTasksByEmployee(employeeId);
        ApiResponse<List<TaskDto>> response = new ApiResponse<>(
                "Tasks for employee " + employeeId + " retrieved successfully", 200, tasks);
        return ResponseEntity.ok(response);
    }

    /**
     * Update only the status of a task.
     * @param id Task ID.
     * @param status The new status (Todo, In Progress, Completed).
     * @return Updated task details.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TaskDto>> updateTaskStatus(
            @PathVariable Long id, @RequestParam String status) {
        TaskDto updated = taskService.updateTaskStatus(id, status);
        ApiResponse<TaskDto> response = new ApiResponse<>("Task status updated successfully", 200, updated);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve all pending tasks (tasks not yet 'Completed').
     * @return List of pending tasks.
     */
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getPendingTasks() {
        List<TaskDto> tasks = taskService.getPendingTasks();
        ApiResponse<List<TaskDto>> response = new ApiResponse<>("Pending tasks retrieved successfully", 200, tasks);
        return ResponseEntity.ok(response);
    }
}
