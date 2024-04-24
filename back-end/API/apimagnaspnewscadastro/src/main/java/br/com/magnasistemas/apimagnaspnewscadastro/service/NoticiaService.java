package br.com.magnasistemas.apimagnaspnewscadastro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.magnasistemas.apimagnaspnewscadastro.dto.noticia.NoticiaDtoAtualizar;
import br.com.magnasistemas.apimagnaspnewscadastro.dto.noticia.NoticiaDtoCadastro;
import br.com.magnasistemas.apimagnaspnewscadastro.entity.Noticia;
import br.com.magnasistemas.apimagnaspnewscadastro.entity.Tag;
import br.com.magnasistemas.apimagnaspnewscadastro.repository.NoticiaRepository;
import br.com.magnasistemas.apimagnaspnewscadastro.repository.TagRepository;
import br.com.magnasistemas.apimagnaspnewscadastro.validacoes.ValidacaoException;
import br.com.magnasistemas.apimagnaspnewscadastro.validacoes.ValidacaoException404;

@Service
public class NoticiaService {

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
				Tag tag = tagRepository.findByNome(tagDto.getNome());
				if (tag != null) {
					tags.add(tag);
				} else {
					// Se a tag não existir,
					// criar uma nova tag
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
