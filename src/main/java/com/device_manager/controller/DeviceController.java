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
import com.device_manager.model.Device;
import com.device_manager.model.E_DeviceStatus;
import com.device_manager.model.E_DeviceType;
import com.device_manager.service.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired DeviceService deviceService;
	
	@GetMapping("/{id}")
	public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
		return new ResponseEntity<Device>(deviceService.findDeviceById(id), HttpStatus.FOUND);
	}
	
	@GetMapping("/paged/type/{type}")
	public ResponseEntity<Page<Device>> findDeviceByType(@PathVariable E_DeviceType type, Pageable pageable) {
		return new ResponseEntity<Page<Device>>( deviceService.findDeviceByType(type, pageable), HttpStatus.FOUND);
	}
	
	@GetMapping("/paged/status/{status}")
	public ResponseEntity<Page<Device>> findDeviceByStatus(@PathVariable E_DeviceStatus status, Pageable pageable) {
		return new ResponseEntity<Page<Device>>( deviceService.findDeviceByStatus(status, pageable), HttpStatus.FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<Device>> getAllDevice() {
		return new ResponseEntity<List<Device>>(deviceService.findAllDevice(), HttpStatus.FOUND);
	}
	
	@GetMapping("/paged")
	public ResponseEntity<Page<Device>> getAllDevice(Pageable pageable) {
		return new ResponseEntity<Page<Device>>(deviceService.findAllDevice(pageable), HttpStatus.FOUND);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addNewDevice(@RequestBody Device d) {
		return new ResponseEntity<String>(deviceService.persistDevice(d), HttpStatus.CREATED);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<String> updateDevice(@RequestBody Device d) {
		return new ResponseEntity<String>(deviceService.updateDevice(d), HttpStatus.OK);
	}
	
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteDevice(@RequestBody Device d) {
		return new ResponseEntity<String>(deviceService.removeDevice(d), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteDevice(@PathVariable Long id) {
		return new ResponseEntity<String>(deviceService.removeDevice(id), HttpStatus.OK);
	}
	
}
