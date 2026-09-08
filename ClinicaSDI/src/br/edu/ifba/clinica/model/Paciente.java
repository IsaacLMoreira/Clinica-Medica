/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifba.clinica.model;

/**
 *
 * @author werner-ludwig
 */
import java.time.LocalDate;

public class Paciente {

    private int codigo;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String email;
    private String convenio;

    public Paciente() {
    }
    
    public Paciente(int codigo, String nome, String cpf,LocalDate dataNascimento, String telefone, String email, String convenio) {
    
    this.codigo = codigo;
    this.nome = nome;
    this.cpf = cpf;
    this.dataNascimento = dataNascimento;
    this.telefone = telefone;
    this.email = email;
    this.convenio = convenio;
    }
    
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getConvenio() {
        return convenio;
    }

}