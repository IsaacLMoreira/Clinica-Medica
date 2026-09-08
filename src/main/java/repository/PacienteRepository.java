package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteRepository {

    // =====================================================
    // CREATE - CADASTRAR
    // =====================================================

    public void salvar(Paciente paciente) {

        // O ID não é informado porque o PostgreSQL gera automaticamente
        String sql =
                "INSERT INTO pacientes "
                + "(nome, cpf, data_nascimento, telefone, email, convenio) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            // Preenche os parâmetros da instrução SQL
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setObject(3, paciente.getDataNascimento());
            comando.setString(4, paciente.getTelefone());
            comando.setString(5, paciente.getEmail());
            comando.setString(6, paciente.getConvenio());

            // Executa o INSERT
            comando.executeUpdate();

            // Recupera o ID criado pelo PostgreSQL
            try (ResultSet chaves = comando.getGeneratedKeys()) {

                if (chaves.next()) {
                    paciente.setId(chaves.getInt(1));
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao cadastrar paciente.",
                    e
            );
        }
    }


    // =====================================================
    // READ - LISTAR TODOS
    // =====================================================

    public List<Paciente> buscarTodos() {

        String sql =
                "SELECT id, nome, cpf, data_nascimento, "
                + "telefone, email, convenio "
                + "FROM pacientes ORDER BY id";

        // Armazena os pacientes encontrados
        List<Paciente> lista = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            // Percorre todas as linhas retornadas
            while (resultado.next()) {

                lista.add(
                        criarPaciente(resultado)
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao listar pacientes.",
                    e
            );
        }

        return lista;
    }


    // =====================================================
    // READ - BUSCAR POR NOME
    // =====================================================

    public List<Paciente> buscarPorNome(String nomeBuscado) {

        // ILIKE ignora diferença entre maiúsculas e minúsculas
        String sql =
                "SELECT id, nome, cpf, data_nascimento, "
                + "telefone, email, convenio "
                + "FROM pacientes "
                + "WHERE nome ILIKE ? "
                + "ORDER BY nome";

        List<Paciente> lista = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            // % permite encontrar o texto em qualquer parte do nome
            comando.setString(
                    1,
                    "%" + nomeBuscado + "%"
            );

            try (ResultSet resultado = comando.executeQuery()) {

                while (resultado.next()) {

                    lista.add(
                            criarPaciente(resultado)
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao buscar paciente por nome.",
                    e
            );
        }

        return lista;
    }


    // =====================================================
    // READ - BUSCAR POR CPF
    // =====================================================

    public List<Paciente> buscarPorCpf(String cpfBuscado) {

        // O CPF é pesquisado pelo valor exato
        String sql =
                "SELECT id, nome, cpf, data_nascimento, "
                + "telefone, email, convenio "
                + "FROM pacientes "
                + "WHERE cpf = ?";

        List<Paciente> lista = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            // Substitui o ? pelo CPF informado
            comando.setString(1, cpfBuscado);

            try (ResultSet resultado = comando.executeQuery()) {

                while (resultado.next()) {

                    lista.add(
                            criarPaciente(resultado)
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao buscar paciente por CPF.",
                    e
            );
        }

        return lista;
    }


    // =====================================================
    // UPDATE - ALTERAR
    // =====================================================

    public void atualizar(Paciente paciente) {

        String sql =
                "UPDATE pacientes SET "
                + "nome = ?, "
                + "cpf = ?, "
                + "data_nascimento = ?, "
                + "telefone = ?, "
                + "email = ?, "
                + "convenio = ? "
                + "WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            // Novos dados do paciente
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setObject(3, paciente.getDataNascimento());
            comando.setString(4, paciente.getTelefone());
            comando.setString(5, paciente.getEmail());
            comando.setString(6, paciente.getConvenio());

            // Define qual paciente será alterado
            comando.setInt(7, paciente.getId());

            int linhasAfetadas = comando.executeUpdate();

            // Nenhum ID correspondente foi encontrado
            if (linhasAfetadas == 0) {

                throw new RuntimeException(
                        "Paciente não encontrado."
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao atualizar paciente.",
                    e
            );
        }
    }


    // =====================================================
    // DELETE - EXCLUIR
    // =====================================================

    public void deletar(Integer id) {

        String sql =
                "DELETE FROM pacientes WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            // Define qual paciente será excluído
            comando.setInt(1, id);

            int linhasAfetadas = comando.executeUpdate();

            if (linhasAfetadas == 0) {

                throw new RuntimeException(
                        "Paciente não encontrado."
                );
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Erro ao excluir paciente.",
                    e
            );
        }
    }


    // =====================================================
    // CONVERTER RESULTADO DO BANCO EM PACIENTE
    // =====================================================

    private Paciente criarPaciente(ResultSet resultado)
            throws SQLException {

        Paciente paciente = new Paciente();

        // Copia cada coluna do banco para o objeto Java
        paciente.setId(
                resultado.getInt("id")
        );

        paciente.setNome(
                resultado.getString("nome")
        );

        paciente.setCpf(
                resultado.getString("cpf")
        );

        paciente.setDataNascimento(
                resultado.getObject(
                        "data_nascimento",
                        LocalDate.class
                )
        );

        paciente.setTelefone(
                resultado.getString("telefone")
        );

        paciente.setEmail(
                resultado.getString("email")
        );

        paciente.setConvenio(
                resultado.getString("convenio")
        );

        return paciente;
    }
}