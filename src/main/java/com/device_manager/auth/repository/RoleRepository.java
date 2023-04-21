package com.device_manager.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.device_manager.auth.entity.ERole;
import com.device_manager.auth.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByRoleName(ERole roleName);

}
