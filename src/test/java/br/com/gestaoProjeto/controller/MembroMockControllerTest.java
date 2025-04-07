package br.com.gestaoProjeto.controller;

import br.com.gestaoProjeto.controller.MembroMockController.CriarMembroRequest;
import br.com.gestaoProjeto.model.enums.Cargo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MembroMockController.class})
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class MembroMockControllerTest {
    @Autowired
    private MembroMockController membroMockController;

    @Test
    @DisplayName("Test buscarMembro(Long); given one; then status isNotFound()")
    void testBuscarMembro_givenOne_thenStatusIsNotFound() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/membros/{id}", 42, "Uri Variables");

        MockMvcBuilders.standaloneSetup(membroMockController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test buscarMembro(Long); when one; then status isOk()")
    void testBuscarMembro_whenOne_thenStatusIsOk() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/membros/{id}", 1L);

        MockMvcBuilders.standaloneSetup(membroMockController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"nome\":\"João Gerente\",\"cargo\":\"GERENTE\"}"));
    }

    @Test
    @DisplayName("Test criarMembro(CriarMembroRequest)")
    void testCriarMembro() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/membros")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new CriarMembroRequest("Nome", Cargo.FUNCIONARIO)));

        MockMvcBuilders.standaloneSetup(membroMockController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":2,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"}"));
    }

    @Test
    @DisplayName("Test listarTodos()")
    void testListarTodos() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/membros");

        MockMvcBuilders.standaloneSetup(membroMockController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"1\":{\"id\":1,\"nome\":\"João Gerente\",\"cargo\":\"GERENTE\"},\"2\":{\"id\":2,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"3\":{\"id\":3,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"4\":{\"id\":4,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"}"
                                        + ",\"5\":{\"id\":5,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"6\":{\"id\":6,\"nome\":\"42\",\"cargo\":\"FUNCIONARIO\"},\"7\""
                                        + ":{\"id\":7,\"nome\":\"\",\"cargo\":\"FUNCIONARIO\"},\"8\":{\"id\":8,\"nome\":\"Nome\",\"cargo\":\"GERENTE\"},\"9\":{\"id\":9,"
                                        + "\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"10\":{\"id\":10,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"11\":{\"id\":11"
                                        + ",\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"12\":{\"id\":12,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"13\":{\"id\""
                                        + ":13,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"14\":{\"id\":14,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"15\":{\"id"
                                        + "\":15,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"16\":{\"id\":16,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"17\":{"
                                        + "\"id\":17,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"18\":{\"id\":18,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"19\""
                                        + ":{\"id\":19,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"20\":{\"id\":20,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"21"
                                        + "\":{\"id\":21,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"22\":{\"id\":22,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},"
                                        + "\"23\":{\"id\":23,\"nome\":\"42\",\"cargo\":\"FUNCIONARIO\"},\"24\":{\"id\":24,\"nome\":\"\",\"cargo\":\"FUNCIONARIO\"},\"25\""
                                        + ":{\"id\":25,\"nome\":\"Nome\",\"cargo\":\"GERENTE\"},\"26\":{\"id\":26,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"27\":{"
                                        + "\"id\":27,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"28\":{\"id\":28,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"29\""
                                        + ":{\"id\":29,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"30\":{\"id\":30,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"31"
                                        + "\":{\"id\":31,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"32\":{\"id\":32,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},"
                                        + "\"33\":{\"id\":33,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"34\":{\"id\":34,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\""
                                        + "},\"35\":{\"id\":35,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"36\":{\"id\":36,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"37\":{\"id\":37,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"38\":{\"id\":38,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"39\":{\"id\":39,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"40\":{\"id\":40,\"nome\":\"42\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"41\":{\"id\":41,\"nome\":\"\",\"cargo\":\"FUNCIONARIO\"},\"42\":{\"id\":42,\"nome\":\"Nome\",\"cargo\":\"GERENTE\"},\"43"
                                        + "\":{\"id\":43,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"44\":{\"id\":44,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},"
                                        + "\"45\":{\"id\":45,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"46\":{\"id\":46,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\""
                                        + "},\"47\":{\"id\":47,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"48\":{\"id\":48,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"49\":{\"id\":49,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"50\":{\"id\":50,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"51\":{\"id\":51,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"52\":{\"id\":52,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"53\":{\"id\":53,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"54\":{\"id\":54,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"55\":{\"id\":55,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"56\":{\"id\":56,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"57\":{\"id\":57,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"58\":{\"id\":58,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"59\":{\"id\":59,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"60\":{\"id\":60,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"61\":{\"id\":61,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"62\":{\"id\":62,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"63\":{\"id\":63,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"64\":{\"id\":64,\"nome\":\"42\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"65\":{\"id\":65,\"nome\":\"\",\"cargo\":\"FUNCIONARIO\"},\"66\":{\"id\":66,\"nome\":\"Nome\",\"cargo\":\"GERENTE\"},\"67"
                                        + "\":{\"id\":67,\"nome\":\"42\",\"cargo\":\"GERENTE\"},\"68\":{\"id\":68,\"nome\":\"\",\"cargo\":\"GERENTE\"},\"69\":{\"id\":69,"
                                        + "\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"70\":{\"id\":70,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"71\":{\"id\":71"
                                        + ",\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"72\":{\"id\":72,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"73\":{\"id\""
                                        + ":73,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"74\":{\"id\":74,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"75\":{\"id"
                                        + "\":75,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"76\":{\"id\":76,\"nome\":\"42\",\"cargo\":\"FUNCIONARIO\"},\"77\":{\"id"
                                        + "\":77,\"nome\":\"\",\"cargo\":\"FUNCIONARIO\"},\"78\":{\"id\":78,\"nome\":\"Nome\",\"cargo\":\"GERENTE\"},\"79\":{\"id\":79,"
                                        + "\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"80\":{\"id\":80,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"81\":{\"id\":81"
                                        + ",\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"82\":{\"id\":82,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"83\":{\"id\""
                                        + ":83,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"84\":{\"id\":84,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"85\":{\"id"
                                        + "\":85,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"86\":{\"id\":86,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"87\":{"
                                        + "\"id\":87,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"88\":{\"id\":88,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"89\""
                                        + ":{\"id\":89,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"90\":{\"id\":90,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"91"
                                        + "\":{\"id\":91,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"92\":{\"id\":92,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},"
                                        + "\"93\":{\"id\":93,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"94\":{\"id\":94,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\""
                                        + "},\"95\":{\"id\":95,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"96\":{\"id\":96,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"97\":{\"id\":97,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"98\":{\"id\":98,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"99\":{\"id\":99,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"100\":{\"id\":100,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"101\":{\"id\":101,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"102\":{\"id\":102,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"103\":{\"id\":103,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"104\":{\"id\":104,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"105\":{\"id\":105,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"106\":{\"id\":106,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"107\":{\"id\":107,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"108\":{\"id\":108,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"109\":{\"id\":109,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"110\":{\"id\":110,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"111\":{\"id\":111,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"112\":{\"id\":112,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"113\":{\"id\":113,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"114\":{\"id\":114,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"115\":{\"id\":115,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"116\":{\"id\":116,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"117\":{\"id\":117,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"118\":{\"id\":118,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"119\":{\"id\":119,\"nome\":\"42\",\"cargo\":\"FUNCIONARIO\"},\"120\":{\"id\":120,\"nome\":\"\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"121\":{\"id\":121,\"nome\":\"Nome\",\"cargo\":\"GERENTE\"},\"122\":{\"id\":122,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"},\"123\":{\"id\":123,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO\"},\"124\":{\"id\":124,\"nome\":\"Nome\",\"cargo\":\"FUNCIONARIO"
                                        + "\"}}"));
    }
}
