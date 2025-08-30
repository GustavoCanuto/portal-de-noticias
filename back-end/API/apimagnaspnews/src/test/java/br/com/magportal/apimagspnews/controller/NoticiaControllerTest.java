package br.com.magportal.apimagspnews.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.magportal.apimagspnews.dto.noticia.NoticiaCompletaDto;
import br.com.magportal.apimagspnews.dto.noticia.PreviaNoticiaDto;
import br.com.magportal.apimagspnews.entity.Noticia;
import br.com.magportal.apimagspnews.entity.Tag;
import br.com.magportal.apimagspnews.infra.PageResponse;
import br.com.magportal.apimagspnews.repository.NoticiaRepository;
import br.com.magportal.apimagspnews.repository.TagNoticiaRepository;
import br.com.magportal.apimagspnews.repository.TagRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
 class NoticiaControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private NoticiaRepository noticiaRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TagNoticiaRepository tagNoticiaRepository;

	private final String URI_PRINCIPAL = "/noticia";
	private final String URI_PESQUISA_SITE = "/noticia/site";
	private final String URI_PESQUISA_DATA_PUBLICACAO = "/noticia/data-publicacao";
	private final String URI_MAIS_LIDAS = "/noticia/mais-lidas";
	private final String URI_MAIS_PRINCIPAIS = "/noticia/principais";
	private final String URI_PESQUISA_TITULO = "/noticia/titulo";
	private final String URI_PESQUISA_SINOPSE = "/noticia/sinopse";
	private final String URI_PESQUISA_CONTEUDO = "/noticia/conteudo";
	private final String URI_PESQUISA_TAG = "/noticia/tag";
	private final String URI_PESQUISA_CATEGORIA = "/noticia/categoria";

	
	@BeforeEach
	void inicializar() {
		
		Tag tag1 = new Tag("tag1");
		Tag tag2 = new Tag("tag2");

		List<Tag> tags = new ArrayList<>();
		tags.add(tag1);
		tags.add(tag2);


		Noticia noticia = new Noticia(
		    "g1",
		    "https://www.google.com/",
		    "categoria",
		    "autoria",
		    LocalDateTime.of(2024, 02, 22, 16, 00),
		    null,
		    "<p>conteudo</p>",
		    "titulo",
		    "sinopse",
		    "img",
		    5L,
		    tags);

		noticiaRepository.save(noticia);
		
		Noticia noticia2 = new Noticia(
			    "g1",
			    "https://www.google.com/search?q=1",
			    "categoria",
			    "autoria",
			    LocalDateTime.now(),
			    null,
			    "<p>conteudo</p>",
			    "titulo",
			    "sinopse",
			    "img",
			    5L,
			    null);

			noticiaRepository.save(noticia2);
	
	}

	@AfterEach
	void finalizar() {
		
		tagNoticiaRepository.deleteAllAndResetSequence();
		tagRepository.deleteAllAndResetSequence();
		noticiaRepository.deleteAllAndResetSequence();

	}

	@Test
	@DisplayName("Deveria listar Previas Noticia")
	void listarTodasPrevias() {

		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	
	@Test
	@DisplayName("Deveria listar por Site")
	void listarNoticiasPorSite() {
	
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_SITE + "/g1?inicioPeriodo=2024-02-22T16:00:00&fimPeriodo=2024-02-22T16:00:00",
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@Test
	@DisplayName("Deveria listar por Site Sem data")
	void listarNoticiasPorSiteSemData() {
	
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_SITE + "/g1",
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@Test
	@DisplayName("Deveria listar por Data")
	void listarNoticiasPorData() {

		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_DATA_PUBLICACAO + "?inicioPeriodo=2024-02-22T16:00:00&fimPeriodo=2024-02-22T16:00:00",
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@Test
	@DisplayName("Deveria listar por Data Sem Parametros")
	void listarNoticiasPorDataSemParametros() {

		
			
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_DATA_PUBLICACAO,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@Test
	@DisplayName("Deveria listar por Mais Lidas")
	void listarNoticiasPorMaisLida() {

		
	
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_MAIS_LIDAS,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@Test
	@DisplayName("Deveria listar por Principais")
	void listarNoticiasPorPrincipais() {

		
	
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_MAIS_PRINCIPAIS,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@ParameterizedTest
	@MethodSource("parametrosParaListar")
	@DisplayName("Deveria listar por Titulo")
	void listarNoticiasPorTitulo(String site,String periodoInicial,String periodoFinal) {

		
		String urlCompleta = site+periodoInicial+periodoFinal;
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_TITULO + "?palavra=t"+ urlCompleta,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}

	@ParameterizedTest
	@MethodSource("parametrosParaListar")
	@DisplayName("Deveria listar por Sinopse")
	void listarNoticiasPorSinopse(String site,String periodoInicial,String periodoFinal) {

		
		String urlCompleta = site+periodoInicial+periodoFinal;
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_SINOPSE + "?palavra=s"+ urlCompleta,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@ParameterizedTest
	@MethodSource("parametrosParaListar")
	@DisplayName("Deveria listar por Conteudo")
	void listarNoticiasPorConteudo(String site,String periodoInicial,String periodoFinal) {

		
		String urlCompleta = site+periodoInicial+periodoFinal;
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_CONTEUDO + "?palavra=c"+ urlCompleta,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@ParameterizedTest
	@MethodSource("parametrosParaListar")
	@DisplayName("Deveria listar por Tag")
	void listarNoticiasPorTag(String site,String periodoInicial,String periodoFinal) {

		
		String urlCompleta = site+periodoInicial+periodoFinal;
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_TAG + "?nomeTag=tag1"+ urlCompleta,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}
	
	@ParameterizedTest
	@MethodSource("parametrosParaListar")
	@DisplayName("Deveria listar por Categoria")
	void listarNoticiasPorCategoria(String site,String periodoInicial,String periodoFinal) {

		
		String urlCompleta = site+periodoInicial+periodoFinal;
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_CATEGORIA + "?categorias=categoria"+ urlCompleta,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}

	
	@Test
	@DisplayName("Deveria detalhar uma noticia por ID")
	void detalharCidadePorId() {
		Long idExistente = 1L;
	    
		ResponseEntity<NoticiaCompletaDto> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL+"/{id}", HttpMethod.GET, null,
				NoticiaCompletaDto.class, idExistente);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody().getId()).isEqualTo(idExistente);
		assertThat(responseEntity.getBody().getTitulo()).isEqualTo("titulo");

	}
	
	@Test
	@DisplayName("Deveria retornar 500")
	void erro500() {

		String idInvalido = "x";

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL+"/{id}", HttpMethod.GET, null,
				String.class, idInvalido);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@Test
	@DisplayName("Deveria retornar 404 com id invalido ")
	void erro404() {

		Long idNaoExistente = 15L;

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL + "/{id}", HttpMethod.GET, null,
				String.class, idNaoExistente);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
	
	@ParameterizedTest
	@MethodSource("parametrosSiteInvalido")
	@DisplayName("Deveria mandar erro site fora do ar ou invalido")
	void siteForaDoAr(String site, String mensagemDeErro) {


		ResponseEntity<String> responseEntity =  restTemplate.exchange(
				URI_PESQUISA_SITE + "/"+site, HttpMethod.GET, null,
				String.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody()).contains(mensagemDeErro);

	}
	
	static Stream<Arguments> parametrosSiteInvalido() {
		return Stream.of(

				Arguments.of("TESTE_SITE_FORA_DO_AR",
						"Site fora do Ar!"),

				Arguments.of("TESTE_SITE_NOT_FOUND",
						"Site fora do Ar!"),
				
				Arguments.of("t2",
		        "Site invalido!"));
	}
	
	@ParameterizedTest
	@MethodSource("parametrosLinksInvalido")
	@DisplayName("nao deviar mandar previa noticia com link quebrado")
	void linkNoticiaInvalido(String linkInvalido) {
		
		Noticia noticia = new Noticia(
			    "g1",
			    linkInvalido,
			    "categoria",
			    "autoria",
			    LocalDateTime.of(2020, 02, 22, 10, 00),
			    null,
			    "<p>conteudo</p>",
			    "titulo",
			    "sinopse",
			    "img",
			    5L,
			    null);

			noticiaRepository.save(noticia);
		
		ResponseEntity<PageResponse<PreviaNoticiaDto>> responseEntity = restTemplate.exchange(
				URI_PESQUISA_DATA_PUBLICACAO + "?inicioPeriodo=2020-02-22T10:00:00&fimPeriodo=2020-02-22T10:00:00",
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<PreviaNoticiaDto>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<PreviaNoticiaDto> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isTrue();
		assertThat(pageResponse.getContent()).isEmpty();


		

	}
	
	static Stream<Arguments> parametrosLinksInvalido() {
		return Stream.of(

				Arguments.of("linkInvalido"),

				Arguments.of("https://httpstat.us/500"));
	}

	static Stream<Arguments> parametrosParaListar() {
		return Stream.of(Arguments.of("&site=g1", "", ""),
				Arguments.of("", "&inicioPeriodo=2024-02-22T16:00:00", "&fimPeriodo=2024-02-22T16:00:00"),
				Arguments.of("&site=g1", "&inicioPeriodo=2024-02-22T16:00:00", "&fimPeriodo=2024-02-22T16:00:00"),
				Arguments.of("", "", "")

		);
	}
}
