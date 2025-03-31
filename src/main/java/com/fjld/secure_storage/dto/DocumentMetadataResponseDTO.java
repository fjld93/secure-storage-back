package com.fjld.secure_storage.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentMetadataResponseDTO {
	
	private String uuid;
    private String name;
    private String value;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
