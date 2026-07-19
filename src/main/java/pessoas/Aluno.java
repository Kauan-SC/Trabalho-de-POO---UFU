package pessoas;
import emprestimos.Emprestimo;
import java.util.ArrayList;

// Classe Aluno que herda de Pessoa
public class Aluno extends Pessoa {
    private String matricula;
    private String curso;
    private ArrayList<Emprestimo> emprestimosAtivos;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Construtor da classe Aluno
    public Aluno(int id, String nome, String cpf, String matricula, String curso) {
        super(id, nome, cpf);
        this.matricula = matricula;
        this.curso = curso;
        this.emprestimosAtivos = new ArrayList<>();
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Métodos getters da classe Aluno
    public String getMatricula() { return matricula; }
    public String getCurso() { return curso; }
    public ArrayList<Emprestimo> getEmprestimosAtivos() { return emprestimosAtivos; }

    @Override
    public String getTipo() {
        return "Aluno";
    }
}