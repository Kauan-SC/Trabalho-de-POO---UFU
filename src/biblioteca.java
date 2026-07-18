import gui.JanelaPrincipal;
import javax.swing.SwingUtilities;

// Ponto de entrada do programa, abre a janela principal
public class biblioteca {
    public static void main(String[] args) {
        // invokeLater garante que a janela seja criada na thread certa do Swing
        SwingUtilities.invokeLater(() -> new JanelaPrincipal().setVisible(true));
    }
}
