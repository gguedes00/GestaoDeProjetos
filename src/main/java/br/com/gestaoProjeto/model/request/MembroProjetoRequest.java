package br.com.gestaoProjeto.model.request;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembroProjetoRequest {
	
    Map<Long , String> idsMembros;
    
}
