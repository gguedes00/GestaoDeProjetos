package br.com.gestaoProjeto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.gestaoProjeto.Exception.MembroProjetoException;
import br.com.gestaoProjeto.model.request.MembroProjetoRequest;
import br.com.gestaoProjeto.repository.MembroProjetoRepository;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class MembroProjetoServiceTest {

    @Mock
    private MembroProjetoRepository membroProjetoRepository;

    @InjectMocks
    private MembroProjetoService membroProjetoService;

    @Test
    @DisplayName("Test associacaoMembroProjeto(Long, MembroProjetoRequest)")
    void testAssociacaoMembroProjeto() {
        when(membroProjetoRepository.existsByIdMembroInAndIdProjeto(Mockito.<List<Long>>any(), Mockito.<Long>any()))
                .thenReturn(true);
        when(membroProjetoRepository.countByIdProjeto(Mockito.<Long>any())).thenReturn(1L);

        MembroProjetoRequest membroProjetoRequest = new MembroProjetoRequest();
        membroProjetoRequest.setIdsMembros(new HashMap<>());

        assertThrows(MembroProjetoException.class,
                () -> membroProjetoService.associacaoMembroProjeto(1L, membroProjetoRequest));
        verify(membroProjetoRepository).countByIdProjeto(eq(1L));
        verify(membroProjetoRepository).existsByIdMembroInAndIdProjeto(isA(List.class), eq(1L));
    }

    @Test
    @DisplayName("Test associacaoMembroProjeto(Long, MembroProjetoRequest)")
    void testAssociacaoMembroProjeto2() {
        when(membroProjetoRepository.countByIdProjeto(Mockito.<Long>any())).thenReturn(10L);

        MembroProjetoRequest membroProjetoRequest = new MembroProjetoRequest();
        membroProjetoRequest.setIdsMembros(new HashMap<>());

        assertThrows(MembroProjetoException.class,
                () -> membroProjetoService.associacaoMembroProjeto(1L, membroProjetoRequest));
        verify(membroProjetoRepository).countByIdProjeto(eq(1L));
    }

    @Test
    @DisplayName("Test associacaoMembroProjeto(Long, MembroProjetoRequest)")
    void testAssociacaoMembroProjeto3() {

        when(membroProjetoRepository.countByIdProjeto(Mockito.<Long>any()))
                .thenThrow(new MembroProjetoException("An error occurred"));

        MembroProjetoRequest membroProjetoRequest = new MembroProjetoRequest();
        membroProjetoRequest.setIdsMembros(new HashMap<>());

        assertThrows(MembroProjetoException.class,
                () -> membroProjetoService.associacaoMembroProjeto(1L, membroProjetoRequest));
        verify(membroProjetoRepository).countByIdProjeto(eq(1L));
    }

    @Test
    @DisplayName("Test associacaoMembroProjeto(Long, MembroProjetoRequest); given HashMap() ten is 'Membro já cadastrado no projeto'")
    void testAssociacaoMembroProjeto_givenHashMapTenIsMembroJCadastradoNoProjeto() {
        HashMap<Long, String> idsMembros = new HashMap<>();
        idsMembros.put(10L, "Membro já cadastrado no projeto");

        MembroProjetoRequest membroProjetoRequest = new MembroProjetoRequest();
        membroProjetoRequest.setIdsMembros(idsMembros);

        assertThrows(MembroProjetoException.class,
                () -> membroProjetoService.associacaoMembroProjeto(1L, membroProjetoRequest));
    }

    @Test
    @DisplayName("Test associacaoMembroProjeto(Long, MembroProjetoRequest); then return longValue is one")
    void testAssociacaoMembroProjeto_thenReturnLongValueIsOne() {
        when(membroProjetoRepository.existsByIdMembroInAndIdProjeto(Mockito.<List<Long>>any(), Mockito.<Long>any()))
                .thenReturn(false);
        when(membroProjetoRepository.countByIdProjeto(Mockito.<Long>any())).thenReturn(1L);

        MembroProjetoRequest membroProjetoRequest = new MembroProjetoRequest();
        membroProjetoRequest.setIdsMembros(new HashMap<>());

        Long actualAssociacaoMembroProjetoResult = membroProjetoService.associacaoMembroProjeto(1L, membroProjetoRequest);

        verify(membroProjetoRepository).countByIdProjeto(eq(1L));
        verify(membroProjetoRepository).existsByIdMembroInAndIdProjeto(isA(List.class), eq(1L));
        assertEquals(1L, actualAssociacaoMembroProjetoResult.longValue());
    }
}
