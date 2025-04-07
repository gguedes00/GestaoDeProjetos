package br.com.gestaoProjeto.service;

import br.com.gestaoProjeto.Exception.InvalidStatusTransitionException;
import br.com.gestaoProjeto.Exception.ProjetoNaoPodeSerExcluidoException;
import br.com.gestaoProjeto.model.Projeto;
import br.com.gestaoProjeto.model.enums.Cargo;
import br.com.gestaoProjeto.model.enums.Evento;
import br.com.gestaoProjeto.model.enums.Risco;
import br.com.gestaoProjeto.model.enums.Status;
import br.com.gestaoProjeto.model.request.ProjetoRequest;
import br.com.gestaoProjeto.model.response.ProjetoDashboardResponse;
import br.com.gestaoProjeto.model.response.ProjetoResponse;
import br.com.gestaoProjeto.repository.ProjetoRepository;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

import br.com.gestaoProjeto.service.machine.StateMachine;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjetoService {

  private final ProjetoRepository projetoRepository;
  private final StateMachine stateMachine;

  public Long criarProjeto(ProjetoRequest request) {
    Projeto projeto = new Projeto();
    projeto.setNome(request.nome());
    projeto.setDataInicio(request.dataInicio());
    projeto.setPrevisaoTermino(request.previsaoTermino());
    projeto.setOrcamentoTotal(request.orcamentoTotal());
    projeto.setDescricao(request.descricao());

    projeto.setRisco(calcularRisco(projeto));
    validarSalvarProjetoComGerente(request);
    projeto.setIdGerente(request.membro().id());

    return projetoRepository.save(projeto).getId();
  }

  public ProjetoResponse buscarProjeto(Long id) {
    Projeto projeto = projetoRepository.findById(id).orElse(null);
    assert projeto != null;
    return modelParaRecord(projeto);
  }

  private ProjetoResponse modelParaRecord(Projeto projeto) {
    return ProjetoResponse.builder()
            .dataInicio(projeto.getDataInicio())
            .previsaoTermino(projeto.getPrevisaoTermino())
            .dataRealTermino(projeto.getDataRealTermino())
            .descricao(projeto.getDescricao())
            .gerenteId(projeto.getIdGerente())
            .id(projeto.getId())
            .status(projeto.getStatus())
            .risco(projeto.getRisco())
            .nome(projeto.getNome())
            .orcamentoTotal(projeto.getOrcamentoTotal())
            .build();
  }

  private static void validarSalvarProjetoComGerente(ProjetoRequest request) {
    if (!request.membro().cargo().equals(Cargo.GERENTE)) {
      throw new RuntimeException(
              "Apenas membros com cargo GERENTE podem ser atribuídos como gerentes do projeto");
    }
  }

  private Risco calcularRisco(Projeto projeto) {
    long meses = ChronoUnit.MONTHS.between(projeto.getDataInicio(), projeto.getPrevisaoTermino());
    BigDecimal orcamento = projeto.getOrcamentoTotal();

    if (orcamento.compareTo(new BigDecimal("500000")) > 0 || meses > 6) {
      return Risco.ALTO;
    } else if (orcamento.compareTo(new BigDecimal("100000")) <= 0 && meses <= 3) {
      return Risco.BAIXO;
    }
    return Risco.MEDIO;
  }

  public ProjetoResponse atualizarStatus(Long projetoId, Evento evento) throws InvalidStatusTransitionException {
    Projeto projeto =
            projetoRepository
                    .findById(projetoId)
                    .orElseThrow(() -> new EntityNotFoundException("Nao existe projeto com esse id"));
    stateMachine .setCurrentState(projeto.getStatus());
    stateMachine.processEvento(evento);
    projeto.setStatus(stateMachine.getCurrentState());
    return modelParaRecord(projetoRepository.save(projeto));
  }

  public void deletarProjeto(Long projetoId) throws ProjetoNaoPodeSerExcluidoException {
    Projeto projeto =
            projetoRepository
                    .findById(projetoId)
                    .orElseThrow(() -> new EntityNotFoundException("Nao existe projeto com esse id"));
   Status status = projeto.getStatus();
    if (status == Status.INICIADO ||
            status == Status.EM_ANDAMENTO ||
            status == Status.ENCERRADO) {
      throw new ProjetoNaoPodeSerExcluidoException(
              "Projetos com status INICIADO, EM_ANDAMENTO ou ENCERRADO não podem ser excluídos."
      );
    }

    projetoRepository.delete(projeto);
  }

 public List<ProjetoDashboardResponse> gerarRelatorioResumo() {
  return projetoRepository.contarMembrosAlocados();

 }


}


