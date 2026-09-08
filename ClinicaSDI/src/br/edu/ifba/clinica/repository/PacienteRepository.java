/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifba.clinica.repository;

import br.edu.ifba.clinica.database.ConnectionFactory;
import br.edu.ifba.clinica.model.Paciente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteRepository {

    // =========================
    // INSERT - Cadastrar paciente
    // =========================
    public void inserir(Paciente paciente) {

        String sql = """
                INSERT INTO pacientes
                (nome, cpf, data_nascimento, telefone, email, convenio)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setDate(3, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEmail());
            stmt.setString(6, paciente.getConvenio());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir paciente: " + e.getMessage(), e);
        }
    }

    // =========================
    // SELECT - Listar pacientes
    // =========================
    public List<Paciente> listar() {

        String sql = """
                SELECT codigo, nome, cpf, data_nascimento,
                       telefone, email, convenio
                FROM pacientes
                ORDER BY codigo
                """;

        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Paciente paciente = new Paciente();

                paciente.setCodigo(rs.getInt("codigo"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDataNascimento(
                        rs.getDate("data_nascimento").toLocalDate()
                );
                paciente.setTelefone(rs.getString("telefone"));
                paciente.setEmail(rs.getString("email"));
                paciente.setConvenio(rs.getString("convenio"));

                pacientes.add(paciente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pacientes: " + e.getMessage(), e);
        }

        return pacientes;
    }

    // =========================
    // SELECT - Buscar por nome
    // =========================
    public List<Paciente> buscarPorNome(String nome) {

        String sql = """
                SELECT codigo, nome, cpf, data_nascimento,
                       telefone, email, convenio
                FROM pacientes
                WHERE LOWER(nome) LIKE LOWER(?)
                ORDER BY nome
                """;

        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Paciente paciente = new Paciente();

                    paciente.setCodigo(rs.getInt("codigo"));
                    paciente.setNome(rs.getString("nome"));
                    paciente.setCpf(rs.getString("cpf"));
                    paciente.setDataNascimento(
                            rs.getDate("data_nascimento").toLocalDate()
                    );
                    paciente.setTelefone(rs.getString("telefone"));
                    paciente.setEmail(rs.getString("email"));
                    paciente.setConvenio(rs.getString("convenio"));

                    pacientes.add(paciente);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por nome: "
                    + e.getMessage(), e);
        }

        return pacientes;
    }

    // =========================
    // SELECT - Buscar por CPF
    // =========================
    public Paciente buscarPorCpf(String cpf) {

        String sql = """
                SELECT codigo, nome, cpf, data_nascimento,
                       telefone, email, convenio
                FROM pacientes
                WHERE cpf = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Paciente paciente = new Paciente();

                    paciente.setCodigo(rs.getInt("codigo"));
                    paciente.setNome(rs.getString("nome"));
                    paciente.setCpf(rs.getString("cpf"));
                    paciente.setDataNascimento(
                            rs.getDate("data_nascimento").toLocalDate()
                    );
                    paciente.setTelefone(rs.getString("telefone"));
                    paciente.setEmail(rs.getString("email"));
                    paciente.setConvenio(rs.getString("convenio"));

                    return paciente;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por CPF: "
                    + e.getMessage(), e);
        }

        return null;
    }

    // =========================
    // UPDATE - Alterar paciente
    // =========================
    public void atualizar(Paciente paciente) {

        String sql = """
                UPDATE pacientes
                SET nome = ?,
                    cpf = ?,
                    data_nascimento = ?,
                    telefone = ?,
                    email = ?,
                    convenio = ?
                WHERE codigo = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setDate(3, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEmail());
            stmt.setString(6, paciente.getConvenio());
            stmt.setInt(7, paciente.getCodigo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente: "
                    + e.getMessage(), e);
        }
    }

    // =========================
    // DELETE - Excluir paciente
    // =========================
    public void excluir(int codigo) {

        String sql = """
                DELETE FROM pacientes
                WHERE codigo = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir paciente: "
                    + e.getMessage(), e);
        }
    }
}