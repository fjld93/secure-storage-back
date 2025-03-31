package com.fjld.secure_storage.service;

import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjld.secure_storage.dto.DocumentContentDTO;
import com.fjld.secure_storage.model.DocumentContent;
import com.fjld.secure_storage.repository.DocumentContentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentContentService {
	
    private final DocumentContentRepository documentContentRepository;

    @Transactional(readOnly = true)
    public DocumentContentDTO getDocumentContent(String documentContentUuid) {
        
    	DocumentContent content = documentContentRepository.findById(documentContentUuid)
        		.orElseThrow(() -> new RuntimeException("Document not found"));
    	
    	String encodedContent = Base64.getEncoder().encodeToString(content.getContent());
    	
        return new DocumentContentDTO(encodedContent);
    }
    
}
