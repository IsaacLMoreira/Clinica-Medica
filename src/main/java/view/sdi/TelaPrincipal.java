package view.sdi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaPrincipal extends JFrame {

    // Botões principais da tela
    private JButton btnCadastrar;
    private JButton btnConsultar;
    private JButton btnSair;

    public TelaPrincipal() {

        // Configura a janela
        configurarJanela();

        // Cria e organiza os componentes
        inicializarComponentes();
    }


    /*
     * Define as configurações básicas da janela principal.
     */
    private void configurarJanela() {

        setTitle("Clínica Médica");

        // Define o tamanho da janela
        setSize(500, 400);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        // Impede redimensionamento
        setResizable(false);

        /*
         * DO_NOTHING_ON_CLOSE permite controlar o fechamento
         * através de uma confirmação.
         */
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }


    /*
     * Cria os componentes visuais e monta a tela.
     */
    private void inicializarComponentes() {

        // Define o layout principal da janela
        setLayout(new BorderLayout());

        criarCabecalho();
        criarMenuPrincipal();
        criarRodape();

        /*
         * Executa a mesma confirmação do botão Sair
         * quando o usuário clicar no X da janela.
         */
        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(
                    java.awt.event.WindowEvent e) {

                sair();
            }
        });
    }


    /*
     * Cria o título apresentado na parte superior.
     */
    private void criarCabecalho() {

        JPanel painelCabecalho = new JPanel();

        painelCabecalho.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 10, 20, 10
                )
        );

        JLabel lblTitulo =
                new JLabel(
                        "CLÍNICA MÉDICA",
                        SwingConstants.CENTER
                );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        JPanel painelTitulos =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                0,
                                5
                        )
                );

        JLabel lblSubtitulo =
                new JLabel(
                        "Gerenciamento de Pacientes",
                        SwingConstants.CENTER
                );

        lblSubtitulo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        painelTitulos.add(lblTitulo);
        painelTitulos.add(lblSubtitulo);

        painelCabecalho.add(painelTitulos);

        add(
                painelCabecalho,
                BorderLayout.NORTH
        );
    }


    /*
     * Cria os botões de navegação do sistema.
     */
    private void criarMenuPrincipal() {

        JPanel painelMenu =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                10,
                                15
                        )
                );

        painelMenu.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        100,
                        20,
                        100
                )
        );

        btnCadastrar =
                new JButton(
                        "Cadastrar Paciente"
                );

        btnConsultar =
                new JButton(
                        "Consultar Pacientes"
                );

        btnSair =
                new JButton(
                        "Sair"
                );


        // Mantém os botões com um tamanho semelhante
        Dimension tamanhoBotao =
                new Dimension(
                        250,
                        45
                );

        btnCadastrar.setPreferredSize(
                tamanhoBotao
        );

        btnConsultar.setPreferredSize(
                tamanhoBotao
        );

        btnSair.setPreferredSize(
                tamanhoBotao
        );


        /*
         * Define o que acontecerá quando
         * cada botão for pressionado.
         */
        btnCadastrar.addActionListener(
                e -> abrirCadastro()
        );

        btnConsultar.addActionListener(
                e -> abrirConsulta()
        );

        btnSair.addActionListener(
                e -> sair()
        );


        painelMenu.add(btnCadastrar);
        painelMenu.add(btnConsultar);
        painelMenu.add(btnSair);

        add(
                painelMenu,
                BorderLayout.CENTER
        );
    }


    /*
     * Cria uma pequena identificação
     * na parte inferior da janela.
     */
    private void criarRodape() {

        JLabel lblRodape =
                new JLabel(
                        "Sistema de Clínica Médica - SDI",
                        SwingConstants.CENTER
                );

        lblRodape.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        15,
                        10
                )
        );

        add(
                lblRodape,
                BorderLayout.SOUTH
        );
    }


    /*
     * Abre a tela de cadastro.
     *
     * Quando CadastroPaciente.java estiver pronto,
     * substituiremos a mensagem pela criação da janela.
     */
    // Abre a janela de cadastro de pacientes
        private void abrirCadastro() {

        new CadastroPacienteSDI()
            .setVisible(true);
        }


    /*
     * Abre a tela de consulta.
     *
     * Quando ConsultaPaciente.java estiver pronta,
     * substituiremos a mensagem pela criação da janela.
     */
    // Abre a janela de consulta de pacientes
        private void abrirConsulta() {

        new ConsultaPacienteSDI()
                .setVisible(true);
        }


    /*
     * Solicita confirmação antes
     * de encerrar o programa.
     */
    private void sair() {

        int resposta =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente sair do sistema?",
                        "Confirmar saída",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

        if (resposta ==
                JOptionPane.YES_OPTION) {

            // Fecha todas as janelas e encerra a aplicação
            System.exit(0);
        }
    }
}