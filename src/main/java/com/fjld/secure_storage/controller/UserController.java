package com.fjld.secure_storage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fjld.secure_storage.dto.UserRequestDTO;
import com.fjld.secure_storage.dto.UserResponseDTO;
import com.fjld.secure_storage.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
    public ResponseEntity<UserResponseDTO> createDocument(@RequestBody UserRequestDTO request) {
		
        UserResponseDTO response = userService.createUser(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
