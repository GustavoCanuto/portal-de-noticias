package br.com.magportal.apimagspnews.enums;

public enum SiteEnum {
	  	G1("https://g1.globo.com/"),
	    CNN("https://edition.cnn.com/"),
	    VEJA("https://veja.abril.com.br/"),
	    GOV("https://www.saopaulo.sp.gov.br/spnoticias/ultimas-noticias/"),
	    UOL("https://www.uol.com.br/"),
	    TESTE_SITE_FORA_DO_AR("https://linkInvalidoErrox7546500.com.br/"),
	    TESTE_SITE_NOT_FOUND("https://httpstat.us/500");

	    private final String url;

	    SiteEnum(String url) {
	        this.url = url;
	    }

	    public String getUrl() {
	        return url;
	    }
}
