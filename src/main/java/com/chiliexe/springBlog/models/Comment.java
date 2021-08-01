package com.chiliexe.springBlog.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	@Size(max = 100)
	private String name;
	
	@Column(columnDefinition = "TEXT", length = 300, nullable = false)
	@NotBlank(message = "Preencha o campo conteúdo")
	private String content;
	
    @Column(columnDefinition = "boolean default true")
	private boolean published;
	
	@ManyToOne
	private Post post;
}
