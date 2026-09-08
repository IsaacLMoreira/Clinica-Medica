package main;

import javax.swing.SwingUtilities;

import view.mdi.TelaPrincipalMDI;

public class MainMDI {

    public static void main(String[] args) {

        // Inicia a interface gráfica na thread apropriada do Swing
        SwingUtilities.invokeLater(() -> {

            new TelaPrincipalMDI().setVisible(true);

        });
    }
}