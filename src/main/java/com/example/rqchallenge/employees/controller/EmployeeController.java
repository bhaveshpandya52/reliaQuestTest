package com.example.rqchallenge.employees.controller;


import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController implements IEmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        return ResponseEntity.ok(employeeService.getEmployeesByNameSearch(searchString));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(employeeService.getHighestSalaryOfEmployees());
    }

    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ok(employeeService.getTopTenHighestEarningEmployeeNames());
    }

    @PostMapping()
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        String name = (String) employeeInput.get("name");
        String salary = (String) employeeInput.get("salary");
        String age = (String) employeeInput.get("age");
        return ResponseEntity.ok(employeeService.createEmployee(name, salary, age));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.deleteEmployeeById(id));
    }
}