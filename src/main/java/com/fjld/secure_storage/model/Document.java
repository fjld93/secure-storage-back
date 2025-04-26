package com.fjld.secure_storage.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;
	
	@Basic
	private String name;
	private String description;
	private Long size;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_uuid", nullable = false)
	private User user;
	
	@OneToOne(mappedBy = "document", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private DocumentContent content;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "document", cascade = CascadeType.ALL)
	private List<DocumentMetadata> metadata;

}
