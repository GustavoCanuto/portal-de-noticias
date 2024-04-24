package br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.cadastroUsuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magnasistemas.apimagnaspnewsusuarios.enuns.Role;
import br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.ValidacaoException;
import br.com.magnasistemas.apimagnaspnewsusuarios.validacoes.permissoes.VerificarRole;


@Component
public class ValidarAcessoRole implements ValidarCadastroUsuario{

	@Autowired
	VerificarRole verificarRole;
	
	@Override
	public void validar(List<Role> roles) {

		 if (roles != null && !roles.isEmpty()) {
	            for (Role roleName : roles) {
	           
	           
	            		//Somente admin pode cadastrar outro admin ou editor 
	            		if(!verificarRole.hasRole("ADMIN") && (roleName == Role.ADMIN || roleName == Role.EDITOR) ) 
	            			throw new ValidacaoException("Seu Usuario não tem permisão para cadastrar novo usuario com a role " +roleName);
	        
	            
	            }
		 }
	    
}
}
