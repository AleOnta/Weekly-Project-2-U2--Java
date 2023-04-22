package com.device_manager.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.device_manager.service.DeviceService;
import com.device_manager.service.EmployeeService;

@Component
public class DeviceManagerRunner implements ApplicationRunner {

	@Autowired EmployeeService e_service;
	@Autowired DeviceService d_service;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("run");

		//e_service.createFakeEmployee();
		//d_service.createFakeDevice();
		
		
		
	}

}
