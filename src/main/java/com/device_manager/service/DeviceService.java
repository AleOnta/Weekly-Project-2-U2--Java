package com.device_manager.service;

import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.device_manager.model.Device;
import com.device_manager.model.E_DeviceStatus;
import com.device_manager.model.E_DeviceType;
import com.device_manager.model.Employee;
import com.device_manager.repository.DevicePageableRepository;
import com.device_manager.repository.EmployeePageableRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class DeviceService {

	@Autowired 
	private DevicePageableRepository deviceRepo;
	
	
	 @Autowired private EmployeePageableRepository employeeRepo;
	 
	 //@Autowired private EmployeeService e_service;
	
	
	@Autowired @Qualifier("fakeDevice") 
	private ObjectProvider<Device> fakeDeviceProvider;
	
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
				  throw new EntityExistsException("Device is already assigned to an another Employee!");
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
	 
}
