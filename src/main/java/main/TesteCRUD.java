package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import model.Paciente;
import repository.PacienteRepository;

public class TesteCRUD {

    public static void main(String[] args) {

        // Lê informações digitadas no terminal
        Scanner scanner = new Scanner(System.in);

        // Permite acessar as operações do banco
        PacienteRepository repository =
                new PacienteRepository();

        // Define o formato usado pelo usuário para digitar datas
        DateTimeFormatter formatoData =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int opcao = -1;

        // Mantém o sistema funcionando até escolher 0
        while (opcao != 0) {

            System.out.println(
                    "\n=== SISTEMA CLÍNICA MÉDICA ==="
            );

            System.out.println("1. Cadastrar paciente");
            System.out.println("2. Listar pacientes");
            System.out.println("3. Buscar por nome");
            System.out.println("4. Buscar por CPF");
            System.out.println("5. Alterar paciente");
            System.out.println("6. Excluir paciente");
            System.out.println("0. Sair");

            System.out.print("Escolha: ");

            try {

                opcao =
                        Integer.parseInt(
                                scanner.nextLine()
                        );

                switch (opcao) {

                    case 1:
                        cadastrar(
                                scanner,
                                repository,
                                formatoData
                        );
                        break;

                    case 2:
                        listar(repository);
                        break;

                    case 3:
                        buscarPorNome(
                                scanner,
                                repository
                        );
                        break;

                    case 4:
                        buscarPorCpf(
                                scanner,
                                repository
                        );
                        break;

                    case 5:
                        alterar(
                                scanner,
                                repository,
                                formatoData
                        );
                        break;

                    case 6:
                        excluir(
                                scanner,
                                repository
                        );
                        break;

                    case 0:
                        System.out.println(
                                "Sistema encerrado."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opção inválida."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Digite um número válido."
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Data inválida. Use dd/MM/yyyy."
                );

            } catch (RuntimeException e) {

                System.out.println(
                        "Erro: " + e.getMessage()
                );
            }
        }

        // Fecha o Scanner ao encerrar
        scanner.close();
    }


    // Cadastra um novo paciente
    private static void cadastrar(
            Scanner scanner,
            PacienteRepository repository,
            DateTimeFormatter formatoData) {

        Paciente paciente = new Paciente();

        System.out.print("Nome: ");
        paciente.setNome(scanner.nextLine());

        System.out.print("CPF: ");
        paciente.setCpf(scanner.nextLine());

        System.out.print(
                "Data de nascimento (dd/MM/yyyy): "
        );

        paciente.setDataNascimento(
                LocalDate.parse(
                        scanner.nextLine(),
                        formatoData
                )
        );

        System.out.print("Telefone: ");
        paciente.setTelefone(scanner.nextLine());

        System.out.print("E-mail: ");
        paciente.setEmail(scanner.nextLine());

        System.out.print("Convênio: ");
        paciente.setConvenio(scanner.nextLine());

        // Envia o objeto para o banco
        repository.salvar(paciente);

        System.out.println(
                "Paciente cadastrado. Código: "
                + paciente.getId()
        );
    }


    // Lista todos os pacientes
    private static void listar(
            PacienteRepository repository) {

        List<Paciente> pacientes =
                repository.buscarTodos();

        if (pacientes.isEmpty()) {

            System.out.println(
                    "Nenhum paciente cadastrado."
            );

            return;
        }

        for (Paciente paciente : pacientes) {

            mostrarPaciente(paciente);
        }
    }


    // Pesquisa pacientes pelo nome
    private static void buscarPorNome(
            Scanner scanner,
            PacienteRepository repository) {

        System.out.print("Nome: ");

        String nome =
                scanner.nextLine();

        List<Paciente> pacientes =
                repository.buscarPorNome(nome);

        mostrarLista(pacientes);
    }


    // Pesquisa paciente pelo CPF
    private static void buscarPorCpf(
            Scanner scanner,
            PacienteRepository repository) {

        System.out.print("CPF: ");

        String cpf =
                scanner.nextLine();

        List<Paciente> pacientes =
                repository.buscarPorCpf(cpf);

        mostrarLista(pacientes);
    }


    // Altera os dados de um paciente existente
    private static void alterar(
            Scanner scanner,
            PacienteRepository repository,
            DateTimeFormatter formatoData) {

        Paciente paciente = new Paciente();

        System.out.print("Código do paciente: ");

        paciente.setId(
                Integer.valueOf(
                        scanner.nextLine()
                )
        );

        System.out.print("Novo nome: ");
        paciente.setNome(scanner.nextLine());

        System.out.print("Novo CPF: ");
        paciente.setCpf(scanner.nextLine());

        System.out.print(
                "Nova data de nascimento (dd/MM/yyyy): "
        );

        paciente.setDataNascimento(
                LocalDate.parse(
                        scanner.nextLine(),
                        formatoData
                )
        );

        System.out.print("Novo telefone: ");
        paciente.setTelefone(scanner.nextLine());

        System.out.print("Novo e-mail: ");
        paciente.setEmail(scanner.nextLine());

        System.out.print("Novo convênio: ");
        paciente.setConvenio(scanner.nextLine());

        // Atualiza o registro cujo ID foi informado
        repository.atualizar(paciente);

        System.out.println(
                "Paciente atualizado com sucesso."
        );
    }


    // Exclui o paciente pelo código
    private static void excluir(
            Scanner scanner,
            PacienteRepository repository) {

        System.out.print("Código do paciente: ");

        Integer id =
                Integer.valueOf(
                        scanner.nextLine()
                );

        repository.deletar(id);

        System.out.println(
                "Paciente excluído com sucesso."
        );
    }


    // Exibe todos os pacientes de uma lista
    private static void mostrarLista(
            List<Paciente> pacientes) {

        if (pacientes.isEmpty()) {

            System.out.println(
                    "Nenhum paciente encontrado."
            );

            return;
        }

        for (Paciente paciente : pacientes) {

            mostrarPaciente(paciente);
        }
    }


    // Exibe os dados de um paciente
    private static void mostrarPaciente(
            Paciente paciente) {

        System.out.println(
                "Código: " + paciente.getId()
                + " | Nome: " + paciente.getNome()
                + " | CPF: " + paciente.getCpf()
                + " | Nascimento: "
                + paciente.getDataNascimento()
                + " | Telefone: "
                + paciente.getTelefone()
                + " | E-mail: "
                + paciente.getEmail()
                + " | Convênio: "
                + paciente.getConvenio()
        );
    }
}