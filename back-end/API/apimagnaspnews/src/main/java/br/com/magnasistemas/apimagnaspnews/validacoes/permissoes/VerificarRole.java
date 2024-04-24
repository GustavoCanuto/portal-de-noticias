package br.com.magnasistemas.apimagnaspnews.validacoes.permissoes;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class VerificarRole {
	
    public boolean hasRole(String role) {
    	var authorities = getAuthentication();
    	System.out.println(authorities);
    	System.out.println(role);
    	System.out.println(authorities.contains(new SimpleGrantedAuthority("ROLE_"+role)));
        return authorities.contains(new SimpleGrantedAuthority("ROLE_"+role));
    
    }

	private Collection<? extends GrantedAuthority> getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities();

	}

}