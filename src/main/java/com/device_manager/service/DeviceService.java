package com.device_manager.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import com.device_manager.model.Device;
import com.device_manager.model.E_DeviceStatus;
import com.device_manager.model.E_DeviceType;
import com.device_manager.model.Employee;
import com.device_manager.repository.DevicePageableRepository;
import com.device_manager.repository.EmployeePageableRepository;
import com.github.javafaker.Faker;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeviceService {

	@Autowired 
	private DevicePageableRepository deviceRepo;
	
	@Autowired private EmployeePageableRepository employeeRepo;
		
	@Autowired @Qualifier("fakeDevice") 
	private ObjectProvider<Device> fakeDeviceProvider;
	
	// internal methods for beans
	
	public E_DeviceType fakeType(int random) {
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
	
	public E_DeviceStatus fakeStatus(int random) {
		E_DeviceStatus toReturn = null;
		switch(random) {
		case 1 -> toReturn = E_DeviceStatus.AVAILABLE;
		case 2 -> toReturn = E_DeviceStatus.ASSIGNED;
		case 3 -> toReturn = E_DeviceStatus.ON_MAINTENANCE;
		case 4 -> toReturn = E_DeviceStatus.ABANDONED;
		}
		return toReturn;
	}
	
	public Device setOwnerIfAssigned(Device d) {
		Faker f = Faker.instance(new Locale("en-US"));
		List<Employee> eList = (List<Employee>) employeeRepo.findAll();
		Employee newOwner = eList.get(f.number().numberBetween(0, (eList.size()-1)));
		d.setOwner(newOwner);
		return d;
	}
	
	// JPA methods
	
	public String createFakeDevice() {
		Device d = fakeDeviceProvider.getObject();
		return persistDevice(d);
	}
	
	public String persistDevice(Device d) {
		deviceRepo.save(d);
		return "Device correctly persisted on Database!";
	}
	
	public String updateDevice(Device d) {
		if (deviceRepo.existsById(d.getId())) {
			deviceRepo.save(d);
			return "Device correctly updated on Database!";
		} else {
			throw new EntityNotFoundException("Device doesn't exists on database!");
		}
	}
	
	public String removeDevice(Long id) {
		if (deviceRepo.existsById(id)) {
			deviceRepo.deleteById(id);
			return "Device correctly removed from Database!";
		} else {
			throw new EntityNotFoundException("Device doesn't exists on database!");
		} 
	}
	
	public String removeDevice(Device d) {
		if (deviceRepo.existsById(d.getId())) {
			deviceRepo.delete(d);
			return "Device correctly removed from Database!";
		} else {
			throw new EntityNotFoundException("Device doesn't exists on database!");
		} 
	}
	
	public Device findDeviceById(Long id) {
		if (deviceRepo.existsById(id)) {
			return deviceRepo.findById(id).get();
		} else {
			throw new EntityNotFoundException("Device doesn't exists on database!");
		}
	}
	
	public Page<Device> findDeviceByStatusAvailable(Pageable pageable) {
		return deviceRepo.findByStatus(E_DeviceStatus.AVAILABLE, pageable);
	}
	
	public Page<Device> findDeviceByType(E_DeviceType type, Pageable pageable) {
		return deviceRepo.findByType(type, pageable);
	}
	
	public Page<Device> findDeviceByStatus(E_DeviceStatus status, Pageable pageable) {
		return deviceRepo.findByStatus(status, pageable);
	}
	
	public List<Device> findAllDevice() {
		return (List<Device>) deviceRepo.findAll();
	}
	
	public Page<Device> findAllDevice(Pageable pageable) {
		return (Page<Device>) deviceRepo.findAll(pageable);
	}
	
	
	public String linkDeviceToEmployee(Long employeeId, Long deviceId) {
		if (employeeRepo.existsById(employeeId) && deviceRepo.existsById(deviceId)) {
			Employee e = employeeRepo.findById(employeeId).get(); 
			Device d =deviceRepo.findById(deviceId).get(); 
			if (d.getStatus().equals(E_DeviceStatus.ASSIGNED)) { 
				throw new DataIntegrityViolationException("Device is already assigned to an another Employee!");
			} else if (d.getStatus().equals(E_DeviceStatus.ABANDONED)) {
				throw new DataIntegrityViolationException("Device has already been abandoned, impossible to continue!");
			}
			e.getDevices().add(d); 
			employeeRepo.save(e);
			d.setStatus(E_DeviceStatus.ASSIGNED); 
			d.setOwner(e); 
			updateDevice(d); 
			return "Device with ID: " + deviceId + " has been correctly assigned to employee with ID: " + employeeId; 
		} else if (!deviceRepo.existsById(deviceId) && !employeeRepo.existsById(employeeId)) {  
			throw new EntityNotFoundException("Both Employee & Device doesn't exists on Database!"); 
		} else if (!employeeRepo.existsById(employeeId)) {
		  	throw new EntityNotFoundException("Employee with ID: " + employeeId +" doesn't exists on Database!"); 
		} else {   
			throw new EntityNotFoundException("Device with ID: " + deviceId + " doesn't exists on Database!"); 
		} 
	}
	 
	public String unlinkDeviceFromEmployee(Long deviceId) {
		if (deviceRepo.existsById(deviceId)) {
			Device d = findDeviceById(deviceId);
			try {
				d.getOwner().equals(null); // this is used only to invoke NullPointerException in case of null, and send custom exception
				d.setOwner(null);
				updateDevice(d);
				return "Device correctly removed from Employee";
			} catch (NullPointerException e) {
				if (d.getStatus().equals(E_DeviceStatus.AVAILABLE)) {
					throw new NullPointerException("Device is already AVAILABLE, no owner was found!");
				} else if (d.getStatus().equals(E_DeviceStatus.ON_MAINTENANCE)) {
					throw new NullPointerException("Device is currently under MAINTENANCE, no owner was found!");
				} else {
					throw new NullPointerException("Device has been ABANDONED, no Employee can borrow it anymore!");
				}
			}
		} else {
			throw new EntityNotFoundException("Device doesn't exists on Database!");
		}
	}
}
