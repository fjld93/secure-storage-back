package com.fjld.secure_storage.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fjld.secure_storage.dto.DocumentRequestDTO;
import com.fjld.secure_storage.dto.DocumentResponseDTO;
import com.fjld.secure_storage.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

	private final DocumentService documentService;

	@PostMapping
	public ResponseEntity<DocumentResponseDTO> createDocument(@RequestParam MultipartFile file,
			@RequestParam String name, @RequestParam String description) {

		DocumentRequestDTO request = DocumentRequestDTO.builder().name(name).description(description).content(file)
				.build();

		DocumentResponseDTO response = documentService.createDocument(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{documentUuid}")
	public ResponseEntity<DocumentResponseDTO> getDocumentById(@PathVariable String documentUuid) {

		DocumentResponseDTO response = documentService.getDocumentById(documentUuid);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{documentUuid}")
	public ResponseEntity<DocumentResponseDTO> updateDocument(@PathVariable String documentUuid,
			@RequestBody DocumentRequestDTO request) {

		DocumentResponseDTO response = documentService.updateDocument(documentUuid, request);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{documentUuid}")
	public ResponseEntity<Void> deleteDocument(@PathVariable String documentUuid) {

		documentService.deleteDocument(documentUuid);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/user")
	public ResponseEntity<Page<DocumentResponseDTO>> getDocumentsByUser(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {

		Page<DocumentResponseDTO> response = documentService.getDocumentsByUser(page, size);

		return ResponseEntity.ok(response);
	}

}
