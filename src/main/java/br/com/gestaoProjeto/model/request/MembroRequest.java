package br.com.gestaoProjeto.model.request;

import br.com.gestaoProjeto.model.enums.Cargo;
import lombok.Builder;

@Builder
public record MembroRequest(Long id, String nome, Cargo cargo) {}
