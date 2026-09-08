package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Paciente;

public class PacienteRepositorio {

    public void salvar(Paciente p) {
        String sql = "INSERT INTO paciente (nome, cpf, dataNascimento, telefone, email, convenio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getDataNascimento());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getEmail());
            stmt.setString(6, p.getConvenio() != null ? p.getConvenio().toUpperCase() : "");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Paciente> listarTodos() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT * FROM paciente ORDER BY codigo ASC";
        
        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Paciente p = new Paciente();
                p.setCodigo(rs.getInt("codigo"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                
                // Tratamento para incompatibilidade de caixa de texto no PostgreSQL
                try {
                    p.setDataNascimento(rs.getString("datanascimento"));
                } catch (SQLException e) {
                    p.setDataNascimento(rs.getString("dataNascimento"));
                }
                
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setConvenio(rs.getString("convenio"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Paciente buscarPorCodigo(int codigo) {
        String sql = "SELECT * FROM paciente WHERE codigo = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Paciente p = new Paciente();
                    p.setCodigo(rs.getInt("codigo"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    
                    try {
                        p.setDataNascimento(rs.getString("datanascimento"));
                    } catch (SQLException e) {
                        p.setDataNascimento(rs.getString("dataNascimento"));
                    }
                    
                    p.setTelefone(rs.getString("telefone"));
                    p.setEmail(rs.getString("email"));
                    p.setConvenio(rs.getString("convenio"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean alterar(Paciente p) {
        String sql = "UPDATE paciente SET nome=?, cpf=?, dataNascimento=?, telefone=?, email=?, convenio=? WHERE codigo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setString(3, p.getDataNascimento());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getEmail());
            stmt.setString(6, p.getConvenio() != null ? p.getConvenio().toUpperCase() : "");
            stmt.setInt(7, p.getCodigo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluirPorCodigo(int codigo) {
        String sql = "DELETE FROM paciente WHERE codigo = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codigo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}