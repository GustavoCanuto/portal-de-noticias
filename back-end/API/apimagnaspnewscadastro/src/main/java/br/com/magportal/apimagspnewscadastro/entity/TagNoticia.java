package br.com.magportal.apimagspnewscadastro.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "tb_tag_noticias")
@Entity(name = "TagsNoticia")
public class TagNoticia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_noticia")
	private Noticia noticia;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_tag")
	private Tag tags;
	
	
}
