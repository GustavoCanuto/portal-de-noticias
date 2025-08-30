package br.com.magportal.apimagspnewscadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.magportal.apimagspnewscadastro.entity.TagNoticia;
import jakarta.transaction.Transactional;

public interface TagNoticiaRepository extends JpaRepository<TagNoticia, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_tag_noticia; ALTER SEQUENCE tb_tag_noticia_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

}
