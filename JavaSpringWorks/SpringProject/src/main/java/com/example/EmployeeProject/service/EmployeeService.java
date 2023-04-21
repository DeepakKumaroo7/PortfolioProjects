package com.example.demo.service;

import java.util.List;

import com.example.demo.Entity.Employee;

public interface EmployeeService {
	Employee saveEmployee(Employee employee);
	List<Employee>getAllEmployee();
	Employee getEmployeeById(Long id);
	Employee updateEmployee(Employee employee,long id);
	void deleteEmployee(long id);
	

}
