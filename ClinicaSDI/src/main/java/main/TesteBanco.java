package main;

import model.Paciente;
import repository.PacienteRepository;

public class TesteBanco {
    
    public static void main(String[] args) {
        
        // 1. Criamos um paciente com dados fictícios.
        // O primeiro campo (ID) vai como 'null' porque o banco (SERIAL) cuida disso.
        Paciente pacienteTeste = new Paciente(null, "João Silva", "111.222.333-44", "(11) 99999-0000", "joao.silva@email.com");
        
        // 2. Instanciamos o nosso repositório
        PacienteRepository repository = new PacienteRepository();
        
        // 3. Mandamos salvar
        System.out.println("Tentando salvar o paciente no banco...");
        repository.salvar(pacienteTeste);
    }
}