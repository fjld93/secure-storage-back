package com.fjld.secure_storage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fjld.secure_storage.model.DocumentMetadata;

@Repository
public interface DocumentMetadataRopository extends JpaRepository<DocumentMetadata, String>{
	
	List<DocumentMetadata> findByDocumentUuid(String userUuid);

}
