package model;

import java.time.LocalDate;

public class Paciente extends Pessoa {

    // Código único do paciente
    private Integer id;

    // Informação específica do paciente
    private String convenio;

    // Construtor vazio
    public Paciente() {
        super();
    }

    // Construtor com todos os dados
    public Paciente(Integer id, String nome, String cpf,
                    String telefone, String email,
                    String convenio, LocalDate dataNascimento) {

        // Envia os atributos comuns para Pessoa
        super(nome, cpf, telefone, email, dataNascimento);

        // Inicializa os atributos específicos de Paciente
        this.id = id;
        this.convenio = convenio;
    }

    // Retorna o código do paciente
    public Integer getId() {
        return id;
    }

    // Define o código do paciente
    public void setId(Integer id) {
        this.id = id;
    }

    // Retorna o convênio
    public String getConvenio() {
        return convenio;
    }

    // Altera o convênio
    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }
}