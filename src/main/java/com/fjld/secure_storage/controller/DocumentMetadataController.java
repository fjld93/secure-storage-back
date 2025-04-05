package com.fjld.secure_storage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fjld.secure_storage.dto.DocumentMetadataRequestDTO;
import com.fjld.secure_storage.dto.DocumentMetadataResponseDTO;
import com.fjld.secure_storage.service.DocumentMetadataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentMetadataController {
	
	private final DocumentMetadataService documentMetadataService;
	
	@PostMapping("/{documentUuid}/metadata")
    public ResponseEntity<DocumentMetadataResponseDTO> createMetadata(@PathVariable String documentUuid, 
            															@RequestBody DocumentMetadataRequestDTO request) {
        
        DocumentMetadataResponseDTO response = documentMetadataService.createMetadata(documentUuid, request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@PutMapping("/metadata/{metadataUuid}")
    public ResponseEntity<DocumentMetadataResponseDTO> updateMetadata(@PathVariable String metadataUuid, 
            															@RequestBody DocumentMetadataRequestDTO request) {
        
        DocumentMetadataResponseDTO response = documentMetadataService.updateMetadata(metadataUuid, request);
        
        return ResponseEntity.ok(response);
    }
	
	@DeleteMapping("/metadata/{metadataUuid}")
    public ResponseEntity<Void> deleteMetadata(@PathVariable String metadataUuid) {
        
        documentMetadataService.deleteMetadata(metadataUuid);
        
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/{documentUuid}/metadata")
    public ResponseEntity<List<DocumentMetadataResponseDTO>> getMetadataByDocument(@PathVariable String documentUuid) {
        
        List<DocumentMetadataResponseDTO> metadataList = documentMetadataService
        													.getMetadataByDocument(documentUuid);
        
        return ResponseEntity.ok(metadataList);
    }

}
