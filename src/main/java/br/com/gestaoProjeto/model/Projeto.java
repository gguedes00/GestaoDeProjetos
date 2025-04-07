package br.com.gestaoProjeto.model;

import br.com.gestaoProjeto.model.enums.Risco;
import br.com.gestaoProjeto.model.enums.Status;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate previsaoTermino;

    private LocalDate dataRealTermino;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal orcamentoTotal;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.EM_ANALISE;

    @Enumerated(EnumType.STRING)
    private Risco risco;

    @Column(nullable = false)
    private Long idGerente;

}
