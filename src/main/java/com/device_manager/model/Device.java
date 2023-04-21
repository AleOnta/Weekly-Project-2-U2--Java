package com.device_manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devices")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Device {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private E_DeviceType type;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private E_DeviceStatus status;
	
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee owner;
	
}
