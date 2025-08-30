package br.com.magportal.apimagspnewscadastro.dto.noticia;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.magportal.apimagspnewscadastro.entity.Noticia;
import br.com.magportal.apimagspnewscadastro.entity.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class NoticiaDtoCadastro {
	
	Long id;

	@NotNull
	String siteBuscado;
	@NotNull
	String linkDaNoticiaOficial;
	@NotNull
	String categoria;
	@NotNull
	String autoria;
	@NotNull
	LocalDateTime dataPublicacao;
	LocalDateTime dataModificada;
	@NotNull
	String conteudo;
	@NotNull
	String titulo;
	String sinopse;
	@NotNull
	String imagemCapa;
	@Positive(message = "O numero de visualização deve ser positivo") 
	Long numeroVisualizacao;

	List<Tag> tags;

	public NoticiaDtoCadastro(Noticia noticia) {
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

	public NoticiaDtoCadastro(Long id, String siteBuscado, String linkDaNoticiaOficial, String categoria,
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
		this.tags = tags != null ? tags : new ArrayList<>();
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

	public LocalDateTime getDataPublicacao() {
		return dataPublicacao;
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

	public Long getNumeroVisualizacao() {
		return numeroVisualizacao;
	}

	public List<Tag> getTags() {
		return tags;
	}

}
