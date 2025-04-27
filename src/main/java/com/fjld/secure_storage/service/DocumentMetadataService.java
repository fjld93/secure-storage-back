package com.fjld.secure_storage.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fjld.secure_storage.dto.DocumentMetadataRequestDTO;
import com.fjld.secure_storage.dto.DocumentMetadataResponseDTO;
import com.fjld.secure_storage.exception.PermissionDeniedException;
import com.fjld.secure_storage.exception.ResourceNotFoundException;
import com.fjld.secure_storage.model.Document;
import com.fjld.secure_storage.model.DocumentMetadata;
import com.fjld.secure_storage.repository.DocumentMetadataRopository;
import com.fjld.secure_storage.repository.DocumentRepository;
import com.fjld.secure_storage.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class DocumentMetadataService {
	
	private final DocumentMetadataRopository metadataRepository;
    private final DocumentRepository documentRepository;
    private final SecurityUtils securityUtils;
    
    @Transactional
    public DocumentMetadataResponseDTO createMetadata(String documentUuid, DocumentMetadataRequestDTO request) {
        
        Document document = documentRepository.findById(documentUuid)
            .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
        
        String currentUsername = securityUtils.getCurrentUsername();
        
        if (currentUsername == null 
        		|| !document.getUser().getUsername().equals(currentUsername)) {
            throw new PermissionDeniedException("You do not have permission to modify this document");
        }
        
        DocumentMetadata metadata = DocumentMetadata.builder()
            .name(request.getName())
            .value(request.getValue())
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .document(document)
            .build();
        
        DocumentMetadata saved = metadataRepository.save(metadata);
        return mapToResponseDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public List<DocumentMetadataResponseDTO> getMetadataByDocument(String documentUuid) {
        
        Document document = documentRepository.findById(documentUuid)
            .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
        
        String currentUsername = securityUtils.getCurrentUsername();
        
        if (currentUsername == null 
        		|| !document.getUser().getUsername().equals(currentUsername)) {
            throw new PermissionDeniedException("You do not have permission to access this document's metadata");
        }
        
        List<DocumentMetadata> metadataList = metadataRepository.findByDocumentUuid(documentUuid);
        
        return metadataList.stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public DocumentMetadataResponseDTO updateMetadata(String metadataUuid, DocumentMetadataRequestDTO request) {
        
        DocumentMetadata metadata = metadataRepository.findById(metadataUuid)
            .orElseThrow(() -> new ResourceNotFoundException("Metadata not found"));
        
        String currentUsername = securityUtils.getCurrentUsername();
        
        if (currentUsername == null 
        		|| !metadata.getDocument().getUser().getUsername().equals(currentUsername)) {
            throw new PermissionDeniedException("You do not have permission to update this metadata");
        }
        
        boolean updated = false;
        
        if (StringUtils.hasText(request.getName()) && !request.getName().equals(metadata.getName())) {
            metadata.setName(request.getName());
            updated = true;
        }
        if (StringUtils.hasText(request.getValue()) && !request.getValue().equals(metadata.getValue())) {
            metadata.setValue(request.getValue());
            updated = true;
        }
        
        if (updated) {
            metadata.setUpdateTime(LocalDateTime.now());
            metadataRepository.save(metadata);
        }
        
        return mapToResponseDTO(metadata);
    }
    
    @Transactional
    public void deleteMetadata(String metadataUuid) {
        
        DocumentMetadata metadata = metadataRepository.findById(metadataUuid)
            .orElseThrow(() -> new ResourceNotFoundException("Metadata not found"));
        
        String currentUsername = securityUtils.getCurrentUsername();
        
        if (currentUsername == null 
        		|| !metadata.getDocument().getUser().getUsername().equals(currentUsername)) {
            throw new PermissionDeniedException("You do not have permission to delete this metadata");
        }
        
        metadataRepository.delete(metadata);
    }
    
    public DocumentMetadataResponseDTO mapToResponseDTO(DocumentMetadata metadata) {
    	
        return DocumentMetadataResponseDTO.builder()
            .uuid(metadata.getUuid())
            .name(metadata.getName())
            .value(metadata.getValue())
            .createTime(metadata.getCreateTime())
            .updateTime(metadata.getUpdateTime())
            .build();
    }

}
