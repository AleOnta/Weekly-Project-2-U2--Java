package com.device_manager.configuration;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.device_manager.model.Employee;
import com.github.javafaker.Faker;

@Configuration
public class EmployeeConfiguration {

	@Bean(name = "customEmployee")
	@Scope("singleton")
	public Employee customEmployee() {
		return new Employee();
	}
	
	@Bean(name = "paramsEmployee")
	@Scope("prototype")
	public Employee paramsEmployee(String firstname, String lastname, String username, String email) {
		return Employee.builder().firstname(firstname).lastname(lastname).username(username).email(email).build();
	}
	
	@Bean(name = "fakeEmployee")
	@Scope("prototype")
	public Employee paramsEmployee() {
		Faker f = Faker.instance(new Locale("en-US"));
		String firstname = f.name().firstName();
		String lastname = f.name().lastName();
		String username = firstname +"_"+lastname;
		String email = firstname.charAt(0) + "." + lastname + "@example.com";
		return Employee.builder().firstname(firstname).lastname(lastname).username(username).email(email).build();
	}
	
}
