package br.com.magportal.apimagspnewscadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.magportal.apimagspnewscadastro.entity.Tag;
import jakarta.transaction.Transactional;

public interface TagRepository extends JpaRepository<Tag, Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_tag; ALTER SEQUENCE tb_tag_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

	boolean existsByNome(String nomeTag);

	Tag findByNome(String nome);


}
