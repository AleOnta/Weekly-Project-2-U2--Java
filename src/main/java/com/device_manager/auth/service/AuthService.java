package com.device_manager.auth.service;

import com.device_manager.auth.payload.LoginDto;
import com.device_manager.auth.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
