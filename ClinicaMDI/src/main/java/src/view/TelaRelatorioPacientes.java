package view;

import javax.swing.table.DefaultTableModel;
import model.Paciente;
import repository.PacienteRepositorio;

public class TelaRelatorioPacientes extends javax.swing.JInternalFrame {

    private PacienteRepositorio repo = new PacienteRepositorio();
    private javax.swing.JTable jTableRelatorio;
    private javax.swing.JScrollPane jScrollPane1;

    public TelaRelatorioPacientes() {
        initComponents();
        carregarDados();
    }

    private void initComponents() {
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Relatório de Pacientes Cadastrados");
        setSize(750, 400);

        jTableRelatorio = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane(jTableRelatorio);

        jTableRelatorio.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] { "Código", "Nome", "CPF", "Data Nasc.", "Telefone", "E-mail", "Convênio" }
        ) {
            boolean[] canEdit = new boolean [] { false, false, false, false, false, false, false };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }

    public void carregarDados() {
        DefaultTableModel model = (DefaultTableModel) jTableRelatorio.getModel();
        model.setRowCount(0);
        for (Paciente p : repo.listarTodos()) {
            model.addRow(new Object[]{
                p.getCodigo(), p.getNome(), p.getCpf(),
                p.getDataNascimento(), p.getTelefone(), p.getEmail(), p.getConvenio()
            });
        }
    }
}