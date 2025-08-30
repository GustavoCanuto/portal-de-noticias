package br.com.magportal.apimagspnewscadastro.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.magportal.apimagspnewscadastro.entity.Noticia;
import jakarta.transaction.Transactional;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

	Optional<Noticia> findByLinkDaNoticiaOficial(String linkNoticia);

	boolean existsByTituloIgnoreCaseAndSiteBuscadoIgnoreCaseAndDataPublicacao(String titulo, String site, LocalDateTime dataPublicacao);

	boolean existsByLinkDaNoticiaOficial(String linkNoticia);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_noticia; ALTER SEQUENCE tb_noticia_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

	

}
