package com.fjld.secure_storage.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDTO {
	
	private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
