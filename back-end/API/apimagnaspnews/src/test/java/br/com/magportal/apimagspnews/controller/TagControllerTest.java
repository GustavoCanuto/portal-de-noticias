package br.com.magportal.apimagspnews.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.magportal.apimagspnews.dto.tags.ListaTags;
import br.com.magportal.apimagspnews.entity.Tag;
import br.com.magportal.apimagspnews.infra.PageResponse;
import br.com.magportal.apimagspnews.repository.TagRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class TagControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TagRepository tagRepository;

	private final String URI_PRINCIPAL = "/tag";

	@BeforeEach
	void inicializar() {
		
		Tag tag1 = new Tag("tag1");
		Tag tag2 = new Tag("tag2");

		List<Tag> tags = new ArrayList<>();
		tags.add(tag1);
		tags.add(tag2);

		tagRepository.saveAll(tags);
		
		
		
	}

	@AfterEach
	void finalizar() {

		tagRepository.deleteAllAndResetSequence();

	}
	
	
	@Test
	@DisplayName("Deveria listar tags Existente")
	void listarTagsExistentes() {

		ResponseEntity<PageResponse<ListaTags>> responseEntity = restTemplate.exchange(
				URI_PRINCIPAL,
				HttpMethod.GET, null, new ParameterizedTypeReference<PageResponse<ListaTags>>() {
				});
		
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();

		PageResponse<ListaTags> pageResponse = responseEntity.getBody();

		assertThat(pageResponse.isEmpty()).isFalse();
		assertThat(pageResponse.getContent()).isNotEmpty();

	}

}
