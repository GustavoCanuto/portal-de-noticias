package br.com.magnasistemas.apimagnaspnews.validacoes.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apimagnaspnews.validacoes.ValidacaoException;
import br.com.magnasistemas.apimagnaspnews.validacoes.permissoes.VerificarRole;

@Component
public class ValidarAcessoRole implements ValidarUsuario {

	@Autowired
	VerificarRole verificarRole;

	@Override
	public void validar(String site) {

		System.out.println(site);

			if (verificarRole.hasRole("ADMIN") || verificarRole.hasRole("EDITOR")) {
				System.out.println("não é admin nem editor");
				return;}
			else if (!site.equalsIgnoreCase("g1")&&!site.equalsIgnoreCase("veja")) {
				System.out.println("não é site g1 ou veja");
				return;}
			else if (!verificarRole.hasRole(site.toUpperCase())) {
				throw new ValidacaoException("Seu Usuario não tem permisão para acessar conteudo do " + site);
			}
		
	}
}
