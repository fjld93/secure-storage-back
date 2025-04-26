package com.fjld.secure_storage.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DocumentRequestDTO {
	
	private String name;
    private String description;
    private MultipartFile content;

}
