package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.exception.EmployeeDeletionException;
import com.example.rqchallenge.employees.exception.EmployeeNotFoundException;
import com.example.rqchallenge.employees.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployees_ShouldReturnEmployeeList() {
        Employee[] employees = {
                new Employee("1", "John Doe", "50000", "30"),
                new Employee("2", "Jane Smith", "60000", "25")
        };
        when(restTemplate.getForEntity(anyString(), eq(Employee[].class))).thenReturn(ResponseEntity.ok(employees));

        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(2, result.size());
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() {
        Employee employee = new Employee("1", "John Doe", "50000", "30");
        when(restTemplate.getForObject(anyString(), eq(Employee.class))).thenReturn(employee);

        Employee result = employeeService.getEmployeeById("1");
        assertEquals("John Doe", result.getEmployeeName());
    }

    @Test
    void getEmployeeById_ShouldThrowException_WhenNotFound() {
        when(restTemplate.getForObject(anyString(), eq(Employee.class))).thenReturn(null);

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById("1"));
    }

    @Test
    void createEmployee_ShouldReturnCreatedEmployee() {
        Employee employee = new Employee("1", "John Doe", "50000", "30");
        when(restTemplate.postForObject(anyString(), any(), eq(Employee.class))).thenReturn(employee);

        Employee result = employeeService.createEmployee("John Doe", "50000", "30");
        assertEquals("John Doe", result.getEmployeeName());
    }

    @Test
    void deleteEmployeeById_ShouldReturnSuccessMessage() {
        doNothing().when(restTemplate).delete(anyString());

        String result = employeeService.deleteEmployeeById("1");
        assertEquals("Deleted employee with ID: 1", result);
    }

    @Test
    void deleteEmployeeById_ShouldThrowException_WhenNotFound() {
        doThrow(new RuntimeException()).when(restTemplate).delete(anyString());

        assertThrows(EmployeeDeletionException.class, () -> employeeService.deleteEmployeeById("1"));
    }


}

