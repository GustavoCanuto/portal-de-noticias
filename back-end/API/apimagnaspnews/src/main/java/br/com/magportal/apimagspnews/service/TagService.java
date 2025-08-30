package br.com.magportal.apimagspnews.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.magportal.apimagspnews.dto.tags.ListaTags;
import br.com.magportal.apimagspnews.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	private TagRepository repository;

	public Page<ListaTags> listarTags(Pageable paginacao) {

		return repository.findAll(paginacao).map(ListaTags::new);
	}


	
	@Cacheable(value = "cacheNoticiaCompleta", 
			key = "#nomeTag + '|' + #paginacao.pageNumber + '|' + #paginacao.pageSize", 
			condition = "#cacheManager == null")
	public List<ListaTags> listarTagsPorNome(String nomeTag, Pageable paginacao, String cacheManager) {
	    return repository.findByNomeContainingIgnoreCase(nomeTag, paginacao).stream()
	                                  .map(ListaTags::new)
	                                  .toList();
	}

	public Page<ListaTags> criarPaginacao(Pageable paginacao, List<ListaTags> tags) {
	    return new PageImpl<>(tags, paginacao, tags.size());
	}
	
}
