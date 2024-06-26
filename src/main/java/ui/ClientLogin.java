package ui;

import lombok.Data;

import javax.swing.*;


public @Data class ClientLogin {
    private JPanel panel;
    private JLabel title;
    private JPasswordField code;
    private JPanel keyboardPanel;
    private JComboBox selectAtm;

    private void createUIComponents() {

    }

}
