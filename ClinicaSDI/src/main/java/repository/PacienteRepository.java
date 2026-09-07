package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteRepository {
    
    // Método responsável por inserir o paciente no banco de dados
    // C - create (CRUD)
    public void salvar(Paciente paciente) {
        
       // definido a query SQL para inserir os dados do paciente na tabela 'pacientes'
        String sql = "INSERT INTO pacientes (nome, cpf, telefone, email, id) VALUES (?, ?, ?, ?, ?)";
        
        // esturura try catch para lidar com possíveis exceções de SQL
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            // chamando o método set para cada parâmetro da query
            // // substituindo os '?' pelos valores do paciente
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getTelefone());
            comando.setString(4, paciente.getEmail());
            comando.setInt(5, paciente.getId());

            // o método executeUpdate() executa a query de inserção no banco de dados
            comando.executeUpdate();
            
            System.out.println("Paciente " + paciente.getNome() + " salvo com sucesso no banco!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar paciente: " + e.getMessage());
        }
    }

    // metodo para consultar um paciente no banco de dados
    // R - read (CRUD)
    public List<Paciente> buscarTodos() {
        // definido a query SQL para buscar todos os pacientes na tabela 'pacientes'
        // o ORDER BY id' garante que os resultados sejam retornados em ordem crescente de ID
        String sql = "SELECT * FROM pacientes ORDER BY id";

        // criando uma lista para armazenar os pacientes retornados do banco de dados
        List<Paciente> lista = new ArrayList<>();
        
        // estrutura try-with-resources para garantir que a conexão, 
        // o comando e o resultado sejam fechados automaticamente
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            
            // o laço while percorre todos os resultados retornados pela consulta SQL
            while (resultado.next()) {
                Paciente paciente = new Paciente(
                    resultado.getInt("id"),
                    resultado.getString("nome"),
                    resultado.getString("cpf"),
                    resultado.getString("telefone"),
                    resultado.getString("email"),
                    resultado.getString("convenio")
                );
                lista.add(paciente);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pacientes: " + e.getMessage());
        }
    
        return lista;
    }

    // metodo para buscar um paciente pelo nome no banco de dados
    // R - read (CRUD)
    public List<Paciente> buscarPorNome(String nomeBuscado) {
        
        // O ILIKE no PostgreSQL ignora letras maiúsculas e minúsculas. 
        // Os '%' em volta indicam que o nome pode estar no começo, meio ou fim.
        String sql = "SELECT * FROM pacientes WHERE nome ILIKE ?";
        List<Paciente> lista = new ArrayList<>();
        
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setString(1, "%" + nomeBuscado + "%");
            
            // O ResultSet armazena os resultados da consulta SQL
            try (ResultSet resultado = comando.executeQuery()) {
                // A busca é feita e os resultados são percorridos. Para cada resultado, um novo objeto Paciente é criado e adicionado à lista.
                while (resultado.next()) {
                    Paciente paciente = new Paciente(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("cpf"),
                        resultado.getString("telefone"),
                        resultado.getString("email"),
                        resultado.getString("convenio")
                    );
                    lista.add(paciente);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar paciente por nome: " + e.getMessage());
        }
        
        return lista;
    }

    // metodo para atualizar um paciente no banco de dados
    // U - update (CRUD)
    public void atualizar(Paciente paciente) {
        String sql = "UPDATE pacientes SET nome = ?, cpf = ?, telefone = ?, email = ? WHERE id = ?";
        
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getTelefone());
            comando.setString(4, paciente.getEmail());
            comando.setInt(5, paciente.getId()); 
            
            comando.executeUpdate();
            System.out.println("Paciente ID " + paciente.getId() + " atualizado com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar paciente: " + e.getMessage());
        }


    }

    // metodo para deletar um paciente no banco de dados
    // D - delete (CRUD)
    public void deletar(Integer id) {
        String sql = "DELETE FROM pacientes WHERE id = ?";
        
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setInt(1, id);
            
            comando.executeUpdate();
            System.out.println("Paciente ID " + id + " removido com sucesso!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar paciente: " + e.getMessage());
        }
    }
    
}