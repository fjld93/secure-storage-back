package com.fjld.secure_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fjld.secure_storage.model.DocumentContent;

@Repository
public interface DocumentContentRepository extends JpaRepository<DocumentContent, String>{

}
