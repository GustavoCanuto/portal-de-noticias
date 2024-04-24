package br.com.magnasistemas.apimagnaspnews.dto.tags;

import java.io.Serializable;

import br.com.magnasistemas.apimagnaspnews.entity.Tag;

public class ListaTags implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long id;
	String nome;

	public ListaTags(Tag tag) {
		this(tag.getId(), tag.getNome());
	}

	public ListaTags(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
