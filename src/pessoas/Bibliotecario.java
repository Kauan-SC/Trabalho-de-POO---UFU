package pessoas;

public class Bibliotecario extends Pessoa {
    private String matriculaFuncional;

    public Bibliotecario(int id, String nome, String cpf, String matriculaFuncional) {
        super(id, nome, cpf);
        this.matriculaFuncional = matriculaFuncional;
    }

    public String getMatriculaFuncional() { return matriculaFuncional; }

    @Override
    public String getTipo() {
        return "Bibliotecário";
    }
}