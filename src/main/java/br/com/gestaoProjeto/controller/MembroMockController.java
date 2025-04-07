package br.com.gestaoProjeto.controller;

import br.com.gestaoProjeto.model.enums.Cargo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membros")
public class MembroMockController {

    private final Map<Long, MembroDTO> membros = new HashMap<>();
    private AtomicLong proximoId = new AtomicLong(2L);

    public MembroMockController() {
        membros.put(1L, new MembroDTO(1L, "João Gerente", Cargo.GERENTE));
        membros.put(2L, new MembroDTO(2L, "Maria Funcionária", Cargo.FUNCIONARIO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembroDTO> buscarMembro(@PathVariable Long id) {
        MembroDTO membro = membros.get(id);
        return membro != null
                ? ResponseEntity.ok(membro)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<MembroDTO> criarMembro(@RequestBody CriarMembroRequest request) {
        Long novoId = proximoId.getAndIncrement();
        MembroDTO novoMembro = new MembroDTO(novoId, request.nome(), request.cargo());
        membros.put(novoId, novoMembro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMembro);
    }

    @GetMapping
    public ResponseEntity<Map<Long, MembroDTO>> listarTodos() {
        return ResponseEntity.ok(membros);
    }

    public record MembroDTO(Long id, String nome, Cargo cargo) {
    }

    public record CriarMembroRequest(String nome, Cargo cargo) {
    }
}
