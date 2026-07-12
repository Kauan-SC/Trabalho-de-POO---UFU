package pessoas;

// Classe Professor que herda de Pessoa
public class Professor extends Pessoa {
    private String departamento;
    private String disciplina;

    public Professor(int id, String nome, String cpf, String departamento, String disciplina) {
        super(id, nome, cpf);
        this.departamento = departamento;
        this.disciplina = disciplina;
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Métodos getters da classe Professor
    public String getDepartamento() { return departamento; }
    public String getDisciplina() { return disciplina; }

    @Override
    public String getTipo() {
        return "Professor";
    }
}