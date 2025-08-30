package br.com.magportal.apimagspnewscadastro.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoAtualizar;
import br.com.magportal.apimagspnewscadastro.dto.noticia.NoticiaDtoCadastro;
import br.com.magportal.apimagspnewscadastro.entity.Noticia;
import br.com.magportal.apimagspnewscadastro.entity.Tag;
import br.com.magportal.apimagspnewscadastro.repository.NoticiaRepository;
import br.com.magportal.apimagspnewscadastro.repository.TagRepository;
import br.com.magportal.apimagspnewscadastro.validacoes.ValidacaoException;
import br.com.magportal.apimagspnewscadastro.validacoes.ValidacaoException404;

@Service
public class NoticiaService {

	private static final Logger logger = LoggerFactory.getLogger(NoticiaService.class);
	
	@Autowired
	private NoticiaRepository repository;

	@Autowired
	private TagRepository tagRepository; 

	public NoticiaDtoCadastro cadastrar(NoticiaDtoCadastro dados) {

		validaDuplicadas(dados);

		List<Tag> tags = validaTag(dados.getTags());

		Noticia entidade = new Noticia(dados, tags);

		repository.save(entidade);

		return new NoticiaDtoCadastro(entidade);

	}

	public NoticiaDtoCadastro atualizarCadastro(NoticiaDtoAtualizar dados, String linkNoticia) {

		Noticia entidade = repository.findByLinkDaNoticiaOficial(linkNoticia)
				.orElseThrow(() -> new ValidacaoException404("Não há nenhuma notícia com esse link"));

		List<Tag> tags = validaTag(dados.getTags());

		entidade.atualizarInformacoes(dados, tags);

		repository.save(entidade);

		return new NoticiaDtoCadastro(entidade);

	}

	private void validaDuplicadas(NoticiaDtoCadastro dados) {

		if (repository.existsByLinkDaNoticiaOficial(dados.getLinkDaNoticiaOficial())) {
			throw new ValidacaoException("noticia com esse link já registrado");
		} else if (repository.existsByTituloIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacao(dados.getTitulo(), dados.getSiteBuscado(), dados.getDataPublicacao())) {
			throw new ValidacaoException("noticia com esse titulo e autoria já registrado");
		}

	}

	private List<Tag> validaTag(List<Tag> tagsEnviadas) {

		if (tagsEnviadas != null) {
			List<Tag> tags = new ArrayList<>();

			for (Tag tagDto : tagsEnviadas) {
				 logger.info("Tag sendo cadastrada já existe, reutilizando tag existente");
				Tag tag = tagRepository.findByNome(tagDto.getNome());
				if (tag != null) {
					tags.add(tag);
				} else {
					logger.info("Tag sendo cadastrada não existe, criando nova tag");
					tag = new Tag(tagDto.getNome());
					tagRepository.save(tag);
					tags.add(tag);
				}
			}

			return tags;
		} else {
			return null;
		}
	}

}
