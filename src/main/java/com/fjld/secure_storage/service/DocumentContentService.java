package com.fjld.secure_storage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjld.secure_storage.dto.DocumentContentDTO;
import com.fjld.secure_storage.model.Document;
import com.fjld.secure_storage.repository.DocumentRepository;
import com.fjld.secure_storage.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentContentService {

	private final DocumentRepository documentRepository;
	private final SecurityUtils securityUtils;

	@Transactional(readOnly = true)
	public DocumentContentDTO getDocumentContent(String documentUuid) {

		String currentUsername = securityUtils.getCurrentUsername();

		Document document = documentRepository.findById(documentUuid)
				.orElseThrow(() -> new RuntimeException("Document not found"));

		if (!document.getUser().getUsername().equals(currentUsername)) {
			throw new RuntimeException("You do not have permission to access to this document.");
		}

		return mapToContentDTO(document);
	}

	private DocumentContentDTO mapToContentDTO(Document document) {

		return DocumentContentDTO.builder()
				.name(document.getName())
				.content(document.getContent())
				.build();
	}

}
