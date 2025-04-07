package br.com.gestaoProjeto.model.response;

import br.com.gestaoProjeto.model.enums.Risco;
import br.com.gestaoProjeto.model.enums.Status;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Builder
public record ProjetoResponse(
    Long id,
    String nome,
    LocalDate dataInicio,
    LocalDate previsaoTermino,
    LocalDate dataRealTermino,
    BigDecimal orcamentoTotal,
    String descricao,
    Long gerenteId,
    Status status,
    Risco risco,
    Set<Long> membrosIds) {}
