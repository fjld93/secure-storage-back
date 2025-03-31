package com.fjld.secure_storage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DocumentMetadataRequestDTO {
	
	private String name;
    private String value;
    private String userUuid;

}
