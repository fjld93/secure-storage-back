package com.fjld.secure_storage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fjld.secure_storage.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	Optional<User> findByUsername(String username);
	
	boolean existsByEmail(String email);
	
    boolean existsByUsername(String username);

}
