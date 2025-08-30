package br.com.magportal.apimagspnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.magportal.apimagspnews.dto.tags.ListaTags;
import br.com.magportal.apimagspnews.service.TagService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("tag")
public class TagController {

	@Autowired
	private TagService tagService;

	@GetMapping
	public ResponseEntity<Page<ListaTags>> listar(@PageableDefault(size = 10) Pageable paginacao) {

		return ResponseEntity.ok(tagService.listarTags(paginacao));

	}

	@GetMapping("/nomeTag")
	public ResponseEntity<Page<ListaTags>> listarPoNomeTag(
			@RequestParam(required = true)  String nomeTag,
			@PageableDefault(size = 10) Pageable paginacao, String cache) {
		try {
			return ResponseEntity
					.ok(tagService.criarPaginacao(paginacao, tagService.listarTagsPorNome(nomeTag, paginacao,cache)));
		} catch (RedisConnectionFailureException ex) {
			return this.listarPoNomeTag(nomeTag, paginacao, "semCache");
			
		}
	}

}
