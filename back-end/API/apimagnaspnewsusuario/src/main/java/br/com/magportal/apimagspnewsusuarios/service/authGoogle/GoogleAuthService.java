package br.com.magportal.apimagspnewsusuarios.service.authGoogle;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class GoogleAuthService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private String scope;

    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    private String authorizationUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;
    
    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
	private String keycloakTokenUri;

    public void googleAuthRedirect(HttpServletResponse response) throws IOException {
        String state = UUID.randomUUID().toString();
        String googleAuthUrl = authorizationUri +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&scope=" + scope +
                "&redirect_uri=" + redirectUri +
                "&state=" + state;

        response.sendRedirect(googleAuthUrl);
    }

    public String exchangeCodeForAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", code);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Map> responseEntity = rt.postForEntity(tokenUri, requestEntity, Map.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = responseEntity.getBody();
            return (String) responseBody.get("access_token");
        } else {
            throw new RuntimeException("Failed to exchange authorization code for access token");
        }
    }
    
    public ResponseEntity<String> tokenGoogle(String acessToken) {

		HttpHeaders headers = new HttpHeaders();
		RestTemplate rt = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		
		formData.add("client_id", "authenticationClientId");
		formData.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
		formData.add("subject_token_type", "urn:ietf:params:oauth:token-type:access_token");
		formData.add("subject_token", acessToken);
		formData.add("subject_issuer", "google");
		
		HttpEntity<MultiValueMap<String, String>> entity 
		= new HttpEntity<MultiValueMap<String, String>>(formData, headers);
		
		var result = rt.postForEntity(keycloakTokenUri,entity, String.class);
		
		return result;
    }
}