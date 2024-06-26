package ui;

import lombok.Data;

import javax.swing.*;

public @Data class Login {
    private JButton loginButton;
    private JLabel userLabel;
    private JLabel title;
    private JPasswordField passField;
    private JLabel passLabel;
    private JTextField userField;
    public JPanel panel;
    private JPanel colM;
    private JPanel header;
    private JPanel main;
    private JPanel section;
    private JButton registerButton;

    public Login() {
        //listener: frame.showPanel("Menu");
    }

}
