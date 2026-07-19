package persistencia;

import emprestimos.Emprestimo;
import livros.Livro;
import pessoas.Pessoa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmprestimoDAO {

    public void inserir(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimos (livro_id, pessoa_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getLivro().getId());
            stmt.setInt(2, emprestimo.getPessoa().getId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, emprestimo.getDataDevolucao() == null ? null : Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.executeUpdate();
        }
    }

    public void registrarDevolucao(int livroId, int pessoaId) throws SQLException {
        String sql = "UPDATE emprestimos SET data_devolucao = CURDATE() " +
                "WHERE livro_id = ? AND pessoa_id = ? AND data_devolucao IS NULL";
        try (PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            stmt.setInt(2, pessoaId);
            stmt.executeUpdate();
        }
    }

    // Recarrega os empréstimos do banco, casando com os objetos Livro/Pessoa já
    // carregados em memória
    public ArrayList<Emprestimo> listarTodos(ArrayList<Livro> livros, ArrayList<Pessoa> pessoas) throws SQLException {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT livro_id, pessoa_id, data_emprestimo, data_devolucao FROM emprestimos";
        try (Statement stmt = ConexaoBD.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int livroId = rs.getInt("livro_id");
                int pessoaId = rs.getInt("pessoa_id");

                Livro livro = livros.stream().filter(l -> l.getId() == livroId).findFirst().orElse(null);
                Pessoa pessoa = pessoas.stream().filter(p -> p.getId() == pessoaId).findFirst().orElse(null);
                if (livro == null || pessoa == null)
                    continue; // registro órfão (livro/pessoa excluído), pula

                LocalDate dataEmprestimo = rs.getDate("data_emprestimo").toLocalDate();
                Date dataDevolucaoSql = rs.getDate("data_devolucao");
                LocalDate dataDevolucao = dataDevolucaoSql == null ? null : dataDevolucaoSql.toLocalDate();

                emprestimos.add(new Emprestimo(livro, pessoa, dataEmprestimo, dataDevolucao));
            }
        }
        return emprestimos;
    }
}