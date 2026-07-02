package com.example.employeemanagement.service;

import com.example.employeemanagement.model.TaskDto;
import java.util.List;

/**
 * Service interface defining CRUD operations and status updates for Tasks.
 */
public interface TaskService {

    /**
     * Create/Assign a new task to an employee.
     * @param taskDto Task details.
     * @return Assigned task details.
     */
    TaskDto createTask(TaskDto taskDto);

    /**
     * Retrieve all tasks.
     * @return List of all tasks.
     */
    List<TaskDto> getAllTasks();

    /**
     * Retrieve a specific task by ID.
     * @param taskId Task ID.
     * @return Task details.
     */
    TaskDto getTaskById(Long taskId);

    /**
     * Update details of an existing task.
     * @param taskId Task ID.
     * @param taskDto Updated details.
     * @return Updated task details.
     */
    TaskDto updateTask(Long taskId, TaskDto taskDto);

    /**
     * Delete a task.
     * @param taskId Task ID.
     */
    void deleteTask(Long taskId);

    /**
     * Retrieve tasks assigned to a specific employee.
     * @param employeeId Employee ID.
     * @return List of tasks.
     */
    List<TaskDto> getTasksByEmployee(Long employeeId);

    /**
     * Update the status of a specific task.
     * @param taskId Task ID.
     * @param status New status (Todo, In Progress, Completed).
     * @return Updated task details.
     */
    TaskDto updateTaskStatus(Long taskId, String status);

    /**
     * Retrieve all pending tasks (status is 'Todo' or 'In Progress').
     * @return List of pending tasks.
     */
    List<TaskDto> getPendingTasks();
}
