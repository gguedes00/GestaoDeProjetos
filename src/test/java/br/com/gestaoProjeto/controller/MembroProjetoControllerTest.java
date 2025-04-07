package br.com.gestaoProjeto.controller;

import static org.mockito.Mockito.when;

import br.com.gestaoProjeto.model.request.MembroProjetoRequest;
import br.com.gestaoProjeto.service.MembroProjetoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MembroProjetoController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MembroProjetoControllerTest {
    @Autowired
    private MembroProjetoController membroProjetoController;

    @MockBean
    private MembroProjetoService membroProjetoService;

    @Test
    @DisplayName("Test associacaoMembroProjeto(Long, MembroProjetoRequest)")
    void testAssociacaoMembroProjeto() throws Exception {
        when(membroProjetoService.associacaoMembroProjeto(Mockito.<Long>any(), Mockito.<MembroProjetoRequest>any()))
                .thenReturn(1L);

        MembroProjetoRequest membroProjetoRequest = new MembroProjetoRequest();
        membroProjetoRequest.setIdsMembros(new HashMap<>());
        String content = (new ObjectMapper()).writeValueAsString(membroProjetoRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/membrosProjeto/{idProjeto}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(membroProjetoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }
}
