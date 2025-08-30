package br.com.magportal.apimagspnews.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.magportal.apimagspnews.dto.noticia.NoticiaCompletaDto;
import br.com.magportal.apimagspnews.dto.noticia.PreviaNoticiaDto;
import br.com.magportal.apimagspnews.service.NoticiaService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("noticia")
public class NoticiaController {

	private static final Logger logger = LoggerFactory.getLogger(NoticiaController.class);
	private static final String MESSANGEM_ERRO_REDIS = "Erro ao tentar se conectar com servidor Redis: {}";
	
	@Autowired
	private NoticiaService noticiaService;

	private static final String SEM_CACHE = "SemCache";

	@GetMapping("/{id}")
	public ResponseEntity<NoticiaCompletaDto> detalhar(@PathVariable Long id, String cache) {
		try {
			return ResponseEntity.ok(noticiaService.detalharNoticia(id, cache));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.detalhar(id, SEM_CACHE);
		}
	}
	
	@GetMapping("/{site}/{dataPublicacao}")
	public ResponseEntity<NoticiaCompletaDto> detalharPorLinkNoticia(
		    @PathVariable String site,
		    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataPublicacao,
		    @RequestParam(required = true) String titulo,
			String cache) {
		try {
			return ResponseEntity.ok(noticiaService.detalharPorLinkNoticia(titulo,site,dataPublicacao, cache));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.detalharPorLinkNoticia(site,dataPublicacao,titulo, SEM_CACHE);
		}
	}
	
	@GetMapping
	public ResponseEntity<Page<PreviaNoticiaDto>> listar(@PageableDefault(size = 10) Pageable paginacao,
			String cache) {
		try {
			return ResponseEntity
					.ok(noticiaService.criarPaginacao(paginacao, noticiaService.listarPrevias(paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.listar(paginacao, SEM_CACHE);
		}

	}


	@GetMapping("/site/{site}")
	public ResponseEntity<Page<PreviaNoticiaDto>> buscarPorSite(@PathVariable String site,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioPeriodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fimPeriodo,
			@PageableDefault(size = 10) Pageable paginacao, String cache) {
		try {
			return ResponseEntity.ok(noticiaService.criarPaginacao(paginacao,
					noticiaService.buscarPorSite(site, inicioPeriodo, fimPeriodo, paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.buscarPorSite(site, inicioPeriodo, fimPeriodo, paginacao, SEM_CACHE);
		}
	}

	// pesquisa multiplas

	@GetMapping("/categoria")
	public ResponseEntity<Page<PreviaNoticiaDto>> buscarPorCategoria(@RequestParam(required = false) String site,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioPeriodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fimPeriodo,
			@RequestParam List<String> categorias, @PageableDefault(size = 10) Pageable paginacao, String cache) {

		try {
			return ResponseEntity
					.ok(noticiaService.criarPaginacao(paginacao,
							noticiaService.buscarPorCategoria(site, inicioPeriodo, fimPeriodo, categorias, paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.buscarPorCategoria(site, inicioPeriodo, fimPeriodo, categorias, paginacao, SEM_CACHE);
		}
	}

	@GetMapping("/tag")
	public ResponseEntity<Page<PreviaNoticiaDto>> buscarPorNomeTag(
			@RequestParam(required = true)  String nomeTag,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioPeriodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fimPeriodo,
			@PageableDefault(size = 10) Pageable paginacao, String cache) {
		try {
			return ResponseEntity
					.ok(noticiaService.criarPaginacao(paginacao,
							noticiaService.buscarPorNomeTag(site, inicioPeriodo, fimPeriodo, nomeTag, paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.buscarPorNomeTag(nomeTag, site, inicioPeriodo, fimPeriodo, paginacao, SEM_CACHE);
		}
	}

	@GetMapping("/titulo")
	public ResponseEntity<Page<PreviaNoticiaDto>> buscarPorTitulo(
			@RequestParam String palavra,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioPeriodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fimPeriodo,
			@PageableDefault(size = 10) Pageable paginacao, String cache) {
		try {
			return ResponseEntity
					.ok(noticiaService.criarPaginacao(paginacao,
							noticiaService.listarPorTitulo(site, inicioPeriodo, fimPeriodo, palavra, paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.buscarPorTitulo(palavra, site, inicioPeriodo, fimPeriodo, paginacao, SEM_CACHE);
		}
	}

	@GetMapping("/sinopse")
	public ResponseEntity<Page<PreviaNoticiaDto>> buscarPorSinopse(@RequestParam String palavra,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioPeriodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fimPeriodo,
			@PageableDefault(size = 10) Pageable paginacao, String cache) {
		try {
			return ResponseEntity
					.ok(noticiaService.criarPaginacao(paginacao,
							noticiaService.listarPorSinopse(site, inicioPeriodo, fimPeriodo, palavra, paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.buscarPorSinopse(palavra, site, inicioPeriodo, fimPeriodo, paginacao, SEM_CACHE);
		}
	}

	@GetMapping("/conteudo")
	public ResponseEntity<Page<PreviaNoticiaDto>> buscarPorConteudo(@RequestParam String palavra,
			@RequestParam(required = false) String site,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioPeriodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fimPeriodo,
			@PageableDefault(size = 10) Pageable paginacao, String cache) {
		try {
			return ResponseEntity
					.ok(noticiaService.criarPaginacao(paginacao,
							noticiaService.listarPorConteudo(site, inicioPeriodo, fimPeriodo, palavra, paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.buscarPorConteudo(palavra, site, inicioPeriodo, fimPeriodo, paginacao, SEM_CACHE);
		}
	}

	@GetMapping("/data-publicacao")
	public ResponseEntity<Page<PreviaNoticiaDto>> listarPorData(
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicioPeriodo,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fimPeriodo,
			@PageableDefault(size = 5) Pageable paginacao, String cache) {

		try {
			return ResponseEntity.ok(noticiaService.criarPaginacao(paginacao,
					noticiaService.listarPorDataPublicacao(inicioPeriodo, fimPeriodo, paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.listarPorData(inicioPeriodo, fimPeriodo, paginacao, SEM_CACHE);
		}
	}

	@GetMapping("/mais-lidas")
	public ResponseEntity<Page<PreviaNoticiaDto>> listarPorMaisLidas(@PageableDefault(size = 5) Pageable paginacao,
			String cache) {

		try {
			return ResponseEntity.ok(noticiaService.criarPaginacao(paginacao,
					noticiaService.listarMaisLidasDaSemana(paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.listarPorMaisLidas(paginacao, SEM_CACHE);
		}
	}

	@GetMapping("/principais")
	public ResponseEntity<Page<PreviaNoticiaDto>> listarPorPrincipais(@PageableDefault(size = 10) Pageable paginacao,
			String cache) {

		try {
			return ResponseEntity.ok(noticiaService.criarPaginacao(paginacao,
					noticiaService.listarPorPrincipais(paginacao, cache)));
		} catch (RedisConnectionFailureException ex) {
			 logger.error(MESSANGEM_ERRO_REDIS, ex.getMessage());
			return this.listarPorPrincipais(paginacao, SEM_CACHE);
		}
	}

}
