package br.com.magportal.apimagspnews.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.magportal.apimagspnews.entity.Noticia;
import jakarta.transaction.Transactional;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

	//detalhar 
	Optional<Noticia> findByTituloIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacao(String titulo, String site, LocalDateTime dataPublicacao);
	
	//site
	
	Page<Noticia> findBySiteBuscadoIgnoreCase(String site, Pageable pageable);
	
	Page<Noticia> findBySiteBuscadoIgnoreCaseAndDataPublicacaoBetween(String site,
			LocalDateTime inicio, LocalDateTime fim, Pageable paginacao);

	// categoria ajustar para receber uma lista
	Page<Noticia> findByCategoriaIgnoreCaseIn(List<String>  categoria, Pageable pageable);
	
	Page<Noticia> findByCategoriaIgnoreCaseInAndSiteBuscadoIgnoreCase(List<String>  categoria, String site, Pageable paginacao);

	Page<Noticia> findByCategoriaIgnoreCaseInAndDataPublicacaoBetween(List<String> categoria,
			LocalDateTime inicio, LocalDateTime fim, Pageable paginacao);

	Page<Noticia> findByCategoriaIgnoreCaseInAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
			List<String>  categoria, String site, LocalDateTime inicio, LocalDateTime fim,  Pageable paginacao);

	// tag

	Page<Noticia> findByTagsNome(String nomeTag, Pageable pageable);

	Page<Noticia> findByTagsNomeAndSiteBuscadoIgnoreCase(String nomeTag, String site, Pageable paginacao);

	Page<Noticia> findByTagsNomeAndDataPublicacaoBetween(String nomeTag,
			LocalDateTime inicio, LocalDateTime fim, Pageable paginacao);

	Page<Noticia> findByTagsNomeAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
			String nomeTag,String site, LocalDateTime inicio, LocalDateTime fim,  Pageable paginacao);

	// sinopse
	
	Page<Noticia> findBySinopseContainingIgnoreCase(String sinopse, Pageable pageable);

	Page<Noticia> findBySinopseContainingIgnoreCaseAndSiteBuscadoIgnoreCase(String sinopse, String site, Pageable paginacao);

	Page<Noticia> findBySinopseContainingIgnoreCaseAndDataPublicacaoBetween(String sinopse,
			LocalDateTime inicio, LocalDateTime fim, Pageable paginacao);

	Page<Noticia> findBySinopseContainingIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
			String sinopse,String site, LocalDateTime inicio, LocalDateTime fim,  Pageable paginacao);

	// conteudo

	Page<Noticia> findByConteudoContainingIgnoreCase(String palavra , Pageable pageable);

	Page<Noticia> findByConteudoContainingIgnoreCaseAndSiteBuscadoIgnoreCase(String palavra, String site, Pageable paginacao);

	Page<Noticia> findByConteudoContainingIgnoreCaseAndDataPublicacaoBetween(String palavra,
			LocalDateTime inicio, LocalDateTime fim, Pageable paginacao);

	Page<Noticia> findByConteudoContainingIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
			String palavra, String site, LocalDateTime inicio, LocalDateTime fim,  Pageable paginacao);

	// por Titulo

	Page<Noticia> findByTituloContainingIgnoreCase(String palavra, Pageable pageable);

	Page<Noticia> findByTituloContainingIgnoreCaseAndSiteBuscadoIgnoreCase(String palavra, String site, Pageable paginacao);

	Page<Noticia> findByTituloContainingIgnoreCaseAndDataPublicacaoBetween(String palavra,
			LocalDateTime inicio, LocalDateTime fim, Pageable paginacao);

	Page<Noticia> findByTituloContainingIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacaoBetween(
			String palavra, String site, LocalDateTime inicio, LocalDateTime fim,  Pageable paginacao);

	// Fixo
	Page<Noticia> findByDataPublicacaoBetween(LocalDateTime inicio, LocalDateTime fim,
			Pageable pageable);

	Page<Noticia> findByDataPublicacaoBetweenOrderByNumeroVisualizacaoDesc(LocalDateTime inicio, LocalDateTime fim,
			Pageable pageable);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_noticia; ALTER SEQUENCE tb_noticia_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

	

}
