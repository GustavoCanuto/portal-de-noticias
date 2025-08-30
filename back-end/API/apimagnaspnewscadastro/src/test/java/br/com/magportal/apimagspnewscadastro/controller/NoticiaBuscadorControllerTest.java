package br.com.magportal.apimagspnewscadastro.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.magportal.apimagspnewscadastro.ApiMagSPNewsCadastroApplication;
import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoAtualizar;
import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoCadastro;
import br.com.magportal.apimagspnewscadastro.entity.Noticia;
import br.com.magportal.apimagspnewscadastro.entity.Tag;
import br.com.magportal.apimagspnewscadastro.repository.NoticiaRepository;
import br.com.magportal.apimagspnewscadastro.repository.TagNoticiaRepository;
import br.com.magportal.apimagspnewscadastro.repository.TagRepository;

@SpringBootTest(classes = ApiMagSPNewsCadastroApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NoticiaBuscadorControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	 private TagRepository tagRepository;

	@Autowired
	 private NoticiaRepository noticiaRepository;


	@Autowired
	 private TagNoticiaRepository tagNoticiaRepository;

	private final String URI_PRINCIPAL = "/noticia";

	private static LocalDateTime horaDataAtual = LocalDateTime.now();
	
	@BeforeEach
	void inicializar() {
		
		Tag tag1 = new Tag("tag1");
		Tag tag2 = new Tag("tag2");

		List<Tag> tags = new ArrayList<>();
		tags.add(tag1);
		tags.add(tag2);

		tagRepository.saveAll(tags);
		
		Noticia noticia2 = new Noticia(
			    "g1",
			    "https://www.google.com/search?q=yy",
			    "categoria",
			    "autoria",
			    horaDataAtual,
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
	
	@ParameterizedTest
	@MethodSource("parametrosCadastroValido")
	@DisplayName("Deveria cadastrar uma noticia com informações válidas")
	void cadastrarCenario1(String site, String link, String categoria, String autoria, LocalDateTime data,
			LocalDateTime dataAtualizada, String conteudo, String titulo, String sinopse, String img, Long visualizacao,
			List<Tag> tags) {

		NoticiaDtoCadastro requestBody = new NoticiaDtoCadastro(1L, site, link, categoria, autoria, data,
				dataAtualizada, conteudo, titulo, sinopse, img, visualizacao, tags);

		ResponseEntity<NoticiaDtoCadastro> responseEntity = restTemplate.postForEntity(URI_PRINCIPAL, requestBody,
				NoticiaDtoCadastro.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getBody()).isNotNull();

	}
	
	static Stream<Arguments> parametrosCadastroValido() {
		return Stream.of(

				Arguments.of("g1", "https://www.google.com/search?q=xx", "categoria", "autoria3",
						LocalDateTime.of(2024, 02, 22, 16, 00), null, "<p>conteudo</p>", "titulo", "sinopse", "img", 5L,
						Arrays.asList(new Tag("tagNova"))),
				
				Arguments.of("g1", "https://www.google.com/", "categoria", "autoria3",
						LocalDateTime.of(2024, 02, 22, 16, 00), null, "<p>conteudo</p>", "titulo", "sinopse", "img", 5L,
						null),

				Arguments.of("g1", "https://www.google.com/", "categoria", "autoria3",
						LocalDateTime.of(2024, 02, 22, 16, 00), LocalDateTime.of(2024, 02, 22, 18, 00),
						"<p>conteudo</p>", "titulo", "sinopse", "img", 5L, Arrays.asList(new Tag("tag1"))));

	}
	
	@ParameterizedTest
	@MethodSource("parametrosCadastroInvalido")
	@DisplayName("Não deveria cadastrar uma noticia com informações invalidas")
	void cadastrarCenario2(String site, String link, String categoria, String autoria, LocalDateTime data,
			LocalDateTime dataAtualizada, String conteudo, String titulo, String sinopse, String img, Long visualizacao,
			List<Tag> tags,String mensagemDeErro) {

		NoticiaDtoCadastro requestBody = new NoticiaDtoCadastro(1L, site, link, categoria, autoria, data,
				dataAtualizada, conteudo, titulo, sinopse, img, visualizacao, tags);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URI_PRINCIPAL, requestBody, String.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody()).contains(mensagemDeErro);

	}
	
	static Stream<Arguments> parametrosCadastroInvalido() {
		return Stream.of(

				Arguments.of("g1", "https://www.google.com/search?q=yy", "categoria", "autoria2",
						LocalDateTime.of(2024, 02, 22, 16, 00), null, "<p>conteudo</p>", "titulo", "sinopse", "img", 5L,
						Arrays.asList(new Tag("tagNova")),"noticia com esse link já registrado"),
				
				Arguments.of("g1", "https://www.google.com/", "categoria", "autoria",
						horaDataAtual, null, "<p>conteudo</p>", "titulo", "sinopse", "img", 5L,
						null,"noticia com esse titulo e autoria já registrado"));

			

	}
	
	@ParameterizedTest
	@MethodSource("parametrosAtualizarValido")
	@DisplayName("Deveria atualizar dados")
	void atualizarCenario1(String site, String categoria, String autoria, 
			LocalDateTime dataAtualizada, String conteudo, String titulo, String sinopse, String img,
			List<Tag> tags) {

		String linkExistente = "https://www.google.com/search?q=yy";
		
		NoticiaDtoAtualizar requestBody = new NoticiaDtoAtualizar( site,  categoria, autoria,
				dataAtualizada, conteudo, titulo, sinopse, img, tags);

		ResponseEntity<NoticiaDtoAtualizar> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL + "?linkNoticia="+ linkExistente,
				HttpMethod.PUT, new HttpEntity<>(requestBody), NoticiaDtoAtualizar.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		//assertThat(responseEntity.getBody().id()).isEqualTo(idExistente);

	}
	
	static Stream<Arguments> parametrosAtualizarValido() {
		return Stream.of(

				Arguments.of(null, null, null,
						LocalDateTime.of(2024, 02, 22, 18, 00), null,null, null, null,
						 null),
				

				Arguments.of("g1",  "categoria", "autoria3",
				  LocalDateTime.of(2024, 02, 22, 18, 00),
						"<p>conteudo</p>", "titulo", "sinopse", "img",  Arrays.asList(new Tag("tag1"))));

	}
	
	
	@Test
	@DisplayName("Deveria dar erro ao nao encontrar link")
	void atualizarCenario2() {

		String linkExistente = "https://www.google.com/search?q=yysdsd";
		
		NoticiaDtoAtualizar requestBody = new NoticiaDtoAtualizar( "g1",  "categoria", "autoria3",
				  LocalDateTime.of(2024, 02, 22, 18, 00),
						"<p>conteudo</p>", "titulo", "sinopse", "img",  Arrays.asList(new Tag("tag1")));

		ResponseEntity<String> responseEntity = restTemplate.exchange(URI_PRINCIPAL + "?linkNoticia="+ linkExistente,
				HttpMethod.PUT, new HttpEntity<>(requestBody), String.class);

		  assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).contains("Não há nenhuma notícia com esse link");

	}
	
	@Test
	@DisplayName("Deveria retornar 500")
	void erro500() {

		String idInvalido = "x";

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL, HttpMethod.PUT, null,
				String.class, idInvalido);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@Test
	@DisplayName("Deveria retornar 404 com id invalido ")
	void erro404() {

		Long idNaoExistente = 15L;

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL + "/{id}", HttpMethod.PUT, null,
				String.class, idNaoExistente);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}
	
}