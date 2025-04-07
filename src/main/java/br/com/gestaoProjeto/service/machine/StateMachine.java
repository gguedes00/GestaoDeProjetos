package br.com.gestaoProjeto.service.machine;

import br.com.gestaoProjeto.Exception.InvalidStatusTransitionException;
import br.com.gestaoProjeto.model.enums.Evento;
import br.com.gestaoProjeto.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateMachine {
    private Status currentState;

    public void processEvento(Evento evento) throws InvalidStatusTransitionException {
        if (evento == Evento.CANCELAR && currentState != Status.CANCELADO) {
            currentState = Status.CANCELADO;
            return;
        }

        if (evento == Evento.PROXIMO) {
            Status nextState = getNextState(currentState);
            if (nextState == null) {
                throw new InvalidStatusTransitionException(
                        "Não há transição definida para o próximo estado a partir de " + currentState
                );
            }
            currentState = nextState;
            return;
        }
        throw new InvalidStatusTransitionException(
                "Evento " + evento + " não é permitido a partir do estado " + currentState
        );
    }

    public Status getNextState(Status status) {
        switch (status) {
            case EM_ANALISE:
                return Status.ANALISE_REALIZADA;
            case ANALISE_REALIZADA:
                return Status.ANALISE_APROVADA;
            case ANALISE_APROVADA:
                return Status.INICIADO;
            case INICIADO:
                return Status.PLANEJADO;
            case PLANEJADO:
                return Status.EM_ANDAMENTO;
            case EM_ANDAMENTO:
                return Status.ENCERRADO;
            default:
                return null;
        }
    }
}