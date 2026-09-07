package main;

import java.util.List;
import java.util.Scanner;
import model.Paciente;
import repository.PacienteRepository;

public class Main {

    public static void main(String[] args) {
        // Criando um objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);
        // Instanciando o repositório de pacientes para realizar operações no banco de dados
        PacienteRepository repository = new PacienteRepository();
        int opcao = -1;

        // Loop principal do sistema, que continua até o usuário escolher sair (opção 0)
        while (opcao != 0) {
            System.out.println("\n=== SISTEMA CLÍNICA MÉDICA ===");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Listar Todos os Pacientes");
            System.out.println("3. Buscar Paciente por Nome");
            System.out.println("4. Editar Paciente");
            System.out.println("5. Excluir Paciente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            // Lendo a opção. O parse do Integer evita o bug de pular linha do Scanner
            opcao = Integer.parseInt(scanner.nextLine());

            // Estrutura switch para tratar cada opção escolhida pelo usuário
            switch (opcao) {
                case 1:
                    System.out.println("\n-- CADASTRAR PACIENTE --");
                    System.out.print("ID: ");
                    Integer idCadastro = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nome: ");
                    String nomeCadastro = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpfCadastro = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telCadastro = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String emailCadastro = scanner.nextLine();
                    System.out.print("Convênio: ");
                    String convenioCadastro = scanner.nextLine();

                    Paciente novoPaciente = new Paciente(idCadastro, nomeCadastro, cpfCadastro, telCadastro, emailCadastro, convenioCadastro);
                    repository.salvar(novoPaciente);
                    break;

                case 2:
                    System.out.println("\n-- LISTA DE PACIENTES --");
                    List<Paciente> todosPacientes = repository.buscarTodos();
                    if (todosPacientes.isEmpty()) {
                        System.out.println("Nenhum paciente cadastrado.");
                    } else {
                        for (Paciente p : todosPacientes) {
                            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() + " | CPF: " + p.getCpf() + " | Convênio: " + p.getConvenio());
                        }
                    }
                    break;

                case 3:
                    System.out.println("\n-- BUSCAR PACIENTE --");
                    System.out.print("Digite credenciais: ");
                    String nomeBusca = scanner.nextLine();
                    List<Paciente> pacientesEncontrados = repository.buscarPorNome(nomeBusca);
                    
                    if (pacientesEncontrados.isEmpty()) {
                        System.out.println("Nenhum paciente encontrado com essas credenciais.");
                    } else {
                        for (Paciente p : pacientesEncontrados) {
                            System.out.println("ID: " + p.getId() + " | Nome: " + p.getNome() + " | CPF: " + p.getCpf() + " | Telefone: " + p.getTelefone() + " | E-mail: " + p.getEmail() + " | Convênio: " + p.getConvenio());
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n-- EDITAR PACIENTE --");
                    System.out.print("Digite o ID do paciente que deseja editar: ");
                    Integer idEditar = Integer.parseInt(scanner.nextLine());
                    
                    System.out.print("Novo Nome: ");
                    String nomeEdit = scanner.nextLine();
                    System.out.print("Novo CPF: ");
                    String cpfEdit = scanner.nextLine();
                    System.out.print("Novo Telefone: ");
                    String telEdit = scanner.nextLine();
                    System.out.print("Novo E-mail: ");
                    String emailEdit = scanner.nextLine();
                    System.out.print("Novo Convênio: ");
                    String convenioEdit = scanner.nextLine();

                    Paciente pacienteAtualizado = new Paciente(idEditar, nomeEdit, cpfEdit, telEdit, emailEdit, convenioEdit);
                    repository.atualizar(pacienteAtualizado);
                    break;

                case 5:
                    System.out.println("\n-- EXCLUIR PACIENTE --");
                    System.out.print("Digite o ID do paciente que deseja excluir: ");
                    Integer idExcluir = Integer.parseInt(scanner.nextLine());
                    repository.deletar(idExcluir);
                    break;

                case 0:
                    System.out.println("\nFechando");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
                    break;
            }
        }
        
        scanner.close();
    }
}