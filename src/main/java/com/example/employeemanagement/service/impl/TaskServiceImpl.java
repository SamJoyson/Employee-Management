package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.entity.Task;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.exception.TaskNotFoundException;
import com.example.employeemanagement.model.TaskDto;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.repository.TaskRepository;
import com.example.employeemanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Task operations.
 * Implements CRUD operations, status updates, and tracking of pending tasks.
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        Employee employee = employeeRepository.findById(taskDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + taskDto.getEmployeeId()));

        Task task = Task.builder()
                .employee(employee)
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .dueDate(taskDto.getDueDate())
                .status(taskDto.getStatus() != null ? taskDto.getStatus() : "Todo")
                .build();

        Task saved = taskRepository.save(task);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDto getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        return mapToDto(task);
    }

    @Override
    @Transactional
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());
        
        if (taskDto.getStatus() != null) {
            validateStatus(taskDto.getStatus());
            task.setStatus(taskDto.getStatus());
        }

        Task updated = taskRepository.save(task);
        return mapToDto(updated);
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        taskRepository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> getTasksByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
        return taskRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskDto updateTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        validateStatus(status);
        task.setStatus(status);
        Task updated = taskRepository.save(task);
        return mapToDto(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> getPendingTasks() {
        return taskRepository.findAll().stream()
                .filter(task -> !task.getStatus().equalsIgnoreCase("Completed"))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Helper method to validate task status
    private void validateStatus(String status) {
        if (!status.equalsIgnoreCase("Todo") && 
            !status.equalsIgnoreCase("In Progress") && 
            !status.equalsIgnoreCase("Completed")) {
            throw new IllegalArgumentException("Invalid task status: " + status + ". Allowed values are Todo, In Progress, Completed.");
        }
    }

    // Helper methods for DTO mapping
    private TaskDto mapToDto(Task entity) {
        return TaskDto.builder()
                .taskId(entity.getTaskId())
                .employeeId(entity.getEmployee().getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .priority(entity.getPriority())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .build();
    }
}
