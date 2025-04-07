package br.com.gestaoProjeto.Exception;

public class ProjetoNaoPodeSerExcluidoException extends RuntimeException {
    public ProjetoNaoPodeSerExcluidoException(String mensagem) {
        super(mensagem);
    }
}
