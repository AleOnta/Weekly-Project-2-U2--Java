package com.device_manager.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.device_manager.service.DeviceService;
import com.device_manager.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeviceManagerRunner implements ApplicationRunner {

	@Autowired EmployeeService e_service;
	@Autowired DeviceService d_service;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		if (e_service.findAllEmployee().size() < 30) {
			log.info("***DEVICE MANAGER RUNNER STARTING -- WAIT FOR GREEN LIGHT***");
			log.warn("populating employees table...");
			for (int i = 0; i < 30; i++) {
				e_service.createFakeEmployee();
			}
			log.info("finished populating employees table...\n");
		}
		if (d_service.findAllDevice().size() < 50) {
			log.warn("populating devices table...");
			for (int j = 0; j < 50; j++) {
				d_service.createFakeDevice();			
			}
			log.info("finished populating devices table, system is ready! (greenLight)");
		}
		
	}

}
