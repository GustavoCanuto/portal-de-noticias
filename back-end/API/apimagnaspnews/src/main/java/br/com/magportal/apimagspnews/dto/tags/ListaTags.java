package br.com.magportal.apimagspnews.dto.tags;

import java.io.Serializable;

import br.com.magportal.apimagspnews.entity.Tag;

public class ListaTags implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;

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
