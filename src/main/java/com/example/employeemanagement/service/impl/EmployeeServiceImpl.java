package com.example.employeemanagement.service.impl;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.model.EmployeeDto;
import com.example.employeemanagement.repository.EmployeeRepository;
import com.example.employeemanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Employee operations.
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        // Check if email already exists
        if (employeeRepository.findByEmail(employeeDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Employee with email " + employeeDto.getEmail() + " already exists");
        }

        Employee employee = mapToEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToDto(savedEmployee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return mapToDto(employee);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        // Check email uniqueness if email is changed
        if (!employee.getEmail().equalsIgnoreCase(employeeDto.getEmail()) &&
                employeeRepository.findByEmail(employeeDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Employee with email " + employeeDto.getEmail() + " already exists");
        }

        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setDepartment(employeeDto.getDepartment());
        employee.setDesignation(employeeDto.getDesignation());
        employee.setJoiningDate(employeeDto.getJoiningDate());
        if (employeeDto.getIsActive() != null) {
            employee.setIsActive(employeeDto.getIsActive());
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToDto(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> searchByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> searchByDepartment(String department) {
        return employeeRepository.findByDepartment(department).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Helper methods for DTO mapping
    private Employee mapToEntity(EmployeeDto dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .joiningDate(dto.getJoiningDate())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : true)
                .build();
    }

    private EmployeeDto mapToDto(Employee entity) {
        return EmployeeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .department(entity.getDepartment())
                .designation(entity.getDesignation())
                .joiningDate(entity.getJoiningDate())
                .isActive(entity.getIsActive())
                .build();
    }
}
