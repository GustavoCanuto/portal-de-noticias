package br.com.magportal.apimagspnewscadastro.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoAtualizar;
import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoCadastro;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Table(name = "tb_noticia")
@Entity(name = "Noticia")
public class Noticia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String siteBuscado;
	private String linkDaNoticiaOficial;
	private String categoria;
	private String autoria;
	private LocalDateTime dataPublicacao;
	private LocalDateTime dataModificada;
	private String conteudo;
	private String titulo;
	private String sinopse;
	private String imagemCapa;
	private Long   numeroVisualizacao;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "tb_tag_noticia",
	    joinColumns = @JoinColumn(name = "fk_noticia"),
	    inverseJoinColumns = @JoinColumn(name = "fk_tag"))
	private List<Tag> tags = new ArrayList<>();

	public Noticia() {
		
	}

	public Noticia(String siteBuscado, String linkDaNoticiaOficial, String categoria, String autoria,
			LocalDateTime dataPublicacao, LocalDateTime dataModificada, String conteudo, String titulo, String sinopse,
			String imagemCapa, Long numeroVisualizacao, List<Tag> tags) {
	
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
		this.tags = tags;
		
	}
	
	public Noticia(NoticiaDtoCadastro dados, List<Tag> tags) {
	
		this.siteBuscado = dados.getSiteBuscado();
		this.linkDaNoticiaOficial = dados.getLinkDaNoticiaOficial();
		this.categoria =  dados.getCategoria();
		this.autoria =  dados.getAutoria();
		this.dataPublicacao =  dados.getDataPublicacao();
		this.dataModificada =  dados.getDataModificada();
		this.conteudo =  dados.getConteudo();
		this.titulo =  dados.getTitulo();
		this.sinopse =  dados.getSinopse();
		this.imagemCapa =  dados.getImagemCapa();
		this.numeroVisualizacao =  dados.getNumeroVisualizacao();
		this.tags =  tags;
		
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
		return conteudo;
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

	public void atualizarInformacoes(NoticiaDtoAtualizar dados, List<Tag> tags) {
	
		if (dados.getSiteBuscado() != null) {
			this.siteBuscado = dados.getSiteBuscado();
		}
		
		if (dados.getCategoria() != null) {
			this.categoria =  dados.getCategoria();
		}
		
		if (dados.getAutoria() != null) {
			this.autoria =  dados.getAutoria();
		}
		
		if (dados.getConteudo() != null) {
			this.conteudo =  dados.getConteudo();
		}
		
		if (dados.getTitulo() != null) {
			this.titulo =  dados.getTitulo();
		}
		
		if (dados.getSinopse() != null) {
			this.sinopse =  dados.getSinopse();
		}
		
		if (dados.getImagemCapa() != null) {
			this.imagemCapa =  dados.getImagemCapa();
		}
		
		if (tags != null) {
			this.tags =  tags;
		}
		
		this.dataModificada =  dados.getDataModificada();
		
	}
	
}
