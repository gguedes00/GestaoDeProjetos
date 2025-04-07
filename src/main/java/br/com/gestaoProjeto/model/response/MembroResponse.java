package br.com.gestaoProjeto.model.response;

import br.com.gestaoProjeto.model.enums.Cargo;
import lombok.Builder;

@Builder
public record MembroResponse(Long id, String nome, Cargo cargo) {}
