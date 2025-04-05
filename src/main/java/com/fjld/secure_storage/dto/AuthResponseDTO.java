package com.fjld.secure_storage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AuthResponseDTO {
	
    private String token;

}
