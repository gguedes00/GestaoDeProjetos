package br.com.gestaoProjeto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembroProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idMembro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_projeto", nullable = false , insertable = false, updatable = false)
    private Projeto projeto;
    
    @Column(name = "id_projeto", nullable = false)
    private Long idProjeto;
    
}
