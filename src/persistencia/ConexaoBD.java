package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password";

    private static Connection connection;

    private ConexaoBD() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
            }
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
    