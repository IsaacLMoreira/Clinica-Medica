package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Endereço do banco PostgreSQL
    private static final String URL =
            "jdbc:postgresql://localhost:5432/clinica_medica";

    // Usuário do PostgreSQL
    private static final String USER = "postgres";

    // Senha do PostgreSQL local
    private static final String PASSWORD = "gta6emnovembro";

    // Abre e retorna uma conexão com o banco
    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (SQLException e) {

            // Converte o erro SQL em erro da aplicação
            throw new RuntimeException(
                    "Erro ao conectar ao banco de dados.",
                    e
            );
        }
    }
}