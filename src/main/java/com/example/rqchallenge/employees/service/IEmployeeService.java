package com.example.rqchallenge.employees.service;




import com.example.rqchallenge.employees.model.Employee;

import java.util.List;

public interface IEmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByNameSearch(String searchString);
    Employee getEmployeeById(String id);
    Integer getHighestSalaryOfEmployees();
    List<String> getTopTenHighestEarningEmployeeNames();
    Employee createEmployee(String name, String salary, String age);
    String deleteEmployeeById(String id);
}