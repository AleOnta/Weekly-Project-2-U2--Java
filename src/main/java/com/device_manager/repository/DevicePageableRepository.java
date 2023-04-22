package com.device_manager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.device_manager.model.Device;
import com.device_manager.model.E_DeviceStatus;
import com.device_manager.model.E_DeviceType;

public interface DevicePageableRepository extends CrudRepository<Device, Long>, PagingAndSortingRepository<Device, Long> {

	// default finders
	public Page<Device> findByStatus(E_DeviceStatus status, Pageable pageable);
	public Page<Device> findByType(E_DeviceType type, Pageable pageable);
	
}
