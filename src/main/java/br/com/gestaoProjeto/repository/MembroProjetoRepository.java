package br.com.gestaoProjeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gestaoProjeto.model.MembroProjeto;

public interface MembroProjetoRepository extends JpaRepository<MembroProjeto, Long> {

	Long countByIdProjeto(Long idProjeto);

	@Query("""
			SELECT COUNT(mp) FROM MembroProjeto mp
			WHERE mp.idMembro in :idMembro
			AND mp.projeto.status NOT IN ('ENCERRADO', 'CANCELADO')
			""")
	Long contarProjetosAtivosPorMembro(Long idMembro);
	
	boolean existsByIdMembroInAndIdProjeto(List<Long> idsMembros , Long idProjeto);
}
