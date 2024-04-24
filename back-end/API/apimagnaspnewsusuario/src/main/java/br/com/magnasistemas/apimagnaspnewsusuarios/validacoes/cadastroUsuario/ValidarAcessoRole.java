package br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.cadastroUsuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.ValidacaoException;
import br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.permissoes.VerificarRole;


@Component
public class ValidarAcessoRole implements ValidarCadastroUsuario{

	@Autowired
	VerificarRole verificarRole;
	
	@Override
	public void validar(List<String> roles) {

		 if (roles != null && !roles.isEmpty()) {
	            for (String roleName : roles) {
	           
	           
	            		//Somente admin pode cadastrar outro admin ou editor 
	            		if(!verificarRole.hasRole("ADMIN") && (roleName.equalsIgnoreCase("ADMIN")||roleName.equalsIgnoreCase("EDITOR")) ) 
	            			throw new ValidacaoException("Seu Usuario não tem permisão para cadastrar novo usuario com a role " +roleName);
	            
	            			//Não é permitido cadastrar usuario buscador
	            		if(roleName.equalsIgnoreCase("BUSCADOR")) 
	            			throw new ValidacaoException("Não é permitido cadastrar usuario Buscador!");
	            
	            }
		 }
	    
}
}
