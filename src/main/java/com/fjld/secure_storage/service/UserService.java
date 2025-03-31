package com.fjld.secure_storage.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjld.secure_storage.dto.UserRequestDTO;
import com.fjld.secure_storage.dto.UserResponseDTO;
import com.fjld.secure_storage.model.User;
import com.fjld.secure_storage.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	//TODO creacion de usuario con password encriptada y utilizacion de JWT
	@Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {
        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(request.getPassword())
            .createTime(LocalDateTime.now())
            .build();

        User saved = userRepository.save(user);
        
        return mapToUserDTO(saved);
    }
	
	private UserResponseDTO mapToUserDTO(User user) {
		
        return UserResponseDTO.builder()
            .uuid(user.getUuid())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }

}
