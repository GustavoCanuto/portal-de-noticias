package br.com.magportal.apimagspnews.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magportal.apimagspnews.dto.noticia.NoticiaCompletaDto;
import br.com.magportal.apimagspnews.dto.noticia.PreviaNoticiaDto;
import br.com.magportal.apimagspnews.entity.Noticia;
import br.com.magportal.apimagspnews.repository.NoticiaRepository;
import br.com.magportal.apimagspnews.validacoes.ValidacaoException404;
import br.com.magportal.apimagspnews.validacoes.link.ValidaLinkNoticia;
import br.com.magportal.apimagspnews.validacoes.link.ValidaLinkSite;

@Service
public class NoticiaService {

	@Autowired
	private NoticiaRepository repository;
	
	@Autowired
	private ValidaLinkNoticia validaLinkNotica;
	
	@Autowired
	private ValidaLinkSite validaLinkSite;

	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	@Cacheable(value = "cacheNoticiaCompleta", key = "#id", condition = "#cacheManager == null")
	public NoticiaCompletaDto detalharNoticia(Long id, String cacheManager) {

		Noticia noticia = repository.getReferenceById(id);
		noticia.setNumeroVisualizacao(noticia.getNumeroVisualizacao() + 1);
		repository.save(noticia);

		return new NoticiaCompletaDto(noticia);

	}

	@Cacheable(value = "cacheNoticiaCompleta", key = "#titulo + '|' + #site + '|' + #dataPublicacao", condition = "#cacheManager == null")
	public NoticiaCompletaDto detalharPorLinkNoticia(String titulo, String site, LocalDateTime dataPublicacao,
			String cacheManager) {

		 
		 
		Noticia noticia = repository
				.findByTituloIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacao(titulo, site, dataPublicacao)
				.orElseThrow(() -> new ValidacaoException404("Não há nenhuma notícia com esse link"));

		validaLinkNotica.validar(noticia.getLinkDaNoticiaOficial(), cacheManager);
		
		noticia.setNumeroVisualizacao(noticia.getNumeroVisualizacao() + 1);
		repository.save(noticia);

		return new NoticiaCompletaDto(noticia);

	}

	@Cacheable(value = "cachePadrao", key = "#paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> listarPrevias(Pageable paginacao, String cacheManager) {

		List<CompletableFuture<Noticia>> futuros = repository.findAll(paginacao).stream()
				.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		
		return processarResultados(futuros);

	}
	
	// conteudo
	@Cacheable(value = "cachePadrao", key = "#palavra + '|' +#site  + '|' + #inicioPeriodo + '|' + #fimPeriodo + '|' + '|' +#paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> listarPorConteudo(String site, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo,
			String palavra, Pageable paginacao, String cacheManager) {

		validaLinkSite.validar(site, cacheManager);
		List<CompletableFuture<Noticia>> futuros;

		if (site != null && inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findByConteudoContainingIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
							palavra, site, inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findByConteudoContainingIgnoreCaseAndDataPublicacaoBetween(palavra,
							inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (site != null) {

			futuros = repository.findByConteudoContainingIgnoreCaseAndSiteBuscadoIgnoreCase(palavra, site, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		} else {

			futuros = repository.findByConteudoContainingIgnoreCase(palavra, paginacao).stream()
					.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		}

		return processarResultados(futuros);

	}

	// sinopse
	@Cacheable(value = "cachePadrao", key = "#palavra + '|' + #site + '|' + #inicioPeriodo + '|' + #fimPeriodo + '|' + #paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> listarPorSinopse(String site, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo,
			String palavra, Pageable paginacao, String cacheManager) {

		validaLinkSite.validar(site, cacheManager);
		List<CompletableFuture<Noticia>> futuros;

		if (site != null && inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findBySinopseContainingIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
							palavra, site, inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findBySinopseContainingIgnoreCaseAndDataPublicacaoBetween(palavra,
							inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (site != null) {

			futuros = repository.findBySinopseContainingIgnoreCaseAndSiteBuscadoIgnoreCase(palavra, site, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else {

			futuros = repository.findBySinopseContainingIgnoreCase(palavra, paginacao).stream()
					.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		}

		return processarResultados(futuros);

	}

	// tag
	@Cacheable(value = "cachePadrao", key = "#nomeTag + '|' + #site + '|' + #inicioPeriodo + '|' + #fimPeriodo + '|' + #paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> buscarPorNomeTag(String site, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo,
			String nomeTag, Pageable paginacao, String cacheManager) {

		validaLinkSite.validar(site, cacheManager);
		List<CompletableFuture<Noticia>> futuros;

		if (site != null && inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findByTagsNomeAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(nomeTag,
							site, inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository.findByTagsNomeAndDataPublicacaoBetween(nomeTag, inicioPeriodo,
					fimPeriodo, paginacao).stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (site != null) {

			futuros = repository.findByTagsNomeAndSiteBuscadoIgnoreCase(nomeTag, site, paginacao).stream()
					.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		} else {

			futuros = repository.findByTagsNome(nomeTag, paginacao).stream()
					.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		}

		return processarResultados(futuros);

	}

	// categoria
	@Cacheable(value = "cachePadrao", key = "#categorias + '|' + #site + '|' + #inicioPeriodo + '|' + #fimPeriodo + '|' + #paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> buscarPorCategoria(String site, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo,
			List<String> categorias, Pageable paginacao, String cacheManager) {

		validaLinkSite.validar(site, cacheManager);
		List<CompletableFuture<Noticia>> futuros;

		if (site != null && inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findByCategoriaIgnoreCaseInAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
							categorias, site, inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findByCategoriaIgnoreCaseInAndDataPublicacaoBetween(categorias,
							inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (site != null) {

			futuros = repository.findByCategoriaIgnoreCaseInAndSiteBuscadoIgnoreCase(categorias, site, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else {

			futuros = repository.findByCategoriaIgnoreCaseIn(categorias, paginacao).stream()
					.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		}

		return processarResultados(futuros);
	}

	// titulo
	@Cacheable(value = "cachePadrao", key = "#palavra + '|' + #site + '|' + #inicioPeriodo + '|' + #fimPeriodo + '|' + #paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> listarPorTitulo(String site, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo,
			String palavra, Pageable paginacao, String cacheManager) {

		validaLinkSite.validar(site, cacheManager);
		List<CompletableFuture<Noticia>> futuros;

		if (site != null && inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findByTituloContainingIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
							palavra, site, inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findByTituloContainingIgnoreCaseAndDataPublicacaoBetween(palavra,
							inicioPeriodo, fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else if (site != null) {

			futuros = repository.findByTituloContainingIgnoreCaseAndSiteBuscadoIgnoreCase(palavra, site, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		} else {

			futuros = repository.findByTituloContainingIgnoreCase(palavra, paginacao).stream()
					.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		}

		return processarResultados(futuros);

	}

	// site
	@Cacheable(value = "cachePadrao", key = "#site + '|' + #inicioPeriodo + '|' + #fimPeriodo + '|' + #paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> buscarPorSite(String site, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo,
			Pageable paginacao, String cacheManager) {

		validaLinkSite.validar(site, cacheManager);
		List<CompletableFuture<Noticia>> futuros;

		if (inicioPeriodo != null && fimPeriodo != null) {

			futuros = repository
					.findBySiteBuscadoIgnoreCaseAndDataPublicacaoBetween(site, inicioPeriodo,
							fimPeriodo, paginacao)
					.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		} else {

			futuros = repository.findBySiteBuscadoIgnoreCase(site, paginacao).stream()
					.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();
		}

		return processarResultados(futuros);

	}

	// ultimas 24h
	@Cacheable(value = "cachePaginaInicial", key = "#paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#inicioPeriodo == null && #fimPeriodo == null && #cacheManager == null")
	public List<PreviaNoticiaDto> listarPorDataPublicacao(LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo,
			Pageable paginacao, String cacheManager) {

		List<CompletableFuture<Noticia>> futuros;

		if (inicioPeriodo == null) {
			inicioPeriodo = LocalDateTime.now().minusDays(1);
		}

		if (fimPeriodo == null) {
			fimPeriodo = LocalDateTime.now();
		}

		futuros = repository.findByDataPublicacaoBetween(inicioPeriodo, fimPeriodo, paginacao)
				.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		return processarResultados(futuros);
	}

	// mais lidas ultimos 7 dias
	@Cacheable(value = "cachePaginaInicial", key = "#paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> listarMaisLidasDaSemana(Pageable paginacao, String cacheManager) {

		List<CompletableFuture<Noticia>> futuros;

		LocalDateTime hoje = LocalDateTime.now();
		LocalDateTime inicioSemana = hoje.minusDays(7).withHour(0).withMinute(0).withSecond(0);

		futuros = repository.findByDataPublicacaoBetweenOrderByNumeroVisualizacaoDesc(inicioSemana, hoje, paginacao)
				.stream().map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		return processarResultados(futuros);
	}

	// mais lidas ultimas 24 horas
	@Cacheable(value = "cachePaginaInicial", key = "#paginacao.pageNumber + '|' + #paginacao.pageSize", condition = "#cacheManager == null")
	public List<PreviaNoticiaDto> listarPorPrincipais(Pageable paginacao, String cacheManager) {

		List<CompletableFuture<Noticia>> futuros;

		LocalDateTime inicioPeriodo = LocalDateTime.now().minusDays(1);
		LocalDateTime fimPeriodo = LocalDateTime.now();

		futuros = repository
				.findByDataPublicacaoBetweenOrderByNumeroVisualizacaoDesc(inicioPeriodo, fimPeriodo, paginacao).stream()
				.map(noticia -> verificarLinkAsync(noticia, cacheManager)).toList();

		return processarResultados(futuros);
	}

	// metodos
	public PageImpl<PreviaNoticiaDto> criarPaginacao(Pageable paginacao, List<PreviaNoticiaDto> noticias) {
		return new PageImpl<>(noticias, paginacao, noticias.size());
	}

	private CompletableFuture<Noticia> verificarLinkAsync(Noticia noticia, String cacheManager) {
		return CompletableFuture
				.supplyAsync(() -> validaLinkNotica.validar(noticia.getLinkDaNoticiaOficial(), cacheManager), executor)
				.thenApply(ok -> Boolean.TRUE.equals(ok) ? noticia : null);
	}

	private List<PreviaNoticiaDto> processarResultados(List<CompletableFuture<Noticia>> futuros) {

		List<Noticia> noticias = futuros.stream().map(CompletableFuture::join).filter(Objects::nonNull).toList();

		return noticias.stream().map(PreviaNoticiaDto::new).toList();
	}

}
