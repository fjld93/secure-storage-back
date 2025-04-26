package com.fjld.secure_storage.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fjld.secure_storage.dto.DocumentRequestDTO;
import com.fjld.secure_storage.dto.DocumentResponseDTO;
import com.fjld.secure_storage.model.Document;
import com.fjld.secure_storage.model.DocumentContent;
import com.fjld.secure_storage.model.User;
import com.fjld.secure_storage.repository.DocumentContentRepository;
import com.fjld.secure_storage.repository.DocumentRepository;
import com.fjld.secure_storage.repository.UserRepository;
import com.fjld.secure_storage.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class DocumentService {
	
	private final DocumentRepository documentRepository;
	private final DocumentContentRepository documentContentRepository;
    private final UserRepository userRepository;
	private final DocumentMetadataService documentMetadataService;
    private final SecurityUtils securityUtils;
    
    @Transactional
    public DocumentResponseDTO createDocument(DocumentRequestDTO request) {
    	
    	String currentUsername = securityUtils.getCurrentUsername();
    	
        User user = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = Document.builder()
            .name(request.getName())
            .description(request.getDescription())
            .size(request.getContent().getSize())
            .createTime(LocalDateTime.now())
            .updateTime(LocalDateTime.now())
            .user(user)
            .build();

        Document savedDocument = documentRepository.save(document);
    	
    	DocumentContent documentContent;
    	
		try {
			documentContent = DocumentContent.builder()
			        .content(request.getContent().getBytes())
			        .document(savedDocument)
			        .build();
		} catch (IOException e) {
			throw new RuntimeException("Could not read the file content");
		}
    	
    	documentContentRepository.save(documentContent);
    	
        return mapToResponseDTO(savedDocument);
    }
    
    @Transactional(readOnly = true)
    public DocumentResponseDTO getDocumentById(String documentUuid) {
    	
    	String currentUsername = securityUtils.getCurrentUsername();
    	
        Document document = documentRepository.findById(documentUuid)
            .orElseThrow(() -> new RuntimeException("Document not found"));
        
        if (!document.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("You do not have permission to access to this document.");
        }

        return mapToResponseDTO(document);
    }
    
    @Transactional(readOnly = true)
    public Page<DocumentResponseDTO> getDocumentsByUser(int page, int size) {
    	
    	String currentUsername = securityUtils.getCurrentUsername();
    	
        User user = userRepository.findByUsername(currentUsername)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime").descending());
    	
        Page<Document> documents = documentRepository.findByUserUuid(user.getUuid(), pageable);    

        return documents.map(this::mapToResponseDTO);
    }
    
    @Transactional
    public DocumentResponseDTO updateDocument(String documentUuid, DocumentRequestDTO request) {
    	
        Document document = documentRepository.findById(documentUuid)
            .orElseThrow(() -> new RuntimeException("Document not found"));
        
        String currentUsername = securityUtils.getCurrentUsername();
        
        if (!document.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("You do not have permission to update this document.");
        }

        boolean updated = false;
        
        if (StringUtils.hasText(request.getName())
        	&& !request.getName().equals(document.getName())) {
            document.setName(request.getName());
            updated = true;
        }
        if (StringUtils.hasText(request.getDescription())
        	&& !request.getDescription().equals(document.getDescription())) {
            document.setDescription(request.getDescription());
            updated = true;
        }

        if (updated) {
            document.setUpdateTime(LocalDateTime.now());
            documentRepository.save(document);            
        }
        
        return mapToResponseDTO(document);
    }
    
    @Transactional
    public void deleteDocument(String documentUuid) {
    	
        Document document = documentRepository.findById(documentUuid)
            .orElseThrow(() -> new RuntimeException("Document not found"));
        
        String currentUsername = securityUtils.getCurrentUsername();

        if (!document.getUser().getUsername().equals(currentUsername)) {
            throw new RuntimeException("You do not have permission to delete this document.");
        }

        documentRepository.delete(document);
    }
    
    private DocumentResponseDTO mapToResponseDTO(Document document) {
    	
        return DocumentResponseDTO.builder()
            .uuid(document.getUuid())
            .name(document.getName())
            .description(document.getDescription())
            .size(document.getSize())
            .createTime(document.getCreateTime())
            .updateTime(document.getUpdateTime())
            .metadata(document.getMetadata() != null
            	? document.getMetadata().stream()
                .map(documentMetadataService::mapToResponseDTO)
                .toList()
                : List.of())
            .build();
    }

}
