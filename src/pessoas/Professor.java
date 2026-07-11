package pessoas;

public class Professor extends Pessoa {
    private String departamento;
    private String disciplina;

    public Professor(int id, String nome, String cpf, String departamento, String disciplina) {
        super(id, nome, cpf);
        this.departamento = departamento;
        this.disciplina = disciplina;
    }

    public String getDepartamento() { return departamento; }
    public String getDisciplina() { return disciplina; }

    @Override
    public String getTipo() {
        return "Professor";
    }
}