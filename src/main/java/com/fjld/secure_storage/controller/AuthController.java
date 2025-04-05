package com.fjld.secure_storage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fjld.secure_storage.dto.AuthRequestDTO;
import com.fjld.secure_storage.dto.AuthResponseDTO;
import com.fjld.secure_storage.dto.UserRequestDTO;
import com.fjld.secure_storage.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	
	@PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody UserRequestDTO request) {
		
		AuthResponseDTO response = userService.createUser(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
		
		AuthResponseDTO response = userService.login(request);
		
        return ResponseEntity.ok(response);
    }

}
