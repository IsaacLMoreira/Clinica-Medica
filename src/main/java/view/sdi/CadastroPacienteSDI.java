package view.sdi;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Paciente;
import repository.PacienteRepository;

public class CadastroPacienteSDI extends JFrame {

    // Campos preenchidos pelo usuário
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtDataNascimento;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtConvenio;

    // Botões da tela
    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnFechar;

    // Repository responsável por salvar o paciente no banco
    private final PacienteRepository repository;

    // Formato esperado para a data digitada pelo usuário
    private final DateTimeFormatter formatoData =
            DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);


    public CadastroPacienteSDI() {

        // Cria o repository usado pela tela
        repository = new PacienteRepository();

        configurarJanela();
        inicializarComponentes();
    }


    // Configura as características básicas da janela
    private void configurarJanela() {

        setTitle("Cadastro de Paciente");

        setSize(600, 500);

        // Abre a janela no centro da tela
        setLocationRelativeTo(null);

        setResizable(false);

        /*
         * Fecha somente esta janela.
         * A TelaPrincipal continua aberta.
         */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    // Monta os componentes da interface
    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        criarCabecalho();
        criarFormulario();
        criarBotoes();

        // Pressionar Enter também executa o cadastro
        getRootPane().setDefaultButton(btnCadastrar);
    }


    // Cria o título da tela
    private void criarCabecalho() {

        JPanel painelCabecalho = new JPanel();

        painelCabecalho.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        10,
                        15,
                        10
                )
        );

        JLabel lblTitulo = new JLabel(
                "CADASTRO DE PACIENTE",
                SwingConstants.CENTER
        );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        painelCabecalho.add(lblTitulo);

        add(
                painelCabecalho,
                BorderLayout.NORTH
        );
    }


    // Cria os campos para entrada dos dados
    private void criarFormulario() {

        JPanel painelFormulario =
                new JPanel(new GridBagLayout());

        painelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        40,
                        10,
                        40
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(7, 7, 7, 7);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        /*
         * Linha informando que o código não precisa
         * ser digitado pelo usuário.
         */
        JLabel lblCodigo =
                new JLabel("Código:");

        JLabel lblCodigoAutomatico =
                new JLabel(
                        "Gerado automaticamente"
                );

        adicionarComponente(
                painelFormulario,
                lblCodigo,
                gbc,
                0,
                0
        );

        adicionarComponente(
                painelFormulario,
                lblCodigoAutomatico,
                gbc,
                1,
                0
        );


        // Nome
        JLabel lblNome =
                new JLabel("Nome:");

        txtNome =
                new JTextField(25);

        adicionarComponente(
                painelFormulario,
                lblNome,
                gbc,
                0,
                1
        );

        adicionarComponente(
                painelFormulario,
                txtNome,
                gbc,
                1,
                1
        );


        // CPF
        JLabel lblCpf =
                new JLabel("CPF:");

        txtCpf =
                new JTextField(25);

        adicionarComponente(
                painelFormulario,
                lblCpf,
                gbc,
                0,
                2
        );

        adicionarComponente(
                painelFormulario,
                txtCpf,
                gbc,
                1,
                2
        );


        // Data de nascimento
        JLabel lblDataNascimento =
                new JLabel("Data de nascimento:");

        txtDataNascimento =
                new JTextField(25);

        txtDataNascimento.setToolTipText(
                "Digite no formato dd/MM/yyyy"
        );

        adicionarComponente(
                painelFormulario,
                lblDataNascimento,
                gbc,
                0,
                3
        );

        adicionarComponente(
                painelFormulario,
                txtDataNascimento,
                gbc,
                1,
                3
        );


        // Telefone
        JLabel lblTelefone =
                new JLabel("Telefone:");

        txtTelefone =
                new JTextField(25);

        adicionarComponente(
                painelFormulario,
                lblTelefone,
                gbc,
                0,
                4
        );

        adicionarComponente(
                painelFormulario,
                txtTelefone,
                gbc,
                1,
                4
        );


        // E-mail
        JLabel lblEmail =
                new JLabel("E-mail:");

        txtEmail =
                new JTextField(25);

        adicionarComponente(
                painelFormulario,
                lblEmail,
                gbc,
                0,
                5
        );

        adicionarComponente(
                painelFormulario,
                txtEmail,
                gbc,
                1,
                5
        );


        // Convênio
        JLabel lblConvenio =
                new JLabel("Convênio:");

        txtConvenio =
                new JTextField(25);

        adicionarComponente(
                painelFormulario,
                lblConvenio,
                gbc,
                0,
                6
        );

        adicionarComponente(
                painelFormulario,
                txtConvenio,
                gbc,
                1,
                6
        );


        add(
                painelFormulario,
                BorderLayout.CENTER
        );
    }


    /*
     * Método auxiliar para evitar repetição
     * ao posicionar os componentes no formulário.
     */
    private void adicionarComponente(
            JPanel painel,
            java.awt.Component componente,
            GridBagConstraints gbc,
            int coluna,
            int linha) {

        gbc.gridx = coluna;
        gbc.gridy = linha;

        /*
         * A coluna dos campos recebe mais espaço
         * quando necessário.
         */
        gbc.weightx =
                coluna == 1 ? 1.0 : 0.0;

        painel.add(
                componente,
                gbc
        );
    }


    // Cria os botões da parte inferior
    private void criarBotoes() {

        JPanel painelBotoes =
                new JPanel();

        painelBotoes.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        25,
                        10
                )
        );

        btnCadastrar =
                new JButton("Cadastrar");

        btnLimpar =
                new JButton("Limpar");

        btnFechar =
                new JButton("Fechar");


        // Define as ações dos botões
        btnCadastrar.addActionListener(
                e -> cadastrarPaciente()
        );

        btnLimpar.addActionListener(
                e -> limparCampos()
        );

        btnFechar.addActionListener(
                e -> dispose()
        );


        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);


        add(
                painelBotoes,
                BorderLayout.SOUTH
        );
    }


    // Valida, cria e salva o paciente
    private void cadastrarPaciente() {

        try {

            // Impede cadastro com campos obrigatórios vazios
            validarCampos();

            /*
             * Converte a data digitada
             * para LocalDate.
             */
            LocalDate dataNascimento =
                    LocalDate.parse(
                            txtDataNascimento
                                    .getText()
                                    .trim(),
                            formatoData
                    );


            // Cria o objeto que será enviado ao repository
            Paciente paciente =
                    new Paciente();


            // Copia os valores da tela para o objeto
            paciente.setNome(
                    txtNome.getText().trim()
            );

            paciente.setCpf(
                    txtCpf.getText().trim()
            );

            paciente.setDataNascimento(
                    dataNascimento
            );

            paciente.setTelefone(
                    txtTelefone.getText().trim()
            );

            paciente.setEmail(
                    txtEmail.getText().trim()
            );

            paciente.setConvenio(
                    txtConvenio.getText().trim()
            );


            /*
             * O repository executa o INSERT
             * no PostgreSQL.
             */
            repository.salvar(paciente);


            /*
             * Depois do INSERT, o objeto possui
             * o ID criado pelo PostgreSQL.
             */
            JOptionPane.showMessageDialog(
                    this,
                    "Paciente cadastrado com sucesso!\n"
                            + "Código: "
                            + paciente.getId(),
                    "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // Prepara a tela para outro cadastro
            limparCampos();


        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data de nascimento inválida.\n"
                            + "Utilize o formato dd/MM/yyyy.",
                    "Data inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDataNascimento.requestFocus();


        } catch (IllegalArgumentException e) {

            /*
             * Trata os erros de validação
             * definidos em validarCampos().
             */
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );


        } catch (RuntimeException e) {

            /*
             * Captura problemas vindos do repository,
             * como erro no banco ou CPF duplicado.
             */
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível cadastrar o paciente.\n"
                            + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // Verifica se os dados necessários foram preenchidos
    private void validarCampos() {

        if (txtNome.getText().trim().isEmpty()) {
            txtNome.requestFocus();
            throw new IllegalArgumentException(
                    "Informe o nome do paciente."
            );
        }

        if (txtCpf.getText().trim().isEmpty()) {
            txtCpf.requestFocus();
            throw new IllegalArgumentException(
                    "Informe o CPF do paciente."
            );
        }

        if (txtDataNascimento
                .getText()
                .trim()
                .isEmpty()) {

            txtDataNascimento.requestFocus();

            throw new IllegalArgumentException(
                    "Informe a data de nascimento."
            );
        }

        if (txtTelefone.getText().trim().isEmpty()) {
            txtTelefone.requestFocus();
            throw new IllegalArgumentException(
                    "Informe o telefone."
            );
        }

        if (txtEmail.getText().trim().isEmpty()) {
            txtEmail.requestFocus();
            throw new IllegalArgumentException(
                    "Informe o e-mail."
            );
        }

        if (txtConvenio.getText().trim().isEmpty()) {
            txtConvenio.requestFocus();
            throw new IllegalArgumentException(
                    "Informe o convênio."
            );
        }
    }


    // Limpa os campos do formulário
    private void limparCampos() {

        txtNome.setText("");
        txtCpf.setText("");
        txtDataNascimento.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtConvenio.setText("");

        // Retorna o cursor para o primeiro campo
        txtNome.requestFocus();
    }
}