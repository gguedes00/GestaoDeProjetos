package br.com.gestaoProjeto.model.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ProjetoRequest(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotNull(message = "Data de início é obrigatória") LocalDate dataInicio,
        @NotNull(message = "Previsão de término é obrigatória") LocalDate previsaoTermino,
        @NotNull @DecimalMin("0.01") BigDecimal orcamentoTotal,
        String descricao,
        @NotNull @Positive MembroRequest membro){}
