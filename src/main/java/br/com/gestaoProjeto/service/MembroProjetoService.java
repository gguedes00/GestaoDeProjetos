package br.com.gestaoProjeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.gestaoProjeto.Exception.MembroProjetoException;
import br.com.gestaoProjeto.model.MembroProjeto;
import br.com.gestaoProjeto.model.request.MembroProjetoRequest;
import br.com.gestaoProjeto.repository.MembroProjetoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MembroProjetoService {

	private final MembroProjetoRepository membroProjetoRepository;

	public Long associacaoMembroProjeto(Long idProjeto, MembroProjetoRequest membroProjetoRequest) {
		List<Long> listaIds = validarCargo(membroProjetoRequest);
		
		validarProjeto(idProjeto, listaIds);
		
		for (Long idMembro : listaIds) {
			validarStatusProjeto(idMembro);
			
			MembroProjeto membroProjeto = new MembroProjeto();
			membroProjeto.setIdMembro(idMembro);
			membroProjeto.setIdProjeto(idProjeto);

			membroProjetoRepository.save(membroProjeto);

		}

		return idProjeto;
	}

	private List<Long> validarCargo(MembroProjetoRequest membroProjetoRequest) {
		List<Long> idsMembros = new ArrayList<>();

		for (Map.Entry<Long, String> entry : membroProjetoRequest.getIdsMembros().entrySet()) {
			if ("FUNCIONARIO".equals(entry.getValue())) {
				idsMembros.add(entry.getKey());
			} else {
				throw new MembroProjetoException("Um projeto não pode possuir mais de um gerente");

			}
		}

		return idsMembros;
	}

	private void validarProjeto(Long idProjeto, List<Long> idsMembro) {
		Long quantidadeMembros = membroProjetoRepository.countByIdProjeto(idProjeto);
		if (quantidadeMembros >= 10) {
			throw new MembroProjetoException("Projeto já possui a quantidade maxima de membros (10)");
		}

		boolean membroExiste = membroProjetoRepository.existsByIdMembroInAndIdProjeto(idsMembro, idProjeto);
		if (membroExiste) {
			throw new MembroProjetoException("Membro já cadastrado no projeto");
		}

	}
	
	private void validarStatusProjeto(Long idMembro) {
		Long quantidadeProjetos = membroProjetoRepository.contarProjetosAtivosPorMembro(idMembro);
		if (quantidadeProjetos > 3) {
			throw new MembroProjetoException("Membro já possui a quantidade maxima de projetos (3)");
		}
	}

}
