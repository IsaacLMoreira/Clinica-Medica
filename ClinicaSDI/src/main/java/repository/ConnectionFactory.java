package repository;

// importando dependencias para o banco de dados
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// classe responsavel para criar a conexao com o banco de dados criado no postgresql
public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/clinica_medica";
    private static final String USER = "postgres";
    private static final String PASSWORD = "gta6emnovembro";

    // metodo para criar a conexão com o banco de dados (removido o throws SQLException daqui)
    public static Connection getConnection() {
        // try catch para tratar a exceção de conexão com o banco de dados
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao obter conexão com o banco de dados: " + e.getMessage());
        }
        // o e.getMessage() retorna a mensagem de erro da exceção lançada
    }
}