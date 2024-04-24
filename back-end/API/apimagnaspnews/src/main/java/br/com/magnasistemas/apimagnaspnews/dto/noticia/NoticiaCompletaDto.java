package br.com.magnasistemas.apimagnaspnews.dto.noticia;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import br.com.magnasistemas.apimagnaspnews.dto.tags.ListaTags;
import br.com.magnasistemas.apimagnaspnews.entity.Noticia;
import br.com.magnasistemas.apimagnaspnews.entity.Tag;

public class NoticiaCompletaDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long id;


	String siteBuscado;
	String linkDaNoticiaOficial;
	String categoria;
	String autoria;
	LocalDateTime dataPublicacao;
	LocalDateTime dataModificada;
	String conteudo;
	String titulo;
	String sinopse;
	String imagemCapa;
	Long numeroVisualizacao;

	List<ListaTags> tags;

	public NoticiaCompletaDto(Noticia noticia) {
		this(noticia.getId(),
				noticia.getSiteBuscado().toUpperCase(),
				noticia.getLinkDaNoticiaOficial(),
				noticia.getCategoria(),
				noticia.getAutoria(),
				noticia.getDataPublicacao(),
				noticia.getDataModificada(),
				noticia.getConteudo(),
				noticia.getTitulo(),
				noticia.getSinopse(),
				noticia.getImagemCapa(),
				noticia.getNumeroVisualizacao(),
				noticia.getTags()
		
				);

	}
	public NoticiaCompletaDto(Long id, String siteBuscado, String linkDaNoticiaOficial, String categoria,
			String autoria, LocalDateTime dataPublicacao, LocalDateTime dataModificada, String conteudo, String titulo,
			String sinopse, String imagemCapa, Long numeroVisualizacao, List<Tag> tags) {
		this.id = id;
		this.siteBuscado = siteBuscado;
		this.linkDaNoticiaOficial = linkDaNoticiaOficial;
		this.categoria = categoria;
		this.autoria = autoria;
		this.dataPublicacao = dataPublicacao;
		this.dataModificada = dataModificada;
		this.conteudo = conteudo;
		this.titulo = titulo;
		this.sinopse = sinopse;
		this.imagemCapa = imagemCapa;
		this.numeroVisualizacao = numeroVisualizacao;
		this.tags = tags.stream().map(ListaTags::new).toList(); 
		
	}

	
	public NoticiaCompletaDto() {
		
	}

	
	public LocalDateTime getDataPublicacao() {
		return dataPublicacao;
	}

	public LocalDateTime getDataModificada() {
		return dataModificada;
	}

	public List<ListaTags> getTags() {
		return tags;
	}

	public Long getId() {
		return id;
	}

	public String getSiteBuscado() {
		return siteBuscado;
	}

	public String getLinkDaNoticiaOficial() {
		return linkDaNoticiaOficial;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getAutoria() {
		return autoria;
	}



	public String getConteudo() {
	    return   conteudo;
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

	public Long getNumeroVisualizacao() {
		return numeroVisualizacao;
	}

	
}
