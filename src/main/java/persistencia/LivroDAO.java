package persistencia;

import livros.Livro;
import java.sql.*;
import java.util.ArrayList;

public class LivroDAO {

    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO livros (titulo, autor, ano, disponivel) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAno());
            stmt.setBoolean(4, livro.isDisponibilidade());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next())
                    livro.setId(rs.getInt(1));
            }
        }
    }

    public ArrayList<Livro> listarTodos() throws SQLException {
        ArrayList<Livro> livros = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, ano, disponivel FROM livros";
        try (Statement stmt = ConexaoBD.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro(rs.getInt("id"), rs.getString("titulo"),
                        rs.getString("autor"), rs.getInt("ano"));
                livro.setDisponibilidade(rs.getBoolean("disponivel"));
                livros.add(livro);
            }
        }
        return livros;
    }

    public void atualizarDisponibilidade(int id, boolean disponivel) throws SQLException {
        String sql = "UPDATE livros SET disponivel = ? WHERE id = ?";
        try (PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM livros WHERE id = ?";
        try (PreparedStatement stmt = ConexaoBD.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}