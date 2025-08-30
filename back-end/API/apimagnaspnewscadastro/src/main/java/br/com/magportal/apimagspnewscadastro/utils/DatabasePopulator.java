package br.com.magportal.apimagspnewscadastro.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import br.com.magportal.apimagspnewscadastro.entity.Noticia;
import br.com.magportal.apimagspnewscadastro.entity.Tag;
import br.com.magportal.apimagspnewscadastro.repository.NoticiaRepository;
import br.com.magportal.apimagspnewscadastro.repository.TagNoticiaRepository;
import br.com.magportal.apimagspnewscadastro.repository.TagRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Component
@ConditionalOnProperty(name = "spring.datasource.url", havingValue = "jdbc:postgresql://localhost:5432/magna_sp_news_test")
public class DatabasePopulator {

	@Autowired
	private NoticiaRepository noticiaRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private TagNoticiaRepository tagNoticiaRepository;

	@Autowired
	private DatabaseGenerator gerador;
	
	@PostConstruct
	@Transactional
	public void populateDatabaseNoticia() {

		tagNoticiaRepository.deleteAllAndResetSequence();
		tagRepository.deleteAllAndResetSequence();

		for (String tagName : gerador.getTags()) {
			Tag tag = new Tag(tagName);
			tagRepository.save(tag);
		}

		noticiaRepository.deleteAllAndResetSequence();

		for (int i = 0; i < 300; i++) {

			List<Tag> tags = gerador.getRandomTags();

			String categoria = gerador.getRandomCategoria();
			String titulo = gerador.getRandomTitulo(categoria, tags);
			String sinopse = gerador.getRandomSinopse(categoria, tags);
			String autoria = gerador.getRandomAutorias();
			String site = gerador.getRandomSites();
			String img = gerador.getRandomImagensCapa();
			String link = gerador.getRandomLink();
			Long visualizacao = gerador.getRandomVisualizaca();
			LocalDateTime dataHora = gerador.getRandomDateTime();
			LocalDateTime dataHoraAtualizado = gerador.getRandomDateTimeAtualizado(dataHora);
			String conteudo = gerador.getRandomConteudo(link+i,categoria, tags);
			
			Noticia noticia = new Noticia(
					site,
					link + i,
					categoria, 
					autoria,
					dataHora,
					dataHoraAtualizado,
					conteudo, 
					titulo,
					sinopse,
					img,
					visualizacao,
					tags);

			noticiaRepository.save(noticia);

		}
		
		//modelo especifico 
		for (int j = 1; j < 10; j++) {
			
			String categoriaExemplo = "Categoria Sem Palavra Chave";
			String tituloExemplo = "Titulo Sem Palavra Chave";
			String sinopseExemplo = "Sinopse Sem Palavra Chave";
			String conteudoExemplo = "Conteudo Sem Palavra chave";
			
			if(j<=2) {
				
				 categoriaExemplo = "PalavraChaveTesteBusca";
				
			}else if(j<=4) {
			
				 tituloExemplo = "PalavraChaveTesteBusca ";
				
			}else if(j<=6) {
				
				sinopseExemplo = "PalavraChaveTesteBusca";
				
			}else  {
	
				conteudoExemplo = "PalavraChaveTesteBusca";
			}
			
			String categoria =  categoriaExemplo ;
			String titulo =  tituloExemplo;
			String sinopse = sinopseExemplo;
			String autoria = gerador.getRandomAutorias();
			String site = "UOL";
			String img = gerador.getRandomImagensCapa();
			String link = "https://www.google.com/search?q=";
			LocalDateTime dataHora = LocalDateTime.of(1997, 02, 22, 16, 00);
			String conteudo = "<p class=text__container>" + conteudoExemplo+"</p>";
		
		Noticia noticia = new Noticia(
				site,
				link + (j+800000),
				categoria, 
				autoria,
				dataHora,
				null,
				conteudo, 
				titulo,
				sinopse,
				img,
				5L,
				null);

			noticiaRepository.save(noticia);

		}
	}

}