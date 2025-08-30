package br.com.magportal.apimagspnewsusuarios.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.magportal.apimagspnewsusuarios.dto.UserDto;

@Service
public class LoginService {

	@Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
	private String keycloakTokenUri;
	
	public ResponseEntity<String> logar(UserDto user) {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate rt = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

		formData.add("client_id", "authenticationClientId");
		formData.add("username", user.username());
		formData.add("password", user.password());
		formData.add("grant_type", "password");

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(formData,
				headers);

		var result = rt.postForEntity(keycloakTokenUri,entity, String.class);

		return result;
	}
}
