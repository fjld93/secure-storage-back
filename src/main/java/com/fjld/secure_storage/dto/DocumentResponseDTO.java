package com.fjld.secure_storage.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentResponseDTO {
	
    private String uuid;
    private String name;
    private String description;
    private Long size;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
}
