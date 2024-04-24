package br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.cadastroUsuario;

import java.util.List;

import br.com.magnasistemas.apimagnaspnewsusuarios.enuns.Role;

public interface ValidarCadastroUsuario {

	void validar(List<Role> roles);
}
