package br.com.magportal.apimagspnewscadastro.dto.noticia;

import java.time.LocalDateTime;
import java.util.List;

import br.com.magportal.apimagspnewscadastro.entity.Tag;
import jakarta.validation.constraints.NotNull;

public class NoticiaDtoAtualizar {
	
	String siteBuscado;	
	String categoria;
	String autoria;
	@NotNull
	LocalDateTime dataModificada;
	String conteudo;
	String titulo;
	String sinopse;
	String imagemCapa;
	List<Tag> tags;


	public NoticiaDtoAtualizar(String siteBuscado, String categoria,
			String autoria, LocalDateTime dataModificada, String conteudo, String titulo,
			String sinopse, String imagemCapa, List<Tag> tags) {
	
		this.siteBuscado = siteBuscado;
		this.categoria = categoria;
		this.autoria = autoria;
		this.dataModificada = dataModificada;
		this.conteudo = conteudo;
		this.titulo = titulo;
		this.sinopse = sinopse;
		this.imagemCapa = imagemCapa;
		this.tags = tags;
	}

	public String getSiteBuscado() {
		return siteBuscado;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getAutoria() {
		return autoria;
	}

	public LocalDateTime getDataModificada() {
		return dataModificada;
	}

	public String getConteudo() {
		return  conteudo;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getSinopse() {
		return sinopse;
	}

	public String getImagemCapa() {
		return imagemCapa;
	}

	public List<Tag> getTags() {
		return tags;
	}

}
