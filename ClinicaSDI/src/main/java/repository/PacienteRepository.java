package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Paciente;

public class PacienteRepository {
    
    // Método responsável por inserir o paciente no banco de dados
    public void salvar(Paciente paciente) {
        
       // 1. Define a query SQL para inserir os dados do paciente na tabela "pacientes"
        String sql = "INSERT INTO pacientes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        
        // 2. Tenta abrir a conexão e preparar o comando
        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            // 3. Substitui as interrogações '?' pelos dados do objeto paciente
            comando.setString(1, paciente.getNome());
            comando.setString(2, paciente.getCpf());
            comando.setString(3, paciente.getTelefone());
            comando.setString(4, paciente.getEmail());
            
            // 4. Executa o comando no banco de dados
            comando.executeUpdate();
            
            System.out.println("Paciente " + paciente.getNome() + " salvo com sucesso no banco!");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar paciente: " + e.getMessage());
        }
    }
}