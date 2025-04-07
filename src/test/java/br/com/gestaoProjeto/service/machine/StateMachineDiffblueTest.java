package br.com.gestaoProjeto.service.machine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.gestaoProjeto.Exception.InvalidStatusTransitionException;
import br.com.gestaoProjeto.model.enums.Evento;
import br.com.gestaoProjeto.model.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StateMachine.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class StateMachineDiffblueTest {
    @Autowired
    private StateMachine stateMachine;

    @Test
    @DisplayName("Test processEvento(Evento); when 'CANCELAR'; then StateMachine CurrentState is 'CANCELADO'")
    @Tag("MaintainedByDiffblue")
    void testProcessEvento_whenCancelar_thenStateMachineCurrentStateIsCancelado()
            throws InvalidStatusTransitionException {
        stateMachine.processEvento(Evento.CANCELAR);

        assertEquals(Status.CANCELADO, stateMachine.getCurrentState());
    }

    @Test
    @DisplayName("Test processEvento(Evento); when CANCELAR; then StateMachine CurrentState is 'CANCELADO'")
    @Tag("MaintainedByDiffblue")
    void testProcessEvento_whenCancelar_thenStateMachineCurrentStateIsCancelado2()
            throws InvalidStatusTransitionException {
        stateMachine.processEvento(Evento.CANCELAR);

        assertEquals(Status.CANCELADO, stateMachine.getCurrentState());
    }

    @Test
    @DisplayName("Test processEvento(Evento); when PROXIMO; then throw NullPointerException")
    @Tag("MaintainedByDiffblue")
    void testProcessEvento_whenProximo_thenThrowNullPointerException() throws InvalidStatusTransitionException {
        assertThrows(NullPointerException.class, () -> stateMachine.processEvento(Evento.PROXIMO));
    }

    @Test
    @DisplayName("Test getNextState(Status); when 'ANALISE_APROVADA'; then return 'INICIADO'")
    @Tag("MaintainedByDiffblue")
    void testGetNextState_whenAnaliseAprovada_thenReturnIniciado() {
        // Arrange, Act and Assert
        assertEquals(Status.INICIADO, stateMachine.getNextState(Status.ANALISE_APROVADA));
    }

    @Test
    @DisplayName("Test getNextState(Status); when 'ANALISE_REALIZADA'; then return 'ANALISE_APROVADA'")
    @Tag("MaintainedByDiffblue")
    void testGetNextState_whenAnaliseRealizada_thenReturnAnaliseAprovada() {
        assertEquals(Status.ANALISE_APROVADA, stateMachine.getNextState(Status.ANALISE_REALIZADA));
    }

    @Test
    @DisplayName("Test getNextState(Status); when 'EM_ANALISE'; then return 'ANALISE_REALIZADA'")
    @Tag("MaintainedByDiffblue")
    void testGetNextState_whenEmAnalise_thenReturnAnaliseRealizada() {
        assertEquals(Status.ANALISE_REALIZADA, stateMachine.getNextState(Status.EM_ANALISE));
    }

    @Test
    @DisplayName("Test getNextState(Status); when 'EM_ANDAMENTO'; then return 'ENCERRADO'")
    @Tag("MaintainedByDiffblue")
    void testGetNextState_whenEmAndamento_thenReturnEncerrado() {
        assertEquals(Status.ENCERRADO, stateMachine.getNextState(Status.EM_ANDAMENTO));
    }

    @Test
    @DisplayName("Test getNextState(Status); when 'ENCERRADO'; then return 'null'")
    @Tag("MaintainedByDiffblue")
    void testGetNextState_whenEncerrado_thenReturnNull() {
        assertNull(stateMachine.getNextState(Status.ENCERRADO));
    }

    @Test
    @DisplayName("Test getNextState(Status); when 'INICIADO'; then return 'PLANEJADO'")
    @Tag("MaintainedByDiffblue")
    void testGetNextState_whenIniciado_thenReturnPlanejado() {
        assertEquals(Status.PLANEJADO, stateMachine.getNextState(Status.INICIADO));
    }

    @Test
    @DisplayName("Test getNextState(Status); when 'PLANEJADO'; then return 'EM_ANDAMENTO'")
    @Tag("MaintainedByDiffblue")
    void testGetNextState_whenPlanejado_thenReturnEmAndamento() {
        assertEquals(Status.EM_ANDAMENTO, stateMachine.getNextState(Status.PLANEJADO));
    }
}
