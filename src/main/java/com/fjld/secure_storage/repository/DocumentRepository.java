package com.fjld.secure_storage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fjld.secure_storage.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>{
	
	Page<Document> findByUserUuid(String userUuid, Pageable pageable);

}
