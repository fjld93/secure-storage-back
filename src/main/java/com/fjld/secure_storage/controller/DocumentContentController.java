package com.fjld.secure_storage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fjld.secure_storage.dto.DocumentContentDTO;
import com.fjld.secure_storage.service.DocumentContentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentContentController {
	
	private final DocumentContentService documentContentService;
	
	@GetMapping("/{documentUuid}/content")
    public ResponseEntity<DocumentContentDTO> getDocumentContent(@PathVariable String documentContentUuid) {
		
        DocumentContentDTO contentDTO = documentContentService.getDocumentContent(documentContentUuid);
        
        return ResponseEntity.ok(contentDTO);
    }

}
