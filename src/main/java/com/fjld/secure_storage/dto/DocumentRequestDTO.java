package com.fjld.secure_storage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DocumentRequestDTO {
	
	private String name;
    private String description;
    private Integer size;
    private String content;
    private String userUuid;

}
