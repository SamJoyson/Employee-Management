package com.example.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Entity representing a Task assigned to an Employee.
 * Tracks task details, priority (Low, Medium, High), due date, and status (Todo, In Progress, Completed).
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    // Many-to-One relationship to Employee
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Priority: Low, Medium, High
    @Column(nullable = false)
    private String priority;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    // Status: Todo, In Progress, Completed
    @Column(nullable = false)
    private String status = "Todo";
}
