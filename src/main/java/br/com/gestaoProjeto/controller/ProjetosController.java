package br.com.gestaoProjeto.controller;

import br.com.gestaoProjeto.Exception.InvalidStatusTransitionException;
import br.com.gestaoProjeto.model.enums.Evento;
import br.com.gestaoProjeto.model.request.ProjetoRequest;
import br.com.gestaoProjeto.model.response.ProjetoDashboardResponse;
import br.com.gestaoProjeto.model.response.ProjetoResponse;
import br.com.gestaoProjeto.service.ProjetoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
@AllArgsConstructor
public class ProjetosController {

    private final ProjetoService projetoService;

    @PostMapping("/salvar")
    public ResponseEntity<Long> criarProjeto(@RequestBody ProjetoRequest projetoRequest) {
        return ResponseEntity.ok(projetoService.criarProjeto(projetoRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponse> buscarProjeto(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarProjeto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponse> updateStatus(@PathVariable Long id, @RequestBody Evento evento) throws InvalidStatusTransitionException {
        return ResponseEntity.ok(projetoService.atualizarStatus(id, evento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjetoResponse> deletarProjeto(@PathVariable Long id) {
        projetoService.deletarProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio/resumo")
    public ResponseEntity<List<ProjetoDashboardResponse>> relatorioResumo() {
        return ResponseEntity.ok(projetoService.gerarRelatorioResumo());
    }
}
