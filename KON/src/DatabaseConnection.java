import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/kon";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // Para sql server
    // private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=aula_poo";
    // private static final String USER = "sa";  // Nome de usuário para o SQL Server (exemplo: 'sa' é o padrão)
    // private static final String PASSWORD = "password";  // Sua senha

    // Conexão com o banco de dados
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }

    // Fechar a conexão
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Fechar o PreparedStatement
    public static void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Fechar o ResultSet
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
