package com.fjld.secure_storage.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fjld.secure_storage.dto.DocumentMetadataRequestDTO;
import com.fjld.secure_storage.dto.DocumentMetadataResponseDTO;
import com.fjld.secure_storage.model.Document;
import com.fjld.secure_storage.model.DocumentMetadata;
import com.fjld.secure_storage.repository.DocumentMetadataRopository;
import com.fjld.secure_storage.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class DocumentMetadataService {
	
	private final DocumentMetadataRopository metadataRepository;
    private final DocumentRepository documentRepository;
    
    @Transactional
    public DocumentMetadataResponseDTO createMetadata(String documentUuid, DocumentMetadataRequestDTO request) {
        
        Document document = documentRepository.findById(documentUuid)
            .orElseThrow(() -> new RuntimeException("Document not found"));
        
        if (!document.getUser().getUuid().equals(request.getUserUuid())) {
            throw new RuntimeException("You do not have permission to modify this document.");
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
    public List<DocumentMetadataResponseDTO> getMetadataByDocument(String documentUuid, String userUuid) {
        
        Document document = documentRepository.findById(documentUuid)
            .orElseThrow(() -> new RuntimeException("Document not found"));
        
        if (!document.getUser().getUuid().equals(userUuid)) {
            throw new RuntimeException("You do not have permission to access this document's metadata.");
        }
        
        List<DocumentMetadata> metadataList = metadataRepository.findByDocumentUuid(documentUuid);
        
        return metadataList.stream()
            .map(this::mapToResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public DocumentMetadataResponseDTO updateMetadata(String metadataUuid, DocumentMetadataRequestDTO request) {
        
        DocumentMetadata metadata = metadataRepository.findById(metadataUuid)
            .orElseThrow(() -> new RuntimeException("Metadata not found"));
        
        if (!metadata.getDocument().getUser().getUuid().equals(request.getUserUuid())) {
            throw new RuntimeException("You do not have permission to update this metadata.");
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
    public void deleteMetadata(String metadataUuid, String userUuid) {
        
        DocumentMetadata metadata = metadataRepository.findById(metadataUuid)
            .orElseThrow(() -> new RuntimeException("Metadata not found"));
        
        if (!metadata.getDocument().getUser().getUuid().equals(userUuid)) {
            throw new RuntimeException("You do not have permission to delete this metadata.");
        }
        
        metadataRepository.delete(metadata);
    }
    
    private DocumentMetadataResponseDTO mapToResponseDTO(DocumentMetadata metadata) {
    	
        return DocumentMetadataResponseDTO.builder()
            .uuid(metadata.getUuid())
            .name(metadata.getName())
            .value(metadata.getValue())
            .createTime(metadata.getCreateTime())
            .updateTime(metadata.getUpdateTime())
            .build();
    }

}
