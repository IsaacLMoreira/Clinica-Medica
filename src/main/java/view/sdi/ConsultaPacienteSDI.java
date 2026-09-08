package view.sdi;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableModel;

import model.Paciente;
import repository.PacienteRepository;

public class ConsultaPacienteSDI extends JFrame {

    // Repository utilizado para acessar os pacientes
    private final PacienteRepository repository;

    /*
     * Guarda os pacientes que estão atualmente
     * sendo exibidos na tabela.
     */
    private List<Paciente> pacientesExibidos;

    // Componentes da pesquisa
    private JComboBox<String> cbTipoBusca;
    private JTextField txtBusca;

    private JButton btnBuscar;
    private JButton btnListarTodos;

    // Tabela
    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;

    // Campos usados para alterar o paciente selecionado
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtDataNascimento;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtConvenio;

    // Botões das operações
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnFechar;

    /*
     * Formato utilizado para exibir e receber
     * a data na interface.
     */
    private final DateTimeFormatter formatoData =
            DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);


    public ConsultaPacienteSDI() {

        repository = new PacienteRepository();

        pacientesExibidos = new ArrayList<>();

        configurarJanela();
        inicializarComponentes();

        /*
         * Ao abrir a tela, todos os pacientes
         * já são carregados na JTable.
         */
        carregarTodos();
    }


    // Configura a janela
    private void configurarJanela() {

        setTitle("Consulta de Pacientes");

        setSize(1100, 700);

        // Centraliza a janela
        setLocationRelativeTo(null);

        setResizable(false);

        /*
         * Fecha somente esta janela.
         * A TelaPrincipal continua aberta.
         */
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    // Monta a interface
    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        criarCabecalho();
        criarAreaCentral();
        criarBotoes();
    }


    // Cria o título
    private void criarCabecalho() {

        JPanel painelCabecalho = new JPanel();

        painelCabecalho.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        10,
                        15,
                        10
                )
        );

        JLabel lblTitulo =
                new JLabel(
                        "CONSULTA DE PACIENTES",
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


    /*
     * Cria pesquisa, tabela e formulário
     * de alteração.
     */
    private void criarAreaCentral() {

        JPanel painelCentral =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        painelCentral.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        20,
                        10,
                        20
                )
        );

        painelCentral.add(
                criarPainelBusca(),
                BorderLayout.NORTH
        );

        painelCentral.add(
                criarTabela(),
                BorderLayout.CENTER
        );

        painelCentral.add(
                criarFormularioAlteracao(),
                BorderLayout.SOUTH
        );

        add(
                painelCentral,
                BorderLayout.CENTER
        );
    }


    // Cria os componentes utilizados na pesquisa
    private JPanel criarPainelBusca() {

        JPanel painelBusca = new JPanel();

        JLabel lblBuscarPor =
                new JLabel("Buscar por:");

        cbTipoBusca =
                new JComboBox<>(
                        new String[]{
                            "Nome",
                            "CPF"
                        }
                );

        txtBusca =
                new JTextField(25);

        btnBuscar =
                new JButton("Buscar");

        btnListarTodos =
                new JButton("Listar Todos");


        // Executa a pesquisa
        btnBuscar.addActionListener(
                e -> buscar()
        );

        // Recarrega todos os registros
        btnListarTodos.addActionListener(
                e -> carregarTodos()
        );

        // Pressionar Enter no campo também realiza a busca
        txtBusca.addActionListener(
                e -> buscar()
        );


        painelBusca.add(lblBuscarPor);
        painelBusca.add(cbTipoBusca);
        painelBusca.add(txtBusca);
        painelBusca.add(btnBuscar);
        painelBusca.add(btnListarTodos);

        return painelBusca;
    }


    // Cria a JTable
    private JScrollPane criarTabela() {

        String[] colunas = {
            "Código",
            "Nome",
            "CPF",
            "Nascimento",
            "Telefone",
            "E-mail",
            "Convênio"
        };


        /*
         * O modelo define os dados apresentados
         * na JTable.
         */
        modeloTabela =
                new DefaultTableModel(
                        colunas,
                        0
                ) {

                    /*
                     * Impede edição diretamente
                     * dentro das células.
                     */
                    @Override
                    public boolean isCellEditable(
                            int linha,
                            int coluna) {

                        return false;
                    }
                };


        tabelaPacientes =
                new JTable(modeloTabela);


        /*
         * Permite selecionar apenas
         * um paciente por vez.
         */
        tabelaPacientes.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        /*
         * Quando uma linha for selecionada,
         * seus dados são enviados aos campos
         * de alteração.
         */
        tabelaPacientes
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        carregarPacienteSelecionado();
                    }
                });


        return new JScrollPane(
                tabelaPacientes
        );
    }


    /*
     * Cria os campos utilizados para alterar
     * um paciente selecionado.
     */
    private JPanel criarFormularioAlteracao() {

        JPanel painel =
                new JPanel(
                        new BorderLayout()
                );

        painel.setBorder(
                BorderFactory.createTitledBorder(
                        "Dados do paciente selecionado"
                )
        );


        JPanel formulario =
                new JPanel(
                        new GridBagLayout()
                );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(
                        5,
                        8,
                        5,
                        8
                );

        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        // Cria os campos
        txtNome =
                new JTextField(20);

        txtCpf =
                new JTextField(20);

        txtDataNascimento =
                new JTextField(20);

        txtDataNascimento.setToolTipText(
                "Formato: dd/MM/yyyy"
        );

        txtTelefone =
                new JTextField(20);

        txtEmail =
                new JTextField(20);

        txtConvenio =
                new JTextField(20);


        // Primeira linha
        adicionarCampo(
                formulario,
                new JLabel("Nome:"),
                0,
                0,
                gbc
        );

        adicionarCampo(
                formulario,
                txtNome,
                1,
                0,
                gbc
        );

        adicionarCampo(
                formulario,
                new JLabel("CPF:"),
                2,
                0,
                gbc
        );

        adicionarCampo(
                formulario,
                txtCpf,
                3,
                0,
                gbc
        );


        // Segunda linha
        adicionarCampo(
                formulario,
                new JLabel("Nascimento:"),
                0,
                1,
                gbc
        );

        adicionarCampo(
                formulario,
                txtDataNascimento,
                1,
                1,
                gbc
        );

        adicionarCampo(
                formulario,
                new JLabel("Telefone:"),
                2,
                1,
                gbc
        );

        adicionarCampo(
                formulario,
                txtTelefone,
                3,
                1,
                gbc
        );


        // Terceira linha
        adicionarCampo(
                formulario,
                new JLabel("E-mail:"),
                0,
                2,
                gbc
        );

        adicionarCampo(
                formulario,
                txtEmail,
                1,
                2,
                gbc
        );

        adicionarCampo(
                formulario,
                new JLabel("Convênio:"),
                2,
                2,
                gbc
        );

        adicionarCampo(
                formulario,
                txtConvenio,
                3,
                2,
                gbc
        );


        painel.add(
                formulario,
                BorderLayout.CENTER
        );

        return painel;
    }


    // Método auxiliar para posicionar os campos
    private void adicionarCampo(
            JPanel painel,
            java.awt.Component componente,
            int coluna,
            int linha,
            GridBagConstraints gbc) {

        gbc.gridx = coluna;
        gbc.gridy = linha;

        /*
         * Os campos de texto recebem
         * mais espaço horizontal.
         */
        gbc.weightx =
                (coluna == 1 || coluna == 3)
                        ? 1.0
                        : 0.0;

        painel.add(
                componente,
                gbc
        );
    }


    // Cria os botões inferiores
    private void criarBotoes() {

        JPanel painelBotoes =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                10,
                                0
                        )
                );

        painelBotoes.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        300,
                        20,
                        300
                )
        );


        btnAlterar =
                new JButton("Alterar");

        btnExcluir =
                new JButton("Excluir");

        btnFechar =
                new JButton("Fechar");


        btnAlterar.addActionListener(
                e -> alterarPaciente()
        );

        btnExcluir.addActionListener(
                e -> excluirPaciente()
        );

        btnFechar.addActionListener(
                e -> dispose()
        );


        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnFechar);


        add(
                painelBotoes,
                BorderLayout.SOUTH
        );
    }


    // =====================================================
    // LISTAR TODOS
    // =====================================================

    private void carregarTodos() {

        try {

            pacientesExibidos =
                    repository.buscarTodos();

            preencherTabela(
                    pacientesExibidos
            );

            // Limpa pesquisa e seleção
            txtBusca.setText("");

            limparCampos();

        } catch (RuntimeException e) {

            mostrarErro(
                    "Não foi possível listar os pacientes.",
                    e
            );
        }
    }


    // =====================================================
    // BUSCAR
    // =====================================================

    private void buscar() {

        String textoBusca =
                txtBusca
                        .getText()
                        .trim();


        /*
         * Se não houver texto,
         * lista todos os pacientes.
         */
        if (textoBusca.isEmpty()) {

            carregarTodos();

            return;
        }


        try {

            String tipoBusca =
                    cbTipoBusca
                            .getSelectedItem()
                            .toString();


            if (tipoBusca.equals("Nome")) {

                pacientesExibidos =
                        repository.buscarPorNome(
                                textoBusca
                        );

            } else {

                pacientesExibidos =
                        repository.buscarPorCpf(
                                textoBusca
                        );
            }


            preencherTabela(
                    pacientesExibidos
            );


            if (pacientesExibidos.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Nenhum paciente encontrado.",
                        "Consulta",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }


            limparCampos();


        } catch (RuntimeException e) {

            mostrarErro(
                    "Não foi possível realizar a consulta.",
                    e
            );
        }
    }


    // =====================================================
    // PREENCHER JTABLE
    // =====================================================

    private void preencherTabela(
            List<Paciente> pacientes) {

        /*
         * Remove as linhas que estavam
         * sendo exibidas anteriormente.
         */
        modeloTabela.setRowCount(0);


        // Adiciona cada paciente como uma linha
        for (Paciente paciente : pacientes) {

            modeloTabela.addRow(
                    new Object[]{
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getCpf(),

                        /*
                         * A data é apresentada
                         * como dd/MM/yyyy.
                         */
                        paciente
                                .getDataNascimento()
                                .format(formatoData),

                        paciente.getTelefone(),
                        paciente.getEmail(),
                        paciente.getConvenio()
                    }
            );
        }
    }


    // =====================================================
    // SELECIONAR PACIENTE
    // =====================================================

    private void carregarPacienteSelecionado() {

        int linha =
                tabelaPacientes
                        .getSelectedRow();


        // Nenhuma linha selecionada
        if (linha == -1) {
            return;
        }


        /*
         * A posição da linha corresponde
         * à posição do paciente na lista.
         */
        Paciente paciente =
                pacientesExibidos.get(linha);


        // Copia os dados para os campos
        txtNome.setText(
                paciente.getNome()
        );

        txtCpf.setText(
                paciente.getCpf()
        );

        txtDataNascimento.setText(
                paciente
                        .getDataNascimento()
                        .format(formatoData)
        );

        txtTelefone.setText(
                paciente.getTelefone()
        );

        txtEmail.setText(
                paciente.getEmail()
        );

        txtConvenio.setText(
                paciente.getConvenio()
        );
    }


    // =====================================================
    // ALTERAR
    // =====================================================

    private void alterarPaciente() {

        int linha =
                tabelaPacientes
                        .getSelectedRow();


        if (linha == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um paciente na tabela.",
                    "Alteração",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        try {

            validarCampos();


            // Obtém o paciente selecionado
            Paciente paciente =
                    pacientesExibidos.get(linha);


            // Converte novamente a data digitada
            LocalDate dataNascimento =
                    LocalDate.parse(
                            txtDataNascimento
                                    .getText()
                                    .trim(),
                            formatoData
                    );


            /*
             * Atualiza o objeto com
             * os valores dos campos.
             */
            paciente.setNome(
                    txtNome
                            .getText()
                            .trim()
            );

            paciente.setCpf(
                    txtCpf
                            .getText()
                            .trim()
            );

            paciente.setDataNascimento(
                    dataNascimento
            );

            paciente.setTelefone(
                    txtTelefone
                            .getText()
                            .trim()
            );

            paciente.setEmail(
                    txtEmail
                            .getText()
                            .trim()
            );

            paciente.setConvenio(
                    txtConvenio
                            .getText()
                            .trim()
            );


            // Executa o UPDATE
            repository.atualizar(
                    paciente
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Paciente alterado com sucesso!",
                    "Alteração",
                    JOptionPane.INFORMATION_MESSAGE
            );


            /*
             * Atualiza a JTable para mostrar
             * os novos valores.
             */
            carregarTodos();


        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data inválida.\n"
                            + "Utilize o formato dd/MM/yyyy.",
                    "Data inválida",
                    JOptionPane.WARNING_MESSAGE
            );

            txtDataNascimento.requestFocus();


        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
            );


        } catch (RuntimeException e) {

            mostrarErro(
                    "Não foi possível alterar o paciente.",
                    e
            );
        }
    }


    // =====================================================
    // EXCLUIR
    // =====================================================

    private void excluirPaciente() {

        int linha =
                tabelaPacientes
                        .getSelectedRow();


        if (linha == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um paciente na tabela.",
                    "Exclusão",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        Paciente paciente =
                pacientesExibidos.get(linha);


        int resposta =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente excluir o paciente:\n"
                                + paciente.getNome()
                                + "?",
                        "Confirmar exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        /*
         * Se o usuário escolher Não,
         * nenhuma operação é realizada.
         */
        if (resposta !=
                JOptionPane.YES_OPTION) {

            return;
        }


        try {

            // Exclui pelo ID do paciente
            repository.deletar(
                    paciente.getId()
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Paciente excluído com sucesso!",
                    "Exclusão",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // Atualiza a JTable
            carregarTodos();


        } catch (RuntimeException e) {

            mostrarErro(
                    "Não foi possível excluir o paciente.",
                    e
            );
        }
    }


    // =====================================================
    // VALIDAÇÃO
    // =====================================================

    private void validarCampos() {

        if (txtNome
                .getText()
                .trim()
                .isEmpty()) {

            txtNome.requestFocus();

            throw new IllegalArgumentException(
                    "Informe o nome do paciente."
            );
        }


        if (txtCpf
                .getText()
                .trim()
                .isEmpty()) {

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


        if (txtTelefone
                .getText()
                .trim()
                .isEmpty()) {

            txtTelefone.requestFocus();

            throw new IllegalArgumentException(
                    "Informe o telefone."
            );
        }


        if (txtEmail
                .getText()
                .trim()
                .isEmpty()) {

            txtEmail.requestFocus();

            throw new IllegalArgumentException(
                    "Informe o e-mail."
            );
        }


        if (txtConvenio
                .getText()
                .trim()
                .isEmpty()) {

            txtConvenio.requestFocus();

            throw new IllegalArgumentException(
                    "Informe o convênio."
            );
        }
    }


    // =====================================================
    // LIMPAR CAMPOS
    // =====================================================

    private void limparCampos() {

        tabelaPacientes.clearSelection();

        txtNome.setText("");
        txtCpf.setText("");
        txtDataNascimento.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtConvenio.setText("");
    }


    // =====================================================
    // MENSAGEM DE ERRO
    // =====================================================

    private void mostrarErro(
            String mensagem,
            RuntimeException e) {

        JOptionPane.showMessageDialog(
                this,
                mensagem
                        + "\n"
                        + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
}