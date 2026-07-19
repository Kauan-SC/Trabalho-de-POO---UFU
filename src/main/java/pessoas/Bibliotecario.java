package pessoas;

// Classe Bibliotecario que herda de Pessoa
public class Bibliotecario extends Pessoa {
    private String matriculaFuncional;

    public Bibliotecario(int id, String nome, String cpf, String matriculaFuncional) {
        super(id, nome, cpf);
        this.matriculaFuncional = matriculaFuncional;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Método getter da classe Bibliotecario
    public String getMatriculaFuncional() {
        return matriculaFuncional;
    }

    @Override
    public String getTipo() {
        return "Bibliotecario";
    }
}