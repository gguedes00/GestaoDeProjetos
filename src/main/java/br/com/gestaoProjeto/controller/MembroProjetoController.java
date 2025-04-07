package br.com.gestaoProjeto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gestaoProjeto.model.request.MembroProjetoRequest;
import br.com.gestaoProjeto.service.MembroProjetoService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/membrosProjeto")
@AllArgsConstructor
public class MembroProjetoController {

    private final MembroProjetoService membroProjetoService;

    @PostMapping("/{idProjeto}")
    public ResponseEntity<Long> associacaoMembroProjeto(@PathVariable Long idProjeto, @RequestBody MembroProjetoRequest membroProjetoRequest) {
        return ResponseEntity.ok(membroProjetoService.associacaoMembroProjeto(idProjeto, membroProjetoRequest));

    }
}
