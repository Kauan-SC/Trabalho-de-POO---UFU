package emprestimos;

import java.time.LocalDate;
import livros.Livro;
import pessoas.Pessoa;

public class Emprestimo {
    private Livro livro;
    private Pessoa pessoa;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo(Livro livro, Pessoa pessoa) {
        this.livro = livro;
        this.pessoa = pessoa;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = null;
    }

    // Construtor usado ao recarregar empréstimos existentes do banco
    public Emprestimo(Livro livro, Pessoa pessoa, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.livro = livro;
        this.pessoa = pessoa;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public Livro getLivro() {
        return livro;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean isAtivo() {
        return dataDevolucao == null;
    }

    public void registrarDevolucao() {
        this.dataDevolucao = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "livro='" + livro.getTitulo() + '\'' +
                ", pessoa='" + pessoa.getNome() + " (" + pessoa.getTipo() + ")'" +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + (dataDevolucao == null ? "em aberto" : dataDevolucao) +
                '}';
    }
}