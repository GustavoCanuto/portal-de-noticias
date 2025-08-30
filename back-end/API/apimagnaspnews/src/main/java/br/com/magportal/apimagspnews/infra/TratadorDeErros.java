package br.com.magportal.apimagspnews.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.magportal.apimagspnews.validacoes.ValidacaoException;
import br.com.magportal.apimagspnews.validacoes.ValidacaoException404;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

	// exemplo http://localhost:8080/noticia/15000
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> tratarErro404() {

		return ResponseEntity.notFound().build();

	}

	// ver se vai ser necessario
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<String> tratarErroRegraDeNegocio(ValidacaoException ex) {

		return ResponseEntity.badRequest().body(ex.getMessage());

	}
	
	@ExceptionHandler(ValidacaoException404.class)
	public ResponseEntity<String> tratarErroRegraDeNegocio(ValidacaoException404 ex) {

		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(ex.getMessage());

	}

	// exemplo http://localhost:8080/noticia/dsgfgfg mandando letra ao inves de
	// numero (id)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> tratarErro500(Exception ex) {

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
	}

}
