package br.com.gestaoProjeto.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.gestaoProjeto.model.enums.Cargo;
import br.com.gestaoProjeto.model.enums.Evento;
import br.com.gestaoProjeto.model.enums.Risco;
import br.com.gestaoProjeto.model.enums.Status;
import br.com.gestaoProjeto.model.request.MembroRequest;
import br.com.gestaoProjeto.model.request.ProjetoRequest;
import br.com.gestaoProjeto.model.response.ProjetoResponse;
import br.com.gestaoProjeto.service.ProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProjetosController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProjetosControllerTest {
    @MockBean
    private ProjetoService projetoService;

    @Autowired
    private ProjetosController projetosController;

    @Test
    @DisplayName("Test criarProjeto(ProjetoRequest)")
    @Disabled("TODO: Complete this test")
    void testCriarProjeto() throws Exception {

        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/projetos/salvar")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("2.3");
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ProjetoRequest("Nome", dataInicio, previsaoTermino, orcamentoTotal,
                        "Descricao", new MembroRequest(1L, "Nome", Cargo.FUNCIONARIO))));

        MockMvcBuilders.standaloneSetup(projetosController).build().perform(requestBuilder);
    }

    @Test
    @DisplayName("Test criarProjeto(ProjetoRequest); then StatusCode return HttpStatus")
    void testCriarProjeto_thenStatusCodeReturnHttpStatus() {

        ProjetoService projetoService = mock(ProjetoService.class);
        when(projetoService.criarProjeto(Mockito.<ProjetoRequest>any())).thenReturn(1L);
        ProjetosController projetosController = new ProjetosController(projetoService);
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("2.3");

        ResponseEntity<Long> actualCriarProjetoResult = projetosController.criarProjeto(new ProjetoRequest("Nome",
                dataInicio, previsaoTermino, orcamentoTotal, "Descricao", new MembroRequest(1L, "Nome", Cargo.FUNCIONARIO)));

        verify(projetoService).criarProjeto(isA(ProjetoRequest.class));
        HttpStatusCode statusCode = actualCriarProjetoResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        assertEquals(1L, actualCriarProjetoResult.getBody().longValue());
        assertEquals(200, actualCriarProjetoResult.getStatusCodeValue());
        assertEquals(HttpStatus.OK, statusCode);
        assertTrue(actualCriarProjetoResult.hasBody());
        assertTrue(actualCriarProjetoResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Test buscarProjeto(Long)")
    void testBuscarProjeto() throws Exception {
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        LocalDate dataRealTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("2.3");
        when(projetoService.buscarProjeto(Mockito.<Long>any()))
                .thenReturn(new ProjetoResponse(1L, "Nome", dataInicio, previsaoTermino, dataRealTermino, orcamentoTotal,
                        "Descricao", 1L, Status.EM_ANALISE, Risco.BAIXO, new HashSet<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/projetos/{id}", 1L);

        MockMvcBuilders.standaloneSetup(projetosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"nome\":\"Nome\",\"dataInicio\":[1970,1,1],\"previsaoTermino\":[1970,1,1],\"dataRealTermino\":[1970,1"
                                        + ",1],\"orcamentoTotal\":2.3,\"descricao\":\"Descricao\",\"gerenteId\":1,\"status\":\"EM_ANALISE\",\"risco\":\"BAIXO\""
                                        + ",\"membrosIds\":[]}"));
    }

    @Test
    @DisplayName("Test updateStatus(Long, Evento)")
    void testUpdateStatus() throws Exception {
        LocalDate dataInicio = LocalDate.of(1970, 1, 1);
        LocalDate previsaoTermino = LocalDate.of(1970, 1, 1);
        LocalDate dataRealTermino = LocalDate.of(1970, 1, 1);
        BigDecimal orcamentoTotal = new BigDecimal("2.3");
        when(projetoService.atualizarStatus(Mockito.<Long>any(), Mockito.<Evento>any()))
                .thenReturn(new ProjetoResponse(1L, "Nome", dataInicio, previsaoTermino, dataRealTermino, orcamentoTotal,
                        "Descricao", 1L, Status.EM_ANALISE, Risco.BAIXO, new HashSet<>()));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/projetos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString(Evento.CANCELAR));

        MockMvcBuilders.standaloneSetup(projetosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"nome\":\"Nome\",\"dataInicio\":[1970,1,1],\"previsaoTermino\":[1970,1,1],\"dataRealTermino\":[1970,1"
                                        + ",1],\"orcamentoTotal\":2.3,\"descricao\":\"Descricao\",\"gerenteId\":1,\"status\":\"EM_ANALISE\",\"risco\":\"BAIXO\""
                                        + ",\"membrosIds\":[]}"));
    }

    @Test
    @DisplayName("Test deletarProjeto(Long)")
    void testDeletarProjeto() throws Exception {
        doNothing().when(projetoService).deletarProjeto(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/projetos/{id}", 1L);

        MockMvcBuilders.standaloneSetup(projetosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Test relatorioResumo()")
    void testRelatorioResumo() throws Exception {
        when(projetoService.gerarRelatorioResumo()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/projetos/relatorio/resumo");

        MockMvcBuilders.standaloneSetup(projetosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
