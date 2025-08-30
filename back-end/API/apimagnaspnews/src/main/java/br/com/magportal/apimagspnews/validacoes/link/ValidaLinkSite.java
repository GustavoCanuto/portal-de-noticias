package br.com.magportal.apimagspnews.validacoes.link;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import br.com.magportal.apimagspnews.enums.SiteEnum;
import br.com.magportal.apimagspnews.validacoes.ValidacaoException;

@Component
public class ValidaLinkSite implements ValidaLink {

	@Cacheable(value = "cacheValidacaoLinkSite", key = "#site", condition = "#cacheManager == null && #site != null")
	public boolean validar(String site, String cacheManager) {
		
		if (site != null) {
			try {
				SiteEnum siteEnum = SiteEnum.valueOf(site.toUpperCase());
				String url = siteEnum.getUrl();

				HttpURLConnection.setFollowRedirects(false);
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				con.setRequestMethod("HEAD");

				if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
					return true;
				else
					throw new ValidacaoException("Site fora do Ar!");

			} catch (IllegalArgumentException e) {
				throw new ValidacaoException("Site invalido!");
			} catch (IOException e) {
				throw new ValidacaoException("Site fora do Ar!");
			}
		} else {
			return true;
		}

	}
}
