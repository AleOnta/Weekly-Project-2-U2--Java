package com.device_manager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.device_manager.model.Employee;

public interface EmployeePageableRepository extends CrudRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {
	
	// default finders
	public Employee findByFirstname(String firstname);
	public Employee findByLastname(String lastname);
	public Employee findByEmail(String email);
	public Employee findByUsername(String username);
	
	// exists
	public boolean existsByEmail(String email);
	
	
}
