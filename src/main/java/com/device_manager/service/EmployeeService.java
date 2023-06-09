package com.device_manager.service;

import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.device_manager.model.Device;
import com.device_manager.model.Employee;
import com.device_manager.repository.EmployeePageableRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

	@Autowired @Qualifier("fakeEmployee")
	private ObjectProvider<Employee> fakeEmployee;
	
	@Autowired EmployeePageableRepository employeeRepo;
	
	@Autowired DeviceService deviceService;
	
	public String createFakeEmployee() {
		Employee e = fakeEmployee.getObject();
		return persistEmployee(e);
	}
	
	public String persistEmployee(Employee e) {
		if (!employeeRepo.existsByEmail(e.getEmail())) {
			employeeRepo.save(e);
			return "Employee correctly persisted on Database";
		} else {
			throw new EntityExistsException("Email already exists on Database!");
		}
	}
	
	public String updateEmployee(Employee e) {
		if (employeeRepo.existsById(e.getId())) {
			employeeRepo.save(e);
			return "Employee correctly updated on Database";
		} else {
			throw new EntityNotFoundException("Employee doesn't exists on Database!");
		}
	}
	
	public String deleteEmployee(Employee e) {
		if (employeeRepo.existsById(e.getId())) {
			if(e.getDevices().size() > 0) {
				throw new DataIntegrityViolationException("Failed to delete employee because has relations with some devices!");
			}
			employeeRepo.delete(e);
			return "Employee correctly removed from Database";
		} else {
			throw new EntityNotFoundException("Employee doesn't exists on Database!");
		}
	}
	
	public String deleteEmployee(Long id) {
		if (employeeRepo.existsById(id)) {
			if(employeeRepo.findById(id).get().getDevices().size() > 0) {
				throw new DataIntegrityViolationException("Failed to delete employee because has relations with some devices!");
			}
			employeeRepo.deleteById(id);
			return "Employee correctly removed from Database";
		} else {
			throw new EntityNotFoundException("Employee doesn't exists on Database!");
		}
	}
	
	public Employee findEmployeeById(Long id) {
		if (employeeRepo.existsById(id)) {
			return employeeRepo.findById(id).get();
		} else {
			throw new EntityNotFoundException("Employee doesn't exists on Database!");
		}
	}
	
	// Find by email , username etc...
	
	public List<Employee> findAllEmployee() {
		return (List<Employee>) employeeRepo.findAll();
	}
	
	public Page<Employee> findAllEmployee(Pageable pageable) {
		return (Page<Employee>) employeeRepo.findAll(pageable);
	}
}
