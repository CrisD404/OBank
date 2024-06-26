import ui.Window;

import javax.swing.*;

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        Window window = new Window();
        window.setVisible(true);
    });
}
