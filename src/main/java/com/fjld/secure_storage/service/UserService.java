package com.fjld.secure_storage.service;

import java.time.LocalDateTime;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjld.secure_storage.dto.AuthRequestDTO;
import com.fjld.secure_storage.dto.AuthResponseDTO;
import com.fjld.secure_storage.dto.UserRequestDTO;
import com.fjld.secure_storage.model.Role;
import com.fjld.secure_storage.model.User;
import com.fjld.secure_storage.repository.RoleRepository;
import com.fjld.secure_storage.repository.UserRepository;
import com.fjld.secure_storage.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
	
	@Transactional
    public AuthResponseDTO createUser(UserRequestDTO request) {
		
		if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("The username already exist");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("The email already exist");
        }
        
        Role defaultRole = roleRepository.findByName("OWNER")
                .orElseThrow(() -> new IllegalStateException("Role OWNER doens't exists"));

        
        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(defaultRole)
            .createTime(LocalDateTime.now())
            .build();

        userRepository.save(user);
        
        String jwt = jwtUtils.generateToken(user.getUsername());
        
        return AuthResponseDTO.builder()
        		.token(jwt)
        		.build();
    }
	
	@Transactional(readOnly = true)
	public AuthResponseDTO login(AuthRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String jwt = jwtUtils.generateToken(user.getUsername());
        
        return AuthResponseDTO.builder().token(jwt).build();
    }	

}
