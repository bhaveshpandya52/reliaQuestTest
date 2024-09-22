package com.example.rqchallenge.employees.controller;

// EmployeeControllerTest.java
import com.example.rqchallenge.employees.service.IEmployeeService;
import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private IEmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeList() {
        Employee employee = new Employee("1", "John Doe", "50000", "30");
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee));

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        Employee employee = new Employee("1", "John Doe", "50000", "30");
        when(employeeService.getEmployeeById("1")).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getEmployeeName());
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        Employee employee = new Employee("1", "John Doe", "50000", "30");
        HashMap<String, Object> employeeInput = new HashMap<>();
        employeeInput.put("name", "John Doe");
        employeeInput.put("salary", "50000");
        employeeInput.put("age", "30");

        when(employeeService.createEmployee("John Doe", "50000", "30")).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.createEmployee(employeeInput);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getEmployeeName());
    }

    @Test
    void deleteEmployeeById_ShouldReturnSuccessMessage() {
        when(employeeService.deleteEmployeeById("1")).thenReturn("Deleted employee with ID: 1");

        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Deleted employee with ID: 1", response.getBody());
    }


}
