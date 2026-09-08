package model;

import java.time.LocalDate;

public abstract class Pessoa {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;

    // construtor padrão
    public Pessoa() {
    }

    // método construtor com parâmetros
    public Pessoa(String nome, String cpf, String telefone,
                  String email, LocalDate dataNascimento) {

        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    // Retorna o nome
    public String getNome() {
        return nome;
    }

    // Altera o nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Retorna o CPF
    public String getCpf() {
        return cpf;
    }

    // Altera o CPF
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Retorna o telefone
    public String getTelefone() {
        return telefone;
    }

    // Altera o telefone
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Retorna o e-mail
    public String getEmail() {
        return email;
    }

    // Altera o e-mail
    public void setEmail(String email) {
        this.email = email;
    }

    // Retorna a data de nascimento
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    // Altera a data de nascimento
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}