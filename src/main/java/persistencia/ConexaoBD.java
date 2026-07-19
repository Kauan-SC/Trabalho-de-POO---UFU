package persistencia;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    private static final String URL = "jdbc:mysql://" +
            dotenv.get("DB_HOST", "localhost") + ":" +
            dotenv.get("DB_PORT", "3306") + "/" +
            dotenv.get("DB_NAME", "biblioteca_ufu") +
            "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = dotenv.get("DB_USER", "root");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    private static Connection connection;

    private ConexaoBD() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void fecharConexao() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}