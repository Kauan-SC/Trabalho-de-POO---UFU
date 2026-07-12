package excecoes;

public class PessoaNaoEncontradaException extends Exception {
    public PessoaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}