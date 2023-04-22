package com.device_manager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.device_manager.model.Employee;
import com.device_manager.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired EmployeeService employeeService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		return new ResponseEntity<Employee>(employeeService.findEmployeeById(id), HttpStatus.FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployee() {
		return new ResponseEntity<List<Employee>>(employeeService.findAllEmployee(), HttpStatus.FOUND);
	}
	
	@GetMapping("/paged")
	public ResponseEntity<Page<Employee>> getAllEmployee(Pageable pageable) {
		return new ResponseEntity<Page<Employee>>(employeeService.findAllEmployee(pageable), HttpStatus.FOUND);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addNewEmployee(@RequestBody Employee e) {
		return new ResponseEntity<String>(employeeService.persistEmployee(e), HttpStatus.CREATED);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> updateEmployee(@RequestBody Employee e) {
		return new ResponseEntity<String>(employeeService.updateEmployee(e), HttpStatus.OK);
	}
	
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteEmployee(@RequestBody Employee e) {
		return new ResponseEntity<String>(employeeService.deleteEmployee(e), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
		return new ResponseEntity<String>(employeeService.deleteEmployee(id), HttpStatus.OK);
	}
	
}
