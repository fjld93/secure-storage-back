package com.fjld.secure_storage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class DocumentMetadata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;
	
	@Basic
	private String name;
	private String value;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_uuid", nullable = false)
	private Document document;

}
