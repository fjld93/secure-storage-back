package com.fjld.secure_storage.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public ResponseEntity<ByteArrayResource> getDocumentContent(@PathVariable String documentUuid) {
		
		DocumentContentDTO content = documentContentService.getDocumentContent(documentUuid);
		ByteArrayResource resource = new ByteArrayResource(content.getContent().getContent());
        
        return ResponseEntity.ok()
        		.contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + content.getName() + "\"")
                .body(resource);
    }

}
