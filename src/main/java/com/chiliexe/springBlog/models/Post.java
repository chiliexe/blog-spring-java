package com.chiliexe.springBlog.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank(message = "Preencha o campo Título")
	private String title;
	
	@Column(nullable = false, unique = true)
	private String slug;

	@Column(columnDefinition = "TEXT", length = 150, nullable = false)
	@NotBlank(message = "Preencha o campo Resumo")
	@Min(value =  150, message = "Ocampo resumo deve conter 150 caracteres")
	private String summary;

	@Column(columnDefinition = "TEXT", nullable = false)
	@NotBlank(message = "Preencha o campo Conteúdo")
	private String content;

	private String image;
	
    @Column(columnDefinition = "boolean default false")
	private boolean published;
	
	private LocalDate createdAt;
	private LocalDate updatedAt;
	
	
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
	
	@OneToOne
	private Category category;
	
	@OneToOne
	private User user;
	

	public String getCreatedAt()
	{
		return DateTimeFormatter.ofPattern("ee MMM yyyy", new Locale("pt", "BR")).format(this.createdAt);
	}

	public String getUpdatedAt()
	{
		return DateTimeFormatter.ofPattern("ee MMM yyyy", new Locale("pt", "BR")).format(this.updatedAt);
	}
}
