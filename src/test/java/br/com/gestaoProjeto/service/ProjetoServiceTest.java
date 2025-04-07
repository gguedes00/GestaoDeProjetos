package br.com.gestaoProjeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.gestaoProjeto.Exception.InvalidStatusTransitionException;
import br.com.gestaoProjeto.Exception.ProjetoNaoPodeSerExcluidoException;
import br.com.gestaoProjeto.model.Projeto;
import br.com.gestaoProjeto.model.enums.Cargo;
import br.com.gestaoProjeto.model.enums.Evento;
import br.com.gestaoProjeto.model.enums.Risco;
import br.com.gestaoProjeto.model.enums.Status;
import br.com.gestaoProjeto.model.request.MembroRequest;
import br.com.gestaoProjeto.model.request.ProjetoRequest;
import br.com.gestaoProjeto.model.response.ProjetoDashboardResponse;
import br.com.gestaoProjeto.model.response.ProjetoResponse;
import br.com.gestaoProjeto.repository.ProjetoRepository;
import br.com.gestaoProjeto.service.machine.StateMachine;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private StateMachine stateMachine;

    @InjectMocks
    private ProjetoService projetoService;


    @Test
    @DisplayName("Test criarProjeto(ProjetoRequest)")
    void testCriarProjeto() {
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.now();
        BigDecimal orcamentoTotal = new BigDecimal("2.3");

        assertThrows(RuntimeException.class, () -> projetoService.criarProjeto(new ProjetoRequest("Nome", dataInicio,
                previsaoTermino, orcamentoTotal, "Descricao", new MembroRequest(1L, "Nome", Cargo.FUNCIONARIO))));
    }

    @Test
    @DisplayName("Test criarProjeto(ProjetoRequest); given Projeto() DataInicio is LocalDate with '1970' and one and one")
    void testCriarProjeto_givenProjetoDataInicioIsLocalDateWith1970AndOneAndOne() {
        Projeto projeto = new Projeto();
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        when(projetoRepository.save(Mockito.<Projeto>any())).thenReturn(projeto);
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("2.3");

        Long actualCriarProjetoResult = projetoService.criarProjeto(new ProjetoRequest("Nome", dataInicio, previsaoTermino,
                orcamentoTotal, "Descricao", new MembroRequest(1L, "Nome", Cargo.GERENTE)));

        verify(projetoRepository).save(isA(Projeto.class));
        assertEquals(1L, actualCriarProjetoResult.longValue());
    }

    @Test
    @DisplayName("Test criarProjeto(ProjetoRequest); then calls getId()")
    void testCriarProjeto_thenCallsGetId() {
        Projeto projeto = mock(Projeto.class);
        when(projeto.getId()).thenReturn(1L);
        doNothing().when(projeto).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDescricao(Mockito.<String>any());
        doNothing().when(projeto).setId(Mockito.<Long>any());
        doNothing().when(projeto).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto).setNome(Mockito.<String>any());
        doNothing().when(projeto).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto).setStatus(Mockito.<Status>any());
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        when(projetoRepository.save(Mockito.<Projeto>any())).thenReturn(projeto);
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("2.3");

        Long actualCriarProjetoResult = projetoService.criarProjeto(new ProjetoRequest("Nome", dataInicio, previsaoTermino,
                orcamentoTotal, "Descricao", new MembroRequest(1L, "Nome", Cargo.GERENTE)));

        verify(projeto).getId();
        verify(projeto).setDataInicio(isA(LocalDate.class));
        verify(projeto).setDataRealTermino(isA(LocalDate.class));
        verify(projeto).setDescricao(eq("Descricao"));
        verify(projeto).setId(eq(1L));
        verify(projeto).setIdGerente(eq(1L));
        verify(projeto).setNome(eq("Nome"));
        verify(projeto).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto).setRisco(eq(Risco.BAIXO));
        verify(projeto).setStatus(eq(Status.EM_ANALISE));
        verify(projetoRepository).save(isA(Projeto.class));
        assertEquals(1L, actualCriarProjetoResult.longValue());
    }

    @Test
    @DisplayName("Test criarProjeto(ProjetoRequest); then throw RuntimeException")
    void testCriarProjeto_thenThrowRuntimeException() {
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("2.3");

        assertThrows(RuntimeException.class, () -> projetoService.criarProjeto(new ProjetoRequest("Nome", dataInicio,
                previsaoTermino, orcamentoTotal, "Descricao", new MembroRequest(1L, "Nome", Cargo.FUNCIONARIO))));
    }


    @Test
    @DisplayName("Test criarProjeto(ProjetoRequest); when BigDecimal(String) with '500000'; then throw RuntimeException")
    void testCriarProjeto_whenBigDecimalWith500000_thenThrowRuntimeException() {
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("500000");

        assertThrows(RuntimeException.class, () -> projetoService.criarProjeto(new ProjetoRequest("Nome", dataInicio,
                previsaoTermino, orcamentoTotal, "Descricao", new MembroRequest(1L, "Nome", Cargo.FUNCIONARIO))));
    }

    @Test
    @DisplayName("Test buscarProjeto(Long); then return orcamentoTotal is BigDecimal(String) with '2.3'")
    void testBuscarProjeto_thenReturnOrcamentoTotalIsBigDecimalWith23() {
        Projeto projeto = mock(Projeto.class);
        when(projeto.getRisco()).thenReturn(Risco.BAIXO);
        when(projeto.getStatus()).thenReturn(Status.EM_ANALISE);
        when(projeto.getId()).thenReturn(1L);
        when(projeto.getIdGerente()).thenReturn(1L);
        when(projeto.getDescricao()).thenReturn("Descricao");
        when(projeto.getNome()).thenReturn("Nome");
        when(projeto.getOrcamentoTotal()).thenReturn(new BigDecimal("2.3"));
        LocalDate ofResult = LocalDate.of(1970, 1, 1);
        when(projeto.getDataInicio()).thenReturn(ofResult);
        LocalDate ofResult2 = LocalDate.of(1970, 1, 1);
        when(projeto.getDataRealTermino()).thenReturn(ofResult2);
        LocalDate ofResult3 = LocalDate.of(1970, 1, 1);
        when(projeto.getPrevisaoTermino()).thenReturn(ofResult3);
        doNothing().when(projeto).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDescricao(Mockito.<String>any());
        doNothing().when(projeto).setId(Mockito.<Long>any());
        doNothing().when(projeto).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto).setNome(Mockito.<String>any());
        doNothing().when(projeto).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto).setStatus(Mockito.<Status>any());
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult4 = Optional.of(projeto);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult4);

        ProjetoResponse actualBuscarProjetoResult = projetoService.buscarProjeto(1L);

        verify(projeto).getDataInicio();
        verify(projeto).getDataRealTermino();
        verify(projeto).getDescricao();
        verify(projeto).getId();
        verify(projeto).getIdGerente();
        verify(projeto).getNome();
        verify(projeto).getOrcamentoTotal();
        verify(projeto).getPrevisaoTermino();
        verify(projeto).getRisco();
        verify(projeto).getStatus();
        verify(projeto).setDataInicio(isA(LocalDate.class));
        verify(projeto).setDataRealTermino(isA(LocalDate.class));
        verify(projeto).setDescricao(eq("Descricao"));
        verify(projeto).setId(eq(1L));
        verify(projeto).setIdGerente(eq(1L));
        verify(projeto).setNome(eq("Nome"));
        verify(projeto).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto).setRisco(eq(Risco.BAIXO));
        verify(projeto).setStatus(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
        assertEquals("Descricao", actualBuscarProjetoResult.descricao());
        assertEquals("Nome", actualBuscarProjetoResult.nome());
        assertNull(actualBuscarProjetoResult.membrosIds());
        assertEquals(1L, actualBuscarProjetoResult.gerenteId().longValue());
        assertEquals(1L, actualBuscarProjetoResult.id().longValue());
        assertEquals(Risco.BAIXO, actualBuscarProjetoResult.risco());
        assertEquals(Status.EM_ANALISE, actualBuscarProjetoResult.status());
        BigDecimal expectedOrcamentoTotalResult = new BigDecimal("2.3");
        assertEquals(expectedOrcamentoTotalResult, actualBuscarProjetoResult.orcamentoTotal());
        assertSame(ofResult, actualBuscarProjetoResult.dataInicio());
        assertSame(ofResult2, actualBuscarProjetoResult.dataRealTermino());
        assertSame(ofResult3, actualBuscarProjetoResult.previsaoTermino());
    }

    @Test
    @DisplayName("Test buscarProjeto(Long); then return orcamentoTotal is 'null'")
    void testBuscarProjeto_thenReturnOrcamentoTotalIsNull() {
        Projeto projeto = new Projeto();
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        projeto.setDataInicio(dataInicio);
        LocalDate dataRealTermino = LocalDate.of(1970, 1, 1);
        projeto.setDataRealTermino(dataRealTermino);
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        projeto.setPrevisaoTermino(previsaoTermino);
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ProjetoResponse actualBuscarProjetoResult = projetoService.buscarProjeto(1L);

        verify(projetoRepository).findById(eq(1L));
        assertEquals("Descricao", actualBuscarProjetoResult.descricao());
        assertEquals("Nome", actualBuscarProjetoResult.nome());
        assertNull(actualBuscarProjetoResult.orcamentoTotal());
        assertNull(actualBuscarProjetoResult.membrosIds());
        assertEquals(1L, actualBuscarProjetoResult.gerenteId().longValue());
        assertEquals(1L, actualBuscarProjetoResult.id().longValue());
        assertEquals(Risco.BAIXO, actualBuscarProjetoResult.risco());
        assertEquals(Status.EM_ANALISE, actualBuscarProjetoResult.status());
        assertSame(dataInicio, actualBuscarProjetoResult.dataInicio());
        assertSame(dataRealTermino, actualBuscarProjetoResult.dataRealTermino());
        assertSame(previsaoTermino, actualBuscarProjetoResult.previsaoTermino());
    }

    @Test
    @DisplayName("Test atualizarStatus(Long, Evento); given ProjetoRepository findById(Object) return Optional with Projeto")
    void testAtualizarStatus_givenProjetoRepositoryFindByIdReturnOptionalWithProjeto()
            throws InvalidStatusTransitionException {
        Projeto projeto = mock(Projeto.class);
        when(projeto.getStatus()).thenReturn(Status.EM_ANALISE);
        doNothing().when(projeto).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDescricao(Mockito.<String>any());
        doNothing().when(projeto).setId(Mockito.<Long>any());
        doNothing().when(projeto).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto).setNome(Mockito.<String>any());
        doNothing().when(projeto).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto).setStatus(Mockito.<Status>any());
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        Projeto projeto2 = mock(Projeto.class);
        when(projeto2.getRisco()).thenReturn(Risco.BAIXO);
        when(projeto2.getStatus()).thenReturn(Status.EM_ANALISE);
        when(projeto2.getId()).thenReturn(1L);
        when(projeto2.getIdGerente()).thenReturn(1L);
        when(projeto2.getDescricao()).thenReturn("Descricao");
        when(projeto2.getNome()).thenReturn("Nome");
        when(projeto2.getOrcamentoTotal()).thenReturn(new BigDecimal("2.3"));
        LocalDate ofResult2 = LocalDate.of(1970, 1, 1);
        when(projeto2.getDataInicio()).thenReturn(ofResult2);
        LocalDate ofResult3 = LocalDate.of(1970, 1, 1);
        when(projeto2.getDataRealTermino()).thenReturn(ofResult3);
        LocalDate ofResult4 = LocalDate.of(1970, 1, 1);
        when(projeto2.getPrevisaoTermino()).thenReturn(ofResult4);
        doNothing().when(projeto2).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto2).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto2).setDescricao(Mockito.<String>any());
        doNothing().when(projeto2).setId(Mockito.<Long>any());
        doNothing().when(projeto2).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto2).setNome(Mockito.<String>any());
        doNothing().when(projeto2).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto2).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto2).setStatus(Mockito.<Status>any());
        projeto2.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto2.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto2.setDescricao("Descricao");
        projeto2.setId(1L);
        projeto2.setIdGerente(1L);
        projeto2.setNome("Nome");
        projeto2.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto2.setRisco(Risco.BAIXO);
        projeto2.setStatus(Status.EM_ANALISE);
        when(projetoRepository.save(Mockito.<Projeto>any())).thenReturn(projeto2);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(stateMachine.getCurrentState()).thenReturn(Status.EM_ANALISE);
        doNothing().when(stateMachine).processEvento(Mockito.<Evento>any());
        doNothing().when(stateMachine).setCurrentState(Mockito.<Status>any());

        ProjetoResponse actualAtualizarStatusResult = projetoService.atualizarStatus(1L, Evento.PROXIMO);

        verify(projeto2).getDataInicio();
        verify(projeto2).getDataRealTermino();
        verify(projeto2).getDescricao();
        verify(projeto2).getId();
        verify(projeto2).getIdGerente();
        verify(projeto2).getNome();
        verify(projeto2).getOrcamentoTotal();
        verify(projeto2).getPrevisaoTermino();
        verify(projeto2).getRisco();
        verify(projeto2).getStatus();
        verify(projeto).getStatus();
        verify(projeto2).setDataInicio(isA(LocalDate.class));
        verify(projeto).setDataInicio(isA(LocalDate.class));
        verify(projeto2).setDataRealTermino(isA(LocalDate.class));
        verify(projeto).setDataRealTermino(isA(LocalDate.class));
        verify(projeto2).setDescricao(eq("Descricao"));
        verify(projeto).setDescricao(eq("Descricao"));
        verify(projeto2).setId(eq(1L));
        verify(projeto).setId(eq(1L));
        verify(projeto2).setIdGerente(eq(1L));
        verify(projeto).setIdGerente(eq(1L));
        verify(projeto2).setNome(eq("Nome"));
        verify(projeto).setNome(eq("Nome"));
        verify(projeto2).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto2).setRisco(eq(Risco.BAIXO));
        verify(projeto).setRisco(eq(Risco.BAIXO));
        verify(projeto2).setStatus(eq(Status.EM_ANALISE));
        verify(projeto, atLeast(1)).setStatus(eq(Status.EM_ANALISE));
        verify(stateMachine).getCurrentState();
        verify(stateMachine).processEvento(eq(Evento.PROXIMO));
        verify(stateMachine).setCurrentState(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
        verify(projetoRepository).save(isA(Projeto.class));
        assertEquals("Descricao", actualAtualizarStatusResult.descricao());
        assertEquals("Nome", actualAtualizarStatusResult.nome());
        assertNull(actualAtualizarStatusResult.membrosIds());
        assertEquals(1L, actualAtualizarStatusResult.gerenteId().longValue());
        assertEquals(1L, actualAtualizarStatusResult.id().longValue());
        assertEquals(Risco.BAIXO, actualAtualizarStatusResult.risco());
        assertEquals(Status.EM_ANALISE, actualAtualizarStatusResult.status());
        BigDecimal expectedOrcamentoTotalResult = new BigDecimal("2.3");
        assertEquals(expectedOrcamentoTotalResult, actualAtualizarStatusResult.orcamentoTotal());
        assertSame(ofResult2, actualAtualizarStatusResult.dataInicio());
        assertSame(ofResult3, actualAtualizarStatusResult.dataRealTermino());
        assertSame(ofResult4, actualAtualizarStatusResult.previsaoTermino());
    }

    @Test
    @DisplayName("Test atualizarStatus(Long, Evento); then return orcamentoTotal is BigDecimal(String) with '2.3'")
    void testAtualizarStatus_thenReturnOrcamentoTotalIsBigDecimalWith23() throws InvalidStatusTransitionException {
        Projeto projeto = new Projeto();
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        Projeto projeto2 = mock(Projeto.class);
        when(projeto2.getRisco()).thenReturn(Risco.BAIXO);
        when(projeto2.getStatus()).thenReturn(Status.EM_ANALISE);
        when(projeto2.getId()).thenReturn(1L);
        when(projeto2.getIdGerente()).thenReturn(1L);
        when(projeto2.getDescricao()).thenReturn("Descricao");
        when(projeto2.getNome()).thenReturn("Nome");
        when(projeto2.getOrcamentoTotal()).thenReturn(new BigDecimal("2.3"));
        LocalDate ofResult2 = LocalDate.of(1970, 1, 1);
        when(projeto2.getDataInicio()).thenReturn(ofResult2);
        LocalDate ofResult3 = LocalDate.of(1970, 1, 1);
        when(projeto2.getDataRealTermino()).thenReturn(ofResult3);
        LocalDate ofResult4 = LocalDate.of(1970, 1, 1);
        when(projeto2.getPrevisaoTermino()).thenReturn(ofResult4);
        doNothing().when(projeto2).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto2).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto2).setDescricao(Mockito.<String>any());
        doNothing().when(projeto2).setId(Mockito.<Long>any());
        doNothing().when(projeto2).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto2).setNome(Mockito.<String>any());
        doNothing().when(projeto2).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto2).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto2).setStatus(Mockito.<Status>any());
        projeto2.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto2.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto2.setDescricao("Descricao");
        projeto2.setId(1L);
        projeto2.setIdGerente(1L);
        projeto2.setNome("Nome");
        projeto2.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto2.setRisco(Risco.BAIXO);
        projeto2.setStatus(Status.EM_ANALISE);
        when(projetoRepository.save(Mockito.<Projeto>any())).thenReturn(projeto2);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(stateMachine.getCurrentState()).thenReturn(Status.EM_ANALISE);
        doNothing().when(stateMachine).processEvento(Mockito.<Evento>any());
        doNothing().when(stateMachine).setCurrentState(Mockito.<Status>any());

        ProjetoResponse actualAtualizarStatusResult = projetoService.atualizarStatus(1L, Evento.PROXIMO);

        verify(projeto2).getDataInicio();
        verify(projeto2).getDataRealTermino();
        verify(projeto2).getDescricao();
        verify(projeto2).getId();
        verify(projeto2).getIdGerente();
        verify(projeto2).getNome();
        verify(projeto2).getOrcamentoTotal();
        verify(projeto2).getPrevisaoTermino();
        verify(projeto2).getRisco();
        verify(projeto2).getStatus();
        verify(projeto2).setDataInicio(isA(LocalDate.class));
        verify(projeto2).setDataRealTermino(isA(LocalDate.class));
        verify(projeto2).setDescricao(eq("Descricao"));
        verify(projeto2).setId(eq(1L));
        verify(projeto2).setIdGerente(eq(1L));
        verify(projeto2).setNome(eq("Nome"));
        verify(projeto2).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto2).setRisco(eq(Risco.BAIXO));
        verify(projeto2).setStatus(eq(Status.EM_ANALISE));
        verify(stateMachine).getCurrentState();
        verify(stateMachine).processEvento(eq(Evento.PROXIMO));
        verify(stateMachine).setCurrentState(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
        verify(projetoRepository).save(isA(Projeto.class));
        assertEquals("Descricao", actualAtualizarStatusResult.descricao());
        assertEquals("Nome", actualAtualizarStatusResult.nome());
        assertNull(actualAtualizarStatusResult.membrosIds());
        assertEquals(1L, actualAtualizarStatusResult.gerenteId().longValue());
        assertEquals(1L, actualAtualizarStatusResult.id().longValue());
        assertEquals(Risco.BAIXO, actualAtualizarStatusResult.risco());
        assertEquals(Status.EM_ANALISE, actualAtualizarStatusResult.status());
        BigDecimal expectedOrcamentoTotalResult = new BigDecimal("2.3");
        assertEquals(expectedOrcamentoTotalResult, actualAtualizarStatusResult.orcamentoTotal());
        assertSame(ofResult2, actualAtualizarStatusResult.dataInicio());
        assertSame(ofResult3, actualAtualizarStatusResult.dataRealTermino());
        assertSame(ofResult4, actualAtualizarStatusResult.previsaoTermino());
    }

    @Test
    @DisplayName("Test atualizarStatus(Long, Evento); then return orcamentoTotal is 'null'")
    void testAtualizarStatus_thenReturnOrcamentoTotalIsNull() throws InvalidStatusTransitionException {
        Projeto projeto = new Projeto();
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);

        Projeto projeto2 = new Projeto();
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        projeto2.setDataInicio(dataInicio);
        LocalDate dataRealTermino = LocalDate.of(1970, 1, 1);
        projeto2.setDataRealTermino(dataRealTermino);
        projeto2.setDescricao("Descricao");
        projeto2.setId(1L);
        projeto2.setIdGerente(1L);
        projeto2.setNome("Nome");
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        projeto2.setPrevisaoTermino(previsaoTermino);
        projeto2.setRisco(Risco.BAIXO);
        projeto2.setStatus(Status.EM_ANALISE);
        when(projetoRepository.save(Mockito.<Projeto>any())).thenReturn(projeto2);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(stateMachine.getCurrentState()).thenReturn(Status.EM_ANALISE);
        doNothing().when(stateMachine).processEvento(Mockito.<Evento>any());
        doNothing().when(stateMachine).setCurrentState(Mockito.<Status>any());

        ProjetoResponse actualAtualizarStatusResult = projetoService.atualizarStatus(1L, Evento.PROXIMO);

        verify(stateMachine).getCurrentState();
        verify(stateMachine).processEvento(eq(Evento.PROXIMO));
        verify(stateMachine).setCurrentState(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
        verify(projetoRepository).save(isA(Projeto.class));
        assertEquals("Descricao", actualAtualizarStatusResult.descricao());
        assertEquals("Nome", actualAtualizarStatusResult.nome());
        assertNull(actualAtualizarStatusResult.orcamentoTotal());
        assertNull(actualAtualizarStatusResult.membrosIds());
        assertEquals(1L, actualAtualizarStatusResult.gerenteId().longValue());
        assertEquals(1L, actualAtualizarStatusResult.id().longValue());
        assertEquals(Risco.BAIXO, actualAtualizarStatusResult.risco());
        assertEquals(Status.EM_ANALISE, actualAtualizarStatusResult.status());
        assertSame(dataInicio, actualAtualizarStatusResult.dataInicio());
        assertSame(dataRealTermino, actualAtualizarStatusResult.dataRealTermino());
        assertSame(previsaoTermino, actualAtualizarStatusResult.previsaoTermino());
    }

    @Test
    @DisplayName("Test atualizarStatus(Long, Evento); then throw EntityNotFoundException")
    void testAtualizarStatus_thenThrowEntityNotFoundException() throws InvalidStatusTransitionException {
        Projeto projeto = new Projeto();
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doThrow(new EntityNotFoundException("An error occurred")).when(stateMachine).setCurrentState(Mockito.<Status>any());

        assertThrows(EntityNotFoundException.class, () -> projetoService.atualizarStatus(1L, Evento.PROXIMO));
        verify(stateMachine).setCurrentState(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long); given Projeto() DataInicio is LocalDate with '1970' and one and one")
    void testDeletarProjeto_givenProjetoDataInicioIsLocalDateWith1970AndOneAndOne()
            throws ProjetoNaoPodeSerExcluidoException {
        Projeto projeto = new Projeto();
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        doNothing().when(projetoRepository).delete(Mockito.<Projeto>any());
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        projetoService.deletarProjeto(1L);

        verify(projetoRepository).delete(isA(Projeto.class));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long); given Projeto getStatus() return 'EM_ANALISE'; then calls delete(Object)")
    void testDeletarProjeto_givenProjetoGetStatusReturnEmAnalise_thenCallsDelete()
            throws ProjetoNaoPodeSerExcluidoException {
        Projeto projeto = mock(Projeto.class);
        when(projeto.getStatus()).thenReturn(Status.EM_ANALISE);
        doNothing().when(projeto).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDescricao(Mockito.<String>any());
        doNothing().when(projeto).setId(Mockito.<Long>any());
        doNothing().when(projeto).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto).setNome(Mockito.<String>any());
        doNothing().when(projeto).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto).setStatus(Mockito.<Status>any());
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        doNothing().when(projetoRepository).delete(Mockito.<Projeto>any());
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        projetoService.deletarProjeto(1L);

        verify(projeto).getStatus();
        verify(projeto).setDataInicio(isA(LocalDate.class));
        verify(projeto).setDataRealTermino(isA(LocalDate.class));
        verify(projeto).setDescricao(eq("Descricao"));
        verify(projeto).setId(eq(1L));
        verify(projeto).setIdGerente(eq(1L));
        verify(projeto).setNome(eq("Nome"));
        verify(projeto).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto).setRisco(eq(Risco.BAIXO));
        verify(projeto).setStatus(eq(Status.EM_ANALISE));
        verify(projetoRepository).delete(isA(Projeto.class));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long); given Projeto getStatus() return 'EM_ANDAMENTO'")
    void testDeletarProjeto_givenProjetoGetStatusReturnEmAndamento() throws ProjetoNaoPodeSerExcluidoException {
        Projeto projeto = mock(Projeto.class);
        when(projeto.getStatus()).thenReturn(Status.EM_ANDAMENTO);
        doNothing().when(projeto).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDescricao(Mockito.<String>any());
        doNothing().when(projeto).setId(Mockito.<Long>any());
        doNothing().when(projeto).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto).setNome(Mockito.<String>any());
        doNothing().when(projeto).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto).setStatus(Mockito.<Status>any());
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        assertThrows(ProjetoNaoPodeSerExcluidoException.class, () -> projetoService.deletarProjeto(1L));
        verify(projeto).getStatus();
        verify(projeto).setDataInicio(isA(LocalDate.class));
        verify(projeto).setDataRealTermino(isA(LocalDate.class));
        verify(projeto).setDescricao(eq("Descricao"));
        verify(projeto).setId(eq(1L));
        verify(projeto).setIdGerente(eq(1L));
        verify(projeto).setNome(eq("Nome"));
        verify(projeto).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto).setRisco(eq(Risco.BAIXO));
        verify(projeto).setStatus(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long); given Projeto getStatus() return 'ENCERRADO'")
    void testDeletarProjeto_givenProjetoGetStatusReturnEncerrado() throws ProjetoNaoPodeSerExcluidoException {
        Projeto projeto = mock(Projeto.class);
        when(projeto.getStatus()).thenReturn(Status.ENCERRADO);
        doNothing().when(projeto).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDescricao(Mockito.<String>any());
        doNothing().when(projeto).setId(Mockito.<Long>any());
        doNothing().when(projeto).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto).setNome(Mockito.<String>any());
        doNothing().when(projeto).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto).setStatus(Mockito.<Status>any());
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        assertThrows(ProjetoNaoPodeSerExcluidoException.class, () -> projetoService.deletarProjeto(1L));
        verify(projeto).getStatus();
        verify(projeto).setDataInicio(isA(LocalDate.class));
        verify(projeto).setDataRealTermino(isA(LocalDate.class));
        verify(projeto).setDescricao(eq("Descricao"));
        verify(projeto).setId(eq(1L));
        verify(projeto).setIdGerente(eq(1L));
        verify(projeto).setNome(eq("Nome"));
        verify(projeto).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto).setRisco(eq(Risco.BAIXO));
        verify(projeto).setStatus(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long); given Projeto getStatus() return 'INICIADO'")
    void testDeletarProjeto_givenProjetoGetStatusReturnIniciado() throws ProjetoNaoPodeSerExcluidoException {
        Projeto projeto = mock(Projeto.class);
        when(projeto.getStatus()).thenReturn(Status.INICIADO);
        doNothing().when(projeto).setDataInicio(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDataRealTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setDescricao(Mockito.<String>any());
        doNothing().when(projeto).setId(Mockito.<Long>any());
        doNothing().when(projeto).setIdGerente(Mockito.<Long>any());
        doNothing().when(projeto).setNome(Mockito.<String>any());
        doNothing().when(projeto).setPrevisaoTermino(Mockito.<LocalDate>any());
        doNothing().when(projeto).setRisco(Mockito.<Risco>any());
        doNothing().when(projeto).setStatus(Mockito.<Status>any());
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        assertThrows(ProjetoNaoPodeSerExcluidoException.class, () -> projetoService.deletarProjeto(1L));
        verify(projeto).getStatus();
        verify(projeto).setDataInicio(isA(LocalDate.class));
        verify(projeto).setDataRealTermino(isA(LocalDate.class));
        verify(projeto).setDescricao(eq("Descricao"));
        verify(projeto).setId(eq(1L));
        verify(projeto).setIdGerente(eq(1L));
        verify(projeto).setNome(eq("Nome"));
        verify(projeto).setPrevisaoTermino(isA(LocalDate.class));
        verify(projeto).setRisco(eq(Risco.BAIXO));
        verify(projeto).setStatus(eq(Status.EM_ANALISE));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long); then throw EntityNotFoundException")
    void testDeletarProjeto_thenThrowEntityNotFoundException() throws ProjetoNaoPodeSerExcluidoException {
        Optional<Projeto> emptyResult = Optional.empty();
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        assertThrows(EntityNotFoundException.class, () -> projetoService.deletarProjeto(1L));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long); then throw RuntimeException")
    void testDeletarProjeto_thenThrowRuntimeException() throws ProjetoNaoPodeSerExcluidoException {
        Projeto projeto = new Projeto();
        projeto.setDataInicio(LocalDate.of(1970, 1, 1));
        projeto.setDataRealTermino(LocalDate.of(1970, 1, 1));
        projeto.setDescricao("Descricao");
        projeto.setId(1L);
        projeto.setIdGerente(1L);
        projeto.setNome("Nome");
        projeto.setPrevisaoTermino(LocalDate.of(1970, 1, 1));
        projeto.setRisco(Risco.BAIXO);
        projeto.setStatus(Status.EM_ANALISE);
        Optional<Projeto> ofResult = Optional.of(projeto);
        doThrow(new RuntimeException("foo")).when(projetoRepository).delete(Mockito.<Projeto>any());
        when(projetoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        assertThrows(RuntimeException.class, () -> projetoService.deletarProjeto(1L));
        verify(projetoRepository).delete(isA(Projeto.class));
        verify(projetoRepository).findById(eq(1L));
    }

    @Test
    @DisplayName("Test gerarRelatorioResumo(); then return Empty")
    void testGerarRelatorioResumo_thenReturnEmpty() {
        when(projetoRepository.contarMembrosAlocados()).thenReturn(new ArrayList<>());

        List<ProjetoDashboardResponse> actualGerarRelatorioResumoResult = projetoService.gerarRelatorioResumo();

        verify(projetoRepository).contarMembrosAlocados();
        assertTrue(actualGerarRelatorioResumoResult.isEmpty());
    }

    @Test
    @DisplayName("Test gerarRelatorioResumo(); then throw RuntimeException")
    void testGerarRelatorioResumo_thenThrowRuntimeException() {
        when(projetoRepository.contarMembrosAlocados()).thenThrow(new RuntimeException("foo"));

        assertThrows(RuntimeException.class, () -> projetoService.gerarRelatorioResumo());
        verify(projetoRepository).contarMembrosAlocados();
    }
}

