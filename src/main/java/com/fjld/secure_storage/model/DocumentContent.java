package com.fjld.secure_storage.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
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
public class DocumentContent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;
	
	@Lob
    @Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_uuid", nullable = false)
	private Document document;

}
