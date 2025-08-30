package br.com.magportal.apimagspnewscadastro.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.magportal.apimagspnewscadastro.entity.Tag;
import br.com.magportal.apimagspnewscadastro.repository.TagRepository;

@Component
public class DatabaseGenerator {

	private final Random random = new Random();

	private final String[] tags = { "transporte", "saude", "Ensino", "Trem", "Famoso", "Transporte", "Imposto",
			"Livros", "Filme", "Cinema", "Roberto Carlos", "Entretenimento", "Comida" };

	private final String[] sites = { "UOL", "VEJA" };

	private final String[] autorias = { "João", "Gustavo", "Ysadora", "Michelle", "Victor", "Jonas", "Jonatha", "Luis",
			"Felipe", "Daniel", "Felipe", "Vinicius" };

	private final String[] imagensCapa = {
			"<img src='https://s2-g1.glbimg.com/Cfe3hKMN3B2VxUmBzmpu6iqwxeE=/0x0:1600x1200/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/a/v/m7ViZfSMCIgMNxde6Gqw/whatsapp-image-2024-02-27-at-07.26.22.jpeg'>",
			"<img src='https://s2-g1.glbimg.com/A6AhdG6mv7_cecQHQT_nnA3A0w4=/0x0:2711x1724/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/E/S/XLlhAsRuafgJxklToMAg/desola-lanre-ologun-7d4lredspyq-unsplash-1-.jpg' alt='Descrição da Imagem'>",
			"<img src='https://s2-g1.glbimg.com/loGn6eLVIZkSZtqrm_uvkNgh8R4=/0x0:1063x681/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/n/c/XJ5hLlRBuoMry6f60CnA/microsoftteams-image-16-.png' alt='Descrição da Imagem'>",
			"<img src='https://s2-g1.glbimg.com/VtCz88uR0bsO-dLfHQVwkfuzF14=/0x0:3000x1687/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/a/g/w4AU3pTKGKzB0CPBJZ3A/apple-vision-pro-2.jpg' alt='Descrição da Imagem'> ",
			"<img src='https://s2-g1.glbimg.com/5BEQXk4AgqjhMxYOcb4jj9EVhI0=/0x0:5472x3648/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/S/l/uoBoQpSMSrv3iTuJPccg/2024-02-15t181817z-1939874556-rc2536asw41u-rtrmadp-3-usa-trump-new-york-daniels.jpg' alt='Descrição da Imagem'>",
			"<img src='https://s2-g1.glbimg.com/P254LO0wSjrl1-fatv_sk3J8nJ4=/0x0:5000x3417/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/B/P/n4ufOBQPKVPbi8NW0Kog/2024-02-06t174630z-642274141-rc2vt5avpuf6-rtrmadp-3-usa-election-biden-michigan.jpg'>",
			"<img src='https://s2-g1.glbimg.com/YFwiPe2W-QCmPg01FuUMafEq_vs=/10x352:1080x1049/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/v/a/wAPFZLTuSg7medd77d6w/designerfrancesafogado-reproducaoinstagramlucien.jpeg'>",
			"<img src='https://s2-g1.glbimg.com/8rkb7HBbNvcjoavGY_E8NobLDFY=/0x0:800x532/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2023/6/i/BdAMAJS1GBKaDWxnP2IA/faustao.jpg'>",
			"<img src='https://s2-g1.glbimg.com/wHPGWUZHh0zQ4aUmVUPc3a9B6u8=/0x146:1912x886/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/n/e/NM3NHVTgeBtiPIwckqXw/globo-canal-5-20240227-0930-frame-18545.jpeg'>",
			"<img src='https://s2-g1.glbimg.com/7oMLFTLBIoNRjIKLBAFdzq0YqAA=/0x0:799x533/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2023/0/I/gGx1CWTkuu52MJQqrs3Q/marinho.jpg'>",
			"<img src='https://s2-g1.glbimg.com/-B8MzxWKrH3BpWqpSn7lnwu48CM=/0x0:840x472/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2021/5/3/r1sjdbRJ6TmA5ZiwqOeg/caixa-economica-federal0505202673-840x472.jpg'>",
			"<img src='https://s2-g1.glbimg.com/RvJ--aLBKZhCKomV9UG9EcMw8tQ=/0x0:1000x616/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/p/U/S4NHKTR3KkR5BHgudATA/img20240227122524011med.jpg' alt='Descrição da Imagem'>" };
	
	private final String[] imagens = {
			"https://s2-g1.glbimg.com/Cfe3hKMN3B2VxUmBzmpu6iqwxeE=/0x0:1600x1200/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/a/v/m7ViZfSMCIgMNxde6Gqw/whatsapp-image-2024-02-27-at-07.26.22.jpeg",
			"https://s2-g1.glbimg.com/A6AhdG6mv7_cecQHQT_nnA3A0w4=/0x0:2711x1724/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/E/S/XLlhAsRuafgJxklToMAg/desola-lanre-ologun-7d4lredspyq-unsplash-1-.jpg",
			"https://s2-g1.glbimg.com/loGn6eLVIZkSZtqrm_uvkNgh8R4=/0x0:1063x681/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/n/c/XJ5hLlRBuoMry6f60CnA/microsoftteams-image-16-.png",
			"https://s2-g1.glbimg.com/VtCz88uR0bsO-dLfHQVwkfuzF14=/0x0:3000x1687/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/a/g/w4AU3pTKGKzB0CPBJZ3A/apple-vision-pro-2.jpg",
			"https://s2-g1.glbimg.com/5BEQXk4AgqjhMxYOcb4jj9EVhI0=/0x0:5472x3648/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/S/l/uoBoQpSMSrv3iTuJPccg/2024-02-15t181817z-1939874556-rc2536asw41u-rtrmadp-3-usa-trump-new-york-daniels.jpg",
			"https://s2-g1.glbimg.com/P254LO0wSjrl1-fatv_sk3J8nJ4=/0x0:5000x3417/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/B/P/n4ufOBQPKVPbi8NW0Kog/2024-02-06t174630z-642274141-rc2vt5avpuf6-rtrmadp-3-usa-election-biden-michigan.jpg",
			"https://s2-g1.glbimg.com/YFwiPe2W-QCmPg01FuUMafEq_vs=/10x352:1080x1049/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/v/a/wAPFZLTuSg7medd77d6w/designerfrancesafogado-reproducaoinstagramlucien.jpeg",
			"https://s2-g1.glbimg.com/8rkb7HBbNvcjoavGY_E8NobLDFY=/0x0:800x532/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2023/6/i/BdAMAJS1GBKaDWxnP2IA/faustao.jpg",
			"https://s2-g1.glbimg.com/wHPGWUZHh0zQ4aUmVUPc3a9B6u8=/0x146:1912x886/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/n/e/NM3NHVTgeBtiPIwckqXw/globo-canal-5-20240227-0930-frame-18545.jpeg",
			"https://s2-g1.glbimg.com/7oMLFTLBIoNRjIKLBAFdzq0YqAA=/0x0:799x533/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2023/0/I/gGx1CWTkuu52MJQqrs3Q/marinho.jpg",
			"https://s2-g1.glbimg.com/-B8MzxWKrH3BpWqpSn7lnwu48CM=/0x0:840x472/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2021/5/3/r1sjdbRJ6TmA5ZiwqOeg/caixa-economica-federal0505202673-840x472.jpg",
			"https://s2-g1.glbimg.com/RvJ--aLBKZhCKomV9UG9EcMw8tQ=/0x0:1000x616/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2024/p/U/S4NHKTR3KkR5BHgudATA/img20240227122524011med.jpg" };

	private final String[][] categorias = {
			{ "Saúde", "Bem-estar", "Prevenção", "Tratamento", "Medicina", "Diagnóstico", "Nutrição", "Exercício",
					"Cuidados", "Higiene", "Vacinação" }, // saude
			{ "Política", "Economia", "Governo", "Democracia", "Eleição", "Partido político", "Orçamento", "Inflação",
					"Desenvolvimento econômico", "Mercado", "Imposto", "Reforma" }, // politica/economia
			{ "Ônibus", "Metro", "Trânsito", "CPTM", "Estrada", "Rodovia", "Trem", "Transporte público", "IPVA",
					"Transporte" }, // transporte
			{ "Educação", "Escola", "Ensino", "Aprendizagem", "Professor", "Aluno", "Matéria", "Currículo", "Pedagogia",
					"Universidade", "Conhecimento" }// educacao
	};

	@Autowired
	private TagRepository tagRepository;

	public String getRandomConteudo(String linkReportagem, String categoria, List<Tag> tags) {
		List<String> conteudo = new ArrayList<>();

		String videoIndisponivel = "<div class=video__indisponivel>"
				+ " <p>Vídeo Indisponível acesse a matéria oficial em: </p> " + "<a href='" + linkReportagem
				+ "' class=linkreportagem>Clique para ler notícia oficial</a> </div>";

		String videoDisponivel = "<div class=video__container><iframe width='620' height='379' src='https://www.youtube.com/embed/dR8DkuqRJps'  title='A virada de chave na bolsa após inflação e entrevista com Carla Argenta' frameborder='0' allow='accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share' allowfullscreen></iframe></div>";

		String citacao = "<div class=citacao>Et sequi dolores ad saepe accusantium ut necessitatibus sunt aut suscipit dolorem et nesciunt maiores a ipsam esse</div>";

		String tabela = "<table class=tabela> <tbody><tr> <td>Salário para base de cálculo</td> <td> Alíquota %</td> <td>Parcela em R$ a deduzir do IR</td> </tr> <tr> <td>Até 2.259,20</td> <td>0%</td> <td>0</td> </tr> <tr> <td>De 2.259,21 até 2.828,65</td> <td>7,5%</td> <td>R$ 169,44</td> </tr> <tr> <td>De 2.828,66 até 3.751,05</td> <td>15%</td> <td>R$ 381,44</td> </tr> <tr> <td>De R$ 3.751,06 até R$ 4.664,68</td> <td>22,5%</td> <td>R$ 662,77</td> </tr> <tr> <td>Acima de R$ 4.664,68</td> <td>27,5%</td> <td>R$ 896,00</td> </tr> </tbody></table>";

		String pubExterna = "<div class=.publi__externa style='display: flex; max-width: "
				+ "				 500px; width: 100%; margin-top: 10px; margin-bottom: 10px;'>"
				+ "				 <iframe id='twitter-widget-0' scrolling='no' frameborder='0' allowtransparency='true'"
				+ "				  allowfullscreen='true' class='' style='position: static; visibility: visible; width: 500px;"
				+ "				  height: 843px; display: block; flex-grow: 1;' title='X Post' "
				+ "				  src='https://platform.twitter.com/embed/Tweet.html?creatorScreenName=CNNBrasil&amp;dnt=true&amp;embedId=twitter-widget-0&amp;features=eyJ0ZndfdGltZWxpbmVfbGlzdCI6eyJidWNrZXQiOltdLCJ2ZXJzaW9uIjpudWxsfSwidGZ3X2ZvbGxvd2VyX2NvdW50X3N1bnNldCI6eyJidWNrZXQiOnRydWUsInZlcnNpb24iOm51bGx9LCJ0ZndfdHdlZXRfZWRpdF9iYWNrZW5kIjp7ImJ1Y2tldCI6Im9uIiwidmVyc2lvbiI6bnVsbH0sInRmd19yZWZzcmNfc2Vzc2lvbiI6eyJidWNrZXQiOiJvbiIsInZlcnNpb24iOm51bGx9LCJ0ZndfZm9zbnJfc29mdF9pbnRlcnZlbnRpb25zX2VuYWJsZWQiOnsiYnVja2V0Ijoib24iLCJ2ZXJzaW9uIjpudWxsfSwidGZ3X21peGVkX21lZGlhXzE1ODk3Ijp7ImJ1Y2tldCI6InRyZWF0bWVudCIsInZlcnNpb24iOm51bGx9LCJ0ZndfZXhwZXJpbWVudHNfY29va2llX2V4cGlyYXRpb24iOnsiYnVja2V0IjoxMjA5NjAwLCJ2ZXJzaW9uIjpudWxsfSwidGZ3X3Nob3dfYmlyZHdhdGNoX3Bpdm90c19lbmFibGVkIjp7ImJ1Y2tldCI6Im9uIiwidmVyc2lvbiI6bnVsbH0sInRmd19kdXBsaWNhdGVfc2NyaWJlc190b19zZXR0aW5ncyI6eyJidWNrZXQiOiJvbiIsInZlcnNpb24iOm51bGx9LCJ0ZndfdXNlX3Byb2ZpbGVfaW1hZ2Vfc2hhcGVfZW5hYmxlZCI6eyJidWNrZXQiOiJvbiIsInZlcnNpb24iOm51bGx9LCJ0ZndfdmlkZW9faGxzX2R5bmFtaWNfbWFuaWZlc3RzXzE1MDgyIjp7ImJ1Y2tldCI6InRydWVfYml0cmF0ZSIsInZlcnNpb24iOm51bGx9LCJ0ZndfbGVnYWN5X3RpbWVsaW5lX3N1bnNldCI6eyJidWNrZXQiOnRydWUsInZlcnNpb24iOm51bGx9LCJ0ZndfdHdlZXRfZWRpdF9mcm9udGVuZCI6eyJidWNrZXQiOiJvbiIsInZlcnNpb24iOm51bGx9fQ%3D%3D&amp;frame=false&amp;hideCard=false&amp;hideThread=false&amp;id=1762852842996408683&amp;lang=en&amp;origin=https%3A%2F%2Fwww.cnnbrasil.com.br%2Fentretenimento%2Fbbb%2Fbbb24-yasmin-leva-punicao-gravissima-e-perde-500-estalecas-por-nao-fazer-raio-x%2F&amp;sessionId=42de10f491cdcb1e3c51243d07f370a2d30dd77c&amp;siteScreenName=CNNBrasil&amp;theme=light&amp;widgetsVersion=2615f7e52b7e0%3A1702314776716&amp;width=500px'"
				+ "				  data-gtm-yt-inspected-8='true' data-tweet-id='1762852842996408683'"
				+ "				  data-gtm-yt-inspected-46923520_480='true' data-gtm-yt-inspected-46923520_534='true'></iframe></div>";

		// Adiciona de 3 a 7 parágrafos
		int numParagrafos = random.nextInt(7) + 4;
		for (int i = 0; i < numParagrafos; i++) {
			conteudo.add(getParagrafoParaConteudo(categoria, tags));
		}

		// Adiciona de 1 a 4 imagens, garantindo que a primeira imagem não tenha outra
		// imagem seguida
		int numImagens = random.nextInt(4) + 1;
		for (int i = 0; i < numImagens; i++) {
			conteudo.add(getImagemParaConteudo());
		}

		// Adiciona de 0 a 2 vídeos
		int numVideos = random.nextInt(3);
		for (int i = 0; i < numVideos; i++) {
			if (random.nextBoolean()) {
				conteudo.add(videoIndisponivel); 
			} else {
				conteudo.add(videoDisponivel); 
			}
		}

		// Adiciona de 0 a 2 citações
		int numCitacoes = random.nextInt(3);
		for (int i = 0; i < numCitacoes; i++) {
			conteudo.add(citacao); 
		}

		// Adiciona de 0 a 2 tabelas
		if(numImagens<3 && numParagrafos>5) {
		int numTabelas = random.nextInt(3);
		for (int i = 0; i < numTabelas; i++) {
			conteudo.add(tabela); 
		}
		}
		
		if(numImagens<3  && numParagrafos>5) {
		// Adiciona de 0 a 2 publicações externas
		int numPubExternas = random.nextInt(3);
		for (int i = 0; i < numPubExternas; i++) {
			conteudo.add(pubExterna); 
		}
		}

		  Collections.shuffle(conteudo);
		
		  boolean primeiraImagem = true;
		  int validaPrimeiraImagem = 0;
		  StringJoiner joiner = new StringJoiner("");
		  for (String elemento : conteudo) {
			  
			  if(validaPrimeiraImagem<1) {
			    if (elemento.contains("<img class='imagem__container'>")) {
			    	
			        if (primeiraImagem) {
			            primeiraImagem = false;
			        } else {
			            joiner.add(getParagrafoParaConteudo(categoria, tags));
			            validaPrimeiraImagem++;
			        }
			    }else if(!primeiraImagem) {
			    	validaPrimeiraImagem++;  
			    }
			  }
			  
			    joiner.add(elemento);
			}
		  
		  return joiner.toString();
	}

	private String getParagrafoParaConteudo(String categoria, List<Tag> tags) {
		String paragrafo = getRandoSubtitulo()
				+ "<p class=text__container>Lorem ipsum dolor sit amet. Aut voluptatem error qui eligendi porro et adipisci reprehenderit? Qui odio beatae qui adipisci ullam in delectus esse eum facere delectus ut cumque recusandae. Ea consequatur commodi non praesentium repudiandae est galisum deserunt."
				+ "Ex galisum excepturi eum dicta tenetur sit dolor dolorem! Id molestias consequatur vel illo adipisci sit aspernatur atque."
				+ "Et sequi dolores ad saepe accusantium ut necessitatibus sunt aut suscipit dolorem et nesciunt maiores a ipsam esse."
				+ " Aut dignissimos delectus quo quibusdam aspernatur est quia quia "
				+ getRandomComplementoTexto(categoria, tags) + "</p>";
		return paragrafo;
	}

	private String getImagemParaConteudo() {
		String imagem = "<img class='image__container' src='" + getRandomImagens() + "' >"
				+ getRandoDescricao();
		return imagem;
	}

	public String getRandomLink() {

		if (random.nextDouble() < 0.25) {
			return "https://httpstat.us/500?search?q=";
		} else {
			return "https://www.google.com/search?q=";
		}

	}

	public String getRandoDescricao() {

		if (random.nextDouble() < 0.25) {
			return "";
		} else {
			return "<p class=descricao__midia>" + " Descrição de midia exemplo"
					+ "	descricao ficticia — Foto: Arquivo Pessoal" + "</p>";
		}

	}

	public String getRandoSubtitulo() {

		if (random.nextDouble() < 0.75) {
			return "";
		} else {
			return "<div class=subtitulo>Lorem ipsum dolor sit amet. Aut voluptatem error qui "
					+ "eligendi porro et adipisci reprehenderit? Qui odio beatae qui adipisci ullam in delectus</div>";

		}
	}

	public LocalDateTime getRandomDateTime() {

		LocalDateTime now = LocalDateTime.now();

		int hours = random.nextInt(24);
		int minutes = random.nextInt(60);
		int chance = random.nextInt(100);

		if (chance < 30) {

			int daysAgo = random.nextInt(14);
			return now.minusDays(daysAgo).withHour(hours).withMinute(minutes).withSecond(0).withNano(0);
		} else if (chance < 60) {

			int daysAgo = random.nextInt(30);
			return now.minusDays(daysAgo).withHour(hours).withMinute(minutes).withSecond(0).withNano(0);
		} else {

			int daysAgo = random.nextInt(365);
			return now.minusDays(daysAgo).withHour(hours).withMinute(minutes).withSecond(0).withNano(0);
		}
	}

	public LocalDateTime getRandomDateTimeAtualizado(LocalDateTime dateTime) {

		int chance = random.nextInt(7);

		if (chance == 0) {
			return dateTime.plusHours(1);
		} else if (chance == 1) {
			return dateTime.plusHours(2);
		} else if (chance == 2) {
			return dateTime.plusHours(5);
		} else if (chance == 3) {
			return dateTime.plusHours(10);
		} else {
			return null;
		}
	}

	public String getRandomSites() {
		int randomSiteIndex = random.nextInt(sites.length);
		return sites[randomSiteIndex];
	}

	public String getRandomAutorias() {
		int randomAutoriaIndex = random.nextInt(autorias.length);
		return autorias[randomAutoriaIndex];
	}

	public String getRandomImagens() {
		int randomImagenIndex = random.nextInt(imagens.length);
		return imagens[randomImagenIndex];
	}
	
	public String getRandomImagensCapa() {
		int randomImagenIndex = random.nextInt(imagensCapa.length);
		return imagensCapa[randomImagenIndex];
	}

	public Long getRandomVisualizaca() {
		return random.nextLong(5001);
	}

	public List<Tag> getRandomTags() {
		List<Tag> selectedTags = new ArrayList<>();

		if (random.nextDouble() < 0.2) {
			return null;
		} else {
			Set<Integer> selectedIndexes = new HashSet<>();
			while (selectedIndexes.size() < random.nextInt(3) + 1) {
				int randomIndex = random.nextInt(tags.length);
				selectedIndexes.add(randomIndex);
			}

			for (Integer index : selectedIndexes) {
				String tagName = tags[index];
				Tag tag = tagRepository.findByNome(tagName);
				if (tag != null) {
					selectedTags.add(tag);
				}
			}
		}
		return selectedTags;
	}

	public String getRandomCategoria() {
		int randomCategoryIndex = random.nextInt(categorias.length);
		String[] category = categorias[randomCategoryIndex];
		int randomStringIndex = random.nextInt(category.length);
		return category[randomStringIndex];
	}

	public String getRandomComplementoTexto(String categoria, List<Tag> tags) {
		double randomValue = random.nextDouble();
		if (randomValue < 0.3 || tags == null) {
			return ".";
		} else {
			// Escolhe aleatoriamente entre categoria e tags
			String selectedTitle;
			if (randomValue < 0.6) {
				selectedTitle = "categoria " + categoria;
			} else {

				int randomIndex = random.nextInt(tags.size());
				selectedTitle = "Tag " + tags.get(randomIndex).getNome();

			}
			return "Texto utilizando a palavra da " + selectedTitle;
		}
	}

	public String getRandomTitulo(String categoria, List<Tag> tags) {
		double randomValue = random.nextDouble();
		if (randomValue < 0.4 || tags == null) {
			return "Texto Padrao de Titulo sem Tag ou Categoria nele";
		} else {
			// Escolhe aleatoriamente entre categoria e tags
			String selectedTitle;
			if (randomValue < 0.6) {
				selectedTitle = "categoria " + categoria;
			} else {

				int randomIndex = random.nextInt(tags.size());
				selectedTitle = "Tag " + tags.get(randomIndex).getNome();

			}
			return "Titulo utilizando a palavra da " + selectedTitle;
		}
	}

	public String getRandomSinopse(String categoria, List<Tag> tags) {
		double randomValue = random.nextDouble();
		String textoExtra = randomValue < 0.6
				? ", lorem ipsum dolor sit amet. Et rerum quisquam est adipisci culpa aut maxime iure et voluptatum architecto sit laborum suscipit est animi eaque."
				: "";
		if (randomValue < 0.2) {
			return "Sinopse Padrao sem Tag ou Categoria nela" + textoExtra;
		} else if (randomValue >= 0.2 && randomValue < 0.4 || tags == null) {
			return null;
		} else {
			// Escolhe aleatoriamente entre categoria e tags
			String selectedTitle;
			if (randomValue < 0.7) {
				selectedTitle = "categoria " + categoria;
			} else {

				int randomIndex = random.nextInt(tags.size());
				selectedTitle = "Tag " + tags.get(randomIndex).getNome();

			}
			return "Sinopse utilizando a palavra da " + selectedTitle + textoExtra;
		}
	}

	public String[] getTags() {
		return tags;
	}

}