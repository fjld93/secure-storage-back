package com.fjld.secure_storage.dto;

import com.fjld.secure_storage.model.DocumentContent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class DocumentContentDTO {
	
	private String name;
	private DocumentContent content;

}
