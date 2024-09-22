package com.example.rqchallenge.employees.service;



import com.example.rqchallenge.employees.exception.*;
import com.example.rqchallenge.employees.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Employee> getAllEmployees() {
        try {
            String url = BASE_URL + "/employees";
            ResponseEntity<Employee[]> response = restTemplate.getForEntity(url, Employee[].class);
            logger.info("Fetched all employees successfully");
            return Arrays.asList(Optional.ofNullable(response.getBody()).orElse(new Employee[0]));
        } catch (Exception e) {
            logger.error("Error fetching all employees: {}", e.getMessage());
            throw new EmployeeServiceException("Error fetching all employees");
        }
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        List<Employee> employees = getAllEmployees();
        List<Employee> filteredEmployees = employees.stream()
                .filter(emp -> emp.getEmployeeName().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
        logger.info("Employees fetched for search string: {}", searchString);
        return filteredEmployees;
    }

    @Override
    public Employee getEmployeeById(String id) {
        try {
            String url = BASE_URL + "/employee/" + id;
            Employee employee = restTemplate.getForObject(url, Employee.class);
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
            }
            logger.info("Employee fetched successfully with ID: {}", id);
            return employee;
        } catch (Exception e) {
            logger.error("Error fetching employee by ID {}: {}", id, e.getMessage());
            throw new EmployeeServiceException("Error fetching employee by ID " + id);
        }
    }

    @Override
    public Integer getHighestSalaryOfEmployees() {
        List<Employee> employees = getAllEmployees();
        int highestSalary = employees.stream()
                .mapToInt(emp -> Integer.parseInt(emp.getEmployeeSalary()))
                .max()
                .orElseThrow(() -> new EmployeeServiceException("No employees found to calculate highest salary"));
        logger.info("Highest salary fetched successfully: {}", highestSalary);
        return highestSalary;
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employees = getAllEmployees();
        List<String> topEmployees = employees.stream()
                .sorted((e1, e2) -> Integer.parseInt(e2.getEmployeeSalary()) - Integer.parseInt(e1.getEmployeeSalary()))
                .limit(10)
                .map(Employee::getEmployeeName)
                .collect(Collectors.toList());
        logger.info("Top 10 highest earning employees fetched successfully");
        return topEmployees;
    }

    @Override
    public Employee createEmployee(String name, String salary, String age) {
        try {
            String url = BASE_URL + "/create";
            Employee employee = new Employee();
            employee.setEmployeeName(name);
            employee.setEmployeeSalary(salary);
            employee.setEmployeeAge(age);
            Employee createdEmployee = restTemplate.postForObject(url, employee, Employee.class);
            logger.info("Employee created successfully: {}", createdEmployee);
            return createdEmployee;
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage());
            throw new EmployeeCreationException("Error creating employee");
        }
    }

    @Override
    public String deleteEmployeeById(String id) {
        try {
            String url = BASE_URL + "/delete/" + id;
            restTemplate.delete(url);
            logger.info("Employee deleted successfully with ID: {}", id);
            return "Deleted employee with ID: " + id;
        } catch (Exception e) {
            logger.error("Error deleting employee with ID {}: {}", id, e.getMessage());
            throw new EmployeeDeletionException("Employee with ID " + id + " not found or already deleted");
        }
    }
}