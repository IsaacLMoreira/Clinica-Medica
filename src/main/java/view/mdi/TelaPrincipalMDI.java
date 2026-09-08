package view.mdi;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaPrincipalMDI extends JFrame {

    // Área onde as janelas internas serão abertas
    private JDesktopPane desktopPane;

    // Botões principais
    private JButton btnCadastrar;
    private JButton btnConsultar;
    private JButton btnSair;


    public TelaPrincipalMDI() {

        configurarJanela();
        inicializarComponentes();
    }


    // Configura a janela principal da aplicação
    private void configurarJanela() {

        setTitle("Clínica Médica - MDI");

        setSize(1200, 750);

        // Centraliza a janela
        setLocationRelativeTo(null);

        // Permite controlar o fechamento manualmente
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /*
         * Quando o usuário clicar no X,
         * executa a mesma confirmação do botão Sair.
         */
        addWindowListener(
                new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(
                            java.awt.event.WindowEvent e) {

                        sair();
                    }
                }
        );
    }


    // Cria e organiza os componentes da tela
    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        criarCabecalho();
        criarDesktopPane();
    }


    /*
     * Cria o título e os botões
     * na parte superior da aplicação.
     */
    private void criarCabecalho() {

        JPanel painelSuperior =
                new JPanel(
                        new BorderLayout()
                );

        painelSuperior.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );


        // Título
        JLabel lblTitulo =
                new JLabel(
                        "CLÍNICA MÉDICA - MDI",
                        SwingConstants.CENTER
                );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );


        // Painel dos botões
        JPanel painelBotoes =
                new JPanel();


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


        // Define as ações dos botões
        btnCadastrar.addActionListener(
                e -> abrirCadastro()
        );

        btnConsultar.addActionListener(
                e -> abrirConsulta()
        );

        btnSair.addActionListener(
                e -> sair()
        );


        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnConsultar);
        painelBotoes.add(btnSair);


        painelSuperior.add(
                lblTitulo,
                BorderLayout.NORTH
        );

        painelSuperior.add(
                painelBotoes,
                BorderLayout.SOUTH
        );


        add(
                painelSuperior,
                BorderLayout.NORTH
        );
    }


    /*
     * Cria a área principal do MDI.
     *
     * Todas as JInternalFrame serão
     * adicionadas dentro deste componente.
     */
    private void criarDesktopPane() {

        desktopPane =
                new JDesktopPane();

        desktopPane.setBorder(
                BorderFactory.createEtchedBorder()
        );

        add(
                desktopPane,
                BorderLayout.CENTER
        );
    }


    // =====================================================
    // ABRIR CADASTRO
    // =====================================================

    private void abrirCadastro() {

        /*
         * Verifica se já existe uma janela
         * de cadastro aberta.
         */
        for (var janela :
                desktopPane.getAllFrames()) {

            if (janela instanceof CadastroPacienteMDI) {

                // Traz a janela existente para frente
                janela.toFront();

                try {
                    janela.setSelected(true);

                } catch (
                        java.beans.PropertyVetoException e) {

                    // A janela apenas continua aberta
                }

                return;
            }
        }


        // Se não existir, cria uma nova
        CadastroPacienteMDI cadastro =
                new CadastroPacienteMDI();


        // Adiciona a janela dentro do JDesktopPane
        desktopPane.add(cadastro);


        // Centraliza a janela interna
        centralizarJanelaInterna(
                cadastro
        );


        cadastro.setVisible(true);
    }


    // =====================================================
    // ABRIR CONSULTA
    // =====================================================

    private void abrirConsulta() {

        /*
         * Evita abrir várias janelas
         * de consulta ao mesmo tempo.
         */
        for (var janela :
                desktopPane.getAllFrames()) {

            if (janela instanceof ConsultaPacienteMDI) {

                janela.toFront();

                try {
                    janela.setSelected(true);

                } catch (
                        java.beans.PropertyVetoException e) {

                    // A janela apenas continua aberta
                }

                return;
            }
        }


        // Cria a tela de consulta
        ConsultaPacienteMDI consulta =
                new ConsultaPacienteMDI();


        // Coloca a janela dentro do desktop
        desktopPane.add(consulta);


        // Centraliza no espaço disponível
        centralizarJanelaInterna(
                consulta
        );


        consulta.setVisible(true);
    }


    // =====================================================
    // CENTRALIZAR JANELA INTERNA
    // =====================================================

    /*
     * Calcula a posição necessária para
     * centralizar uma JInternalFrame.
     */
    private void centralizarJanelaInterna(
            javax.swing.JInternalFrame janela) {

        int larguraDesktop =
                desktopPane.getWidth();

        int alturaDesktop =
                desktopPane.getHeight();

        int larguraJanela =
                janela.getWidth();

        int alturaJanela =
                janela.getHeight();


        int x =
                (larguraDesktop - larguraJanela)
                        / 2;

        int y =
                (alturaDesktop - alturaJanela)
                        / 2;


        /*
         * Evita valores negativos caso a janela
         * interna seja maior que o desktop.
         */
        janela.setLocation(
                Math.max(0, x),
                Math.max(0, y)
        );
    }


    // =====================================================
    // SAIR
    // =====================================================

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

            System.exit(0);
        }
    }
}