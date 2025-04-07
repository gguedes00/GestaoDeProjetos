package br.com.gestaoProjeto.repository;


import br.com.gestaoProjeto.model.response.ProjetoDashboardResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gestaoProjeto.model.Projeto;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
	@Query(value = """
			SELECT
            COUNT(*) FILTER (WHERE p.status = 'EM_ANALISE')         AS qtdEmAnalise,
            COUNT(*) FILTER (WHERE p.status = 'ANALISE_REALIZADA')    AS qtdAnaliseRealizada,
            COUNT(*) FILTER (WHERE p.status = 'ANALISE_APROVADA')     AS qtdAnaliseAprovada,
            COUNT(*) FILTER (WHERE p.status = 'INICIADO')             AS qtdIniciado,
            COUNT(*) FILTER (WHERE p.status = 'PLANEJADO')            AS qtdPlanejado,
            COUNT(*) FILTER (WHERE p.status = 'EM_ANDAMENTO')         AS qtdEmAndamento,
            COUNT(*) FILTER (WHERE p.status = 'ENCERRADO')            AS qtdEncerrado,
            COUNT(*) FILTER (WHERE p.status = 'CANCELADO')            AS qtdCancelado,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'EM_ANALISE')         AS totalOrcadoEmAnalise,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'ANALISE_REALIZADA')    AS totalOrcadoAnaliseRealizada,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'ANALISE_APROVADA')     AS totalOrcadoAnaliseAprovada,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'INICIADO')             AS totalOrcadoIniciado,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'PLANEJADO')            AS totalOrcadoPlanejado,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'EM_ANDAMENTO')         AS totalOrcadoEmAndamento,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'ENCERRADO')            AS totalOrcadoEncerrado,
            SUM(p.orcamento_total) FILTER (WHERE p.status = 'CANCELADO')            AS totalOrcadoCancelado,
            AVG(EXTRACT(EPOCH FROM AGE(p.previsao_termino, p.data_inicio)) / 86400)
                FILTER (WHERE p.status = 'ENCERRADO')                              AS mediaDiasEncerrados,
            (SELECT COUNT(DISTINCT prm.id_membro) FROM membro_projeto prm)         AS totalMembrosUnicos
        FROM Projeto p
        """, nativeQuery = true)
	List<ProjetoDashboardResponse> contarMembrosAlocados();
}


