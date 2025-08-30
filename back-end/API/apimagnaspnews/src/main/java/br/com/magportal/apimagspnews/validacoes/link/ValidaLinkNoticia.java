package br.com.magportal.apimagspnews.validacoes.link;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ValidaLinkNoticia implements ValidaLink {

	@Cacheable(value = "cacheValidacaoLink", key = "#link", condition = "#cacheManager == null", sync = true)
	public boolean validar(String link, String cacheManager) {

		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(link).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (IOException e) {
			return false;
		}
	}
	
}
