package com.device_manager.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.device_manager.model.Device;
import com.device_manager.model.E_DeviceStatus;
import com.device_manager.model.E_DeviceType;
import com.github.javafaker.Faker;

@Configuration
public class DeviceConfiguration {

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
	public Device paramsDevice() {
		Faker f = Faker.instance(new Locale("en-US"));
		int rand_one = f.number().numberBetween(1, 5);
		int rand_two = f.number().numberBetween(1, 5);
		return Device.builder().type(fakeType(rand_one)).status(fakeStatus(rand_two)).owner(null).build();
	}
	
	// TO MOVE IN SERVICE
	private E_DeviceType fakeType(int random) {
		E_DeviceType toReturn = null;
		switch(random) {
		case 1 -> toReturn = E_DeviceType.SMARTPHONE;
		case 2 -> toReturn = E_DeviceType.LAPTOP;
		case 3 -> toReturn = E_DeviceType.DESKTOP;
		case 4 -> toReturn = E_DeviceType.HARDDRIVE;
		case 5 -> toReturn = E_DeviceType.VEHICLE;
		}
		return toReturn;
	}
	
	private E_DeviceStatus fakeStatus(int random) {
		E_DeviceStatus toReturn = null;
		switch(random) {
		case 1 -> toReturn = E_DeviceStatus.AVAILABLE;
		case 2 -> toReturn = E_DeviceStatus.ASSIGNED;
		case 3 -> toReturn = E_DeviceStatus.ON_MAINTENANCE;
		case 4 -> toReturn = E_DeviceStatus.ABANDONED;
		}
		return toReturn;
	}
}
