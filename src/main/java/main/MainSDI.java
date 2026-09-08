package main;

import javax.swing.SwingUtilities;
import view.sdi.TelaPrincipal;

public class MainSDI {

    public static void main(String[] args) {

        // Inicia a interface gráfica na thread apropriada do Swing
        SwingUtilities.invokeLater(() -> {

            new TelaPrincipal().setVisible(true);

        });
    }
}