package model;

public class Paciente extends Pessoa {

    // declarando um ID que é exclusivo da classe paciente
    private Integer id;
    
    // classe construtora padrão e construtora com parâmetros para inicializar os atributos da classe
    public Paciente() {
        super();
        // o super() chama o construtor da classe pai (Pessoa) para inicializar os atributos herdados
    }

    public Paciente(Integer id, String nome, String cpf, String telefone, String email) {
        super(nome, cpf, telefone, email);
        this.id = id;
    }

    // Getters e Setters do atributo id
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }
}