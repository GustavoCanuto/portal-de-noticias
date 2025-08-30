package br.com.magportal.apimagspnewscadastro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoAtualizar;
import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoCadastro;
import br.com.magportal.apimagspnewscadastro.service.NoticiaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("noticia")
public class NoticiaBuscadorController {

	@Autowired
	private NoticiaService noticiaService;

	@PostMapping
	@Transactional
	public ResponseEntity<NoticiaDtoCadastro> cadastrar(@RequestBody @Valid NoticiaDtoCadastro dados,
			UriComponentsBuilder uriBuilder) {

		var entidade = noticiaService.cadastrar(dados);

		var uri = uriBuilder.path("/noticia/{id}").buildAndExpand(entidade.getId()).toUri();

		return ResponseEntity.created(uri).body(entidade);

	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<NoticiaDtoCadastro> atualizar(
			@RequestParam(name = "linkNoticia", required = true) String linkNoticia,
			@RequestBody NoticiaDtoAtualizar dados) {

		return ResponseEntity.ok(noticiaService.atualizarCadastro(dados, linkNoticia));

	}
	

}
