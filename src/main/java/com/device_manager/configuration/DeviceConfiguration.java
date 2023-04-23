package com.device_manager.configuration;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.device_manager.model.Device;
import com.device_manager.model.E_DeviceStatus;
import com.device_manager.model.E_DeviceType;
import com.device_manager.service.DeviceService;
import com.github.javafaker.Faker;

@Configuration
public class DeviceConfiguration {
	
	@Autowired private DeviceService d_service;

	@Bean(name = "customDevice")
	@Scope("singleton")
	public Device customDevice() {
		return new Device();
	}
	
	@Bean(name = "paramsDevice")
	@Scope("prototype")
	public Device paramsDevice(E_DeviceType type, E_DeviceStatus status) {
		return Device.builder().type(type).status(status).owner(null).build();
	}
	
	@Bean(name = "fakeDevice")
	@Scope("prototype")
	public Device fakeDevice() {
		Faker f = Faker.instance(new Locale("en-US"));
		int rand_one = f.number().numberBetween(1, 5);
		int rand_two = f.number().numberBetween(1, 4);
		Device d = Device.builder().type(d_service.fakeType(rand_one)).status(d_service.fakeStatus(rand_two)).owner(null).build();
		if (d.getStatus().equals(E_DeviceStatus.ASSIGNED)) {
			d = d_service.setOwnerIfAssigned(d);
		}
		return d;
	}
}
