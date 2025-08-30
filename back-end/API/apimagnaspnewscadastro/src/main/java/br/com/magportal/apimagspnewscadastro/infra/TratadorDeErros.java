package br.com.magportal.apimagspnewscadastro.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.magportal.apimagspnewscadastro.validacoes.ValidacaoException;
import br.com.magportal.apimagspnewscadastro.validacoes.ValidacaoException404;

@RestControllerAdvice
public class TratadorDeErros {


	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<String> tratarErroRegraDeNegocio(ValidacaoException ex) {

		return ResponseEntity.badRequest().body(ex.getMessage());

	}
	
	@ExceptionHandler(ValidacaoException404.class)
	public ResponseEntity<String> tratarErroRegraDeNegocio(ValidacaoException404 ex) {

		 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(ex.getMessage());

	}
	
	//exemplo http://localhost:8080/noticia/dsgfgfg mandando letra ao inves de numero (id)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> tratarErro500(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
	}
	
}
