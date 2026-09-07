package model;

public class Paciente extends Pessoa {

    // declarando um ID que é exclusivo da classe paciente
    private Integer id; // atributo adicional para armazenar o ID do paciente
    private String convenio; // atributo adicional para armazenar o convênio do paciente
    
    // classe construtora padrão e construtora com parâmetros para inicializar os atributos da classe
    public Paciente() {
        super();
        // o super() chama o construtor da classe pai (Pessoa) para inicializar os atributos herdados
    }

    public Paciente(Integer id, String nome, String cpf, String telefone, String email, String convenio) {
        super(nome, cpf, telefone, email);
        this.id = id;
        this.convenio = convenio; // AQUI FALTAVA ATRIBUIR O CONVÊNIO
    }

    // Getters e Setters do atributo id
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getConvenio() {
        return convenio;
    }

    // AQUI FALTAVA O SETTER DO CONVENIO
    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }
}