package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/clinica_medica";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "gta6emnovembro";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver do PostgreSQL não encontrado!");
        }

        Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        criarTabelaSeNaoExistir(conn);
        return conn;
    }

    private static void criarTabelaSeNaoExistir(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS paciente (" +
                     "codigo SERIAL PRIMARY KEY, " +
                     "nome VARCHAR(100) NOT NULL, " +
                     "cpf VARCHAR(14), " +
                     "dataNascimento VARCHAR(10), " +
                     "telefone VARCHAR(20), " +
                     "email VARCHAR(100), " +
                     "convenio VARCHAR(10));";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try (Connection conexao = conectar()) {

            System.out.println("Conexão realizada com sucesso!");
            System.out.println("Banco: " + conexao.getCatalog());
            System.out.println("Usuário: " + conexao.getMetaData().getUserName());
            System.out.println("Driver: " + conexao.getMetaData().getDriverName());

        } catch (SQLException e) {

            System.out.println("Erro ao conectar com o banco de dados!");
            e.printStackTrace();
        }
    }
}