package view.mdi;

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
import javax.swing.JInternalFrame;
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

public class ConsultaPacienteMDI extends JInternalFrame {

    // Responsável pelas operações no banco
    private final PacienteRepository repository;

    // Guarda os pacientes exibidos atualmente na tabela
    private List<Paciente> pacientesExibidos;

    // Componentes da pesquisa
    private JComboBox<String> cbTipoBusca;
    private JTextField txtBusca;

    private JButton btnBuscar;
    private JButton btnListarTodos;

    // Tabela
    private JTable tabelaPacientes;
    private DefaultTableModel modeloTabela;

    // Campos usados para alteração
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtDataNascimento;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtConvenio;

    // Botões
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnFechar;

    // Formato usado para exibir e receber datas
    private final DateTimeFormatter formatoData =
            DateTimeFormatter
                    .ofPattern("dd/MM/uuuu")
                    .withResolverStyle(
                            ResolverStyle.STRICT
                    );


    public ConsultaPacienteMDI() {

        /*
         * Configura o JInternalFrame:
         *
         * título
         * redimensionável
         * fechável
         * maximizável
         * minimizável
         */
        super(
                "Consulta de Pacientes",
                true,
                true,
                true,
                true
        );

        repository =
                new PacienteRepository();

        pacientesExibidos =
                new ArrayList<>();

        configurarJanela();
        inicializarComponentes();

        // Lista os pacientes assim que a janela abrir
        carregarTodos();
    }


    // =====================================================
    // CONFIGURAÇÃO DA JANELA
    // =====================================================

    private void configurarJanela() {

        setSize(
                1050,
                600
        );

        /*
         * Fecha somente esta janela interna.
         * A TelaPrincipalMDI permanece aberta.
         */
        setDefaultCloseOperation(
                JInternalFrame.DISPOSE_ON_CLOSE
        );
    }


    // =====================================================
    // COMPONENTES PRINCIPAIS
    // =====================================================

    private void inicializarComponentes() {

        setLayout(
                new BorderLayout()
        );

        criarCabecalho();
        criarAreaCentral();
        criarBotoes();
    }


    // =====================================================
    // CABEÇALHO
    // =====================================================

    private void criarCabecalho() {

        JPanel painelCabecalho =
                new JPanel();

        painelCabecalho.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        10,
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


        painelCabecalho.add(
                lblTitulo
        );


        add(
                painelCabecalho,
                BorderLayout.NORTH
        );
    }


    // =====================================================
    // ÁREA CENTRAL
    // =====================================================

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


    // =====================================================
    // BUSCA
    // =====================================================

    private JPanel criarPainelBusca() {

        JPanel painelBusca =
                new JPanel();


        JLabel lblBuscarPor =
                new JLabel(
                        "Buscar por:"
                );


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
                new JButton(
                        "Buscar"
                );


        btnListarTodos =
                new JButton(
                        "Listar Todos"
                );


        // Realiza a pesquisa
        btnBuscar.addActionListener(
                e -> buscar()
        );


        // Volta a mostrar todos os pacientes
        btnListarTodos.addActionListener(
                e -> carregarTodos()
        );


        // Pressionar Enter também realiza a busca
        txtBusca.addActionListener(
                e -> buscar()
        );


        painelBusca.add(
                lblBuscarPor
        );

        painelBusca.add(
                cbTipoBusca
        );

        painelBusca.add(
                txtBusca
        );

        painelBusca.add(
                btnBuscar
        );

        painelBusca.add(
                btnListarTodos
        );


        return painelBusca;
    }


    // =====================================================
    // JTABLE
    // =====================================================

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
         * Modelo responsável pelos dados
         * apresentados na JTable.
         */
        modeloTabela =
                new DefaultTableModel(
                        colunas,
                        0
                ) {

                    // Impede edição diretamente nas células
                    @Override
                    public boolean isCellEditable(
                            int linha,
                            int coluna) {

                        return false;
                    }
                };


        tabelaPacientes =
                new JTable(
                        modeloTabela
                );


        // Permite selecionar somente um paciente
        tabelaPacientes.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );


        /*
         * Ao selecionar uma linha,
         * carrega os dados nos campos inferiores.
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


    // =====================================================
    // FORMULÁRIO DE ALTERAÇÃO
    // =====================================================

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


        txtNome =
                new JTextField(20);

        txtCpf =
                new JTextField(20);

        txtDataNascimento =
                new JTextField(20);

        txtTelefone =
                new JTextField(20);

        txtEmail =
                new JTextField(20);

        txtConvenio =
                new JTextField(20);


        txtDataNascimento.setToolTipText(
                "Formato: dd/MM/yyyy"
        );


        // Linha 1
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


        // Linha 2
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


        // Linha 3
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


    /*
     * Método auxiliar para posicionar
     * os componentes do formulário.
     */
    private void adicionarCampo(
            JPanel painel,
            java.awt.Component componente,
            int coluna,
            int linha,
            GridBagConstraints gbc) {

        gbc.gridx =
                coluna;

        gbc.gridy =
                linha;


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


    // =====================================================
    // BOTÕES
    // =====================================================

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
                        15,
                        300
                )
        );


        btnAlterar =
                new JButton(
                        "Alterar"
                );


        btnExcluir =
                new JButton(
                        "Excluir"
                );


        btnFechar =
                new JButton(
                        "Fechar"
                );


        btnAlterar.addActionListener(
                e -> alterarPaciente()
        );


        btnExcluir.addActionListener(
                e -> excluirPaciente()
        );


        btnFechar.addActionListener(
                e -> dispose()
        );


        painelBotoes.add(
                btnAlterar
        );

        painelBotoes.add(
                btnExcluir
        );

        painelBotoes.add(
                btnFechar
        );


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
         * Se a busca estiver vazia,
         * volta a listar todos.
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

        // Remove as linhas anteriores
        modeloTabela.setRowCount(0);


        // Cada paciente vira uma linha da JTable
        for (Paciente paciente : pacientes) {

            modeloTabela.addRow(
                    new Object[]{
                        paciente.getId(),
                        paciente.getNome(),
                        paciente.getCpf(),

                        paciente
                                .getDataNascimento()
                                .format(
                                        formatoData
                                ),

                        paciente.getTelefone(),
                        paciente.getEmail(),
                        paciente.getConvenio()
                    }
            );
        }
    }


    // =====================================================
    // PACIENTE SELECIONADO
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
         * à posição na lista.
         */
        Paciente paciente =
                pacientesExibidos.get(
                        linha
                );


        txtNome.setText(
                paciente.getNome()
        );


        txtCpf.setText(
                paciente.getCpf()
        );


        txtDataNascimento.setText(
                paciente
                        .getDataNascimento()
                        .format(
                                formatoData
                        )
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


            // Recupera o paciente selecionado
            Paciente paciente =
                    pacientesExibidos.get(
                            linha
                    );


            // Converte a data para LocalDate
            LocalDate dataNascimento =
                    LocalDate.parse(
                            txtDataNascimento
                                    .getText()
                                    .trim(),
                            formatoData
                    );


            // Atualiza o objeto com os novos dados
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


            // Executa o UPDATE no banco
            repository.atualizar(
                    paciente
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Paciente alterado com sucesso!",
                    "Alteração",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // Recarrega a tabela
            carregarTodos();


        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data inválida.\n"
                            + "Utilize o formato dd/MM/yyyy.",
                    "Data inválida",
                    JOptionPane.WARNING_MESSAGE
            );


            txtDataNascimento
                    .requestFocus();


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
                pacientesExibidos.get(
                        linha
                );


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


        // Se escolher Não, encerra a operação
        if (resposta !=
                JOptionPane.YES_OPTION) {

            return;
        }


        try {

            // Exclui pelo ID
            repository.deletar(
                    paciente.getId()
            );


            JOptionPane.showMessageDialog(
                    this,
                    "Paciente excluído com sucesso!",
                    "Exclusão",
                    JOptionPane.INFORMATION_MESSAGE
            );


            // Atualiza a tabela
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

            txtDataNascimento
                    .requestFocus();

            throw new IllegalArgumentException(
                    "Informe a data de nascimento."
            );
        }


        if (txtTelefone
                .getText()
                .trim()
                .isEmpty()) {

            txtTelefone
                    .requestFocus();

            throw new IllegalArgumentException(
                    "Informe o telefone."
            );
        }


        if (txtEmail
                .getText()
                .trim()
                .isEmpty()) {

            txtEmail
                    .requestFocus();

            throw new IllegalArgumentException(
                    "Informe o e-mail."
            );
        }


        if (txtConvenio
                .getText()
                .trim()
                .isEmpty()) {

            txtConvenio
                    .requestFocus();

            throw new IllegalArgumentException(
                    "Informe o convênio."
            );
        }
    }


    // =====================================================
    // LIMPAR CAMPOS
    // =====================================================

    private void limparCampos() {

        tabelaPacientes
                .clearSelection();


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