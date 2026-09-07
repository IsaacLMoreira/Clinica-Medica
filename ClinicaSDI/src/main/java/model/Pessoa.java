package model;

public abstract class Pessoa {
    
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    // Construtor padrão e construtor com parâmetros para inicializar os atributos da classe
    public Pessoa() {
    }

    // Construtor com parâmetros para inicializar os atributos da classe
    // O construtor com parâmetros é útil para criar objetos da classe Pessoa com valores iniciais para os atributos
    public Pessoa(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters de todos os atributos
    public String getNome() {
         return nome; 
        }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    public String getCpf() {
         return cpf; 
        }
    public void setCpf(String cpf) {
         this.cpf = cpf; 
        }
    public String getTelefone() {
         return telefone; 
        }
    public void setTelefone(String telefone) {
         this.telefone = telefone; 
        }
    public String getEmail() {
         return email; 
        }
    public void setEmail(String email) {
         this.email = email; 
        }
}