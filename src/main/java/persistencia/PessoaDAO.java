package persistencia;

import pessoas.*;
import java.sql.*;
import java.util.ArrayList;

public class PessoaDAO {

    public void inserir(Pessoa pessoa) throws SQLException {
        String sql = "INSERT INTO pessoas (nome, cpf, tipo, matricula, curso, departamento, disciplina, matricula_funcional) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTipo());

            if (pessoa instanceof Aluno a) {
                stmt.setString(4, a.getMatricula());
                stmt.setString(5, a.getCurso());
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.VARCHAR);
                stmt.setNull(8, Types.VARCHAR);
            } else if (pessoa instanceof Professor p) {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.VARCHAR);
                stmt.setString(6, p.getDepartamento());
                stmt.setString(7, p.getDisciplina());
                stmt.setNull(8, Types.VARCHAR);
            } else if (pessoa instanceof Bibliotecario b) {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.VARCHAR);
                stmt.setString(8, b.getMatriculaFuncional());
            }

            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pessoa.setId(rs.getInt(1));
                }
            }
        }
    }

    public ArrayList<Pessoa> listarTodos() throws SQLException {
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM pessoas";
        try (Statement stmt = ConexaoBD.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");

                Pessoa pessoa = switch (tipo) {
                    case "Aluno" -> new Aluno(id, nome, cpf, rs.getString("matricula"), rs.getString("curso"));
                    case "Professor" ->
                        new Professor(id, nome, cpf, rs.getString("departamento"), rs.getString("disciplina"));
                    case "Bibliotecario" -> new Bibliotecario(id, nome, cpf, rs.getString("matricula_funcional"));
                    default -> throw new IllegalStateException("Tipo desconhecido: " + tipo);
                };
                pessoas.add(pessoa);
            }
        }
        return pessoas;
    }
}