package com.example.demo.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Employee;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}
	@Override
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return employeeRepository.save(employee);
	}
	@Override
	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}
	@Override
	public Employee getEmployeeById(Long id) {
		// TODO Auto-generated method stub
//		Optional<Employee>employee=employeeRepository.findById(id);
//		if(employee.isPresent()) {
//			return employee.get();
//		}else {
//			throw new ResourceNotFoundException("Employee","id",id);
//		}
//		
//	}
		return employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee","id", id));

	

}
	@Override
	public Employee updateEmployee(Employee employee, long id) {
		// TODO Auto-generated method stub
		//check whether employee with given id is existed
		Employee existingEmployee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee", "Id", id));
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		//save in database
		employeeRepository.save(existingEmployee);
		return existingEmployee;
	
	}
	@Override
	public void deleteEmployee(long id) {
		// TODO Auto-generated method stub
		//check whether id is existed
		employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee", "Id",id));
		employeeRepository.deleteById(id);
	}
	
}
