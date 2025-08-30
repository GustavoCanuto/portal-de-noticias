package br.com.magportal.apimagspnewsusuarios.controller;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	//exibir informaçoes pessoa usuario
	@GetMapping("/{idusuario}")
	public String listInfoUsuario(@PathVariable String site) {
	         return "informaçao pessoa usuario ";         
	 }
	
	@PostMapping("/usuario-normal")
	public String createUsuarioNormal() {
		return "Cadastrando usuario normal";
	}
	
	
	@PostMapping("/usuario-admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String create() {
		return "Cadastrando usuario admin";
	}
	

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private boolean isUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
    }
    
}