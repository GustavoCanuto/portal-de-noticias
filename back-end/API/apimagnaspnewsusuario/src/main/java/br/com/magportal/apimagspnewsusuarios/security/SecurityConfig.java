package br.com.magportal.apimagspnewsusuarios.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf(csrf -> csrf.disable())
			.oauth2ResourceServer(outh2 -> outh2
					.jwt(jwt -> jwt.jwtAuthenticationConverter(new JWTConverter())));
		
		
		return http.build();
	}
}
