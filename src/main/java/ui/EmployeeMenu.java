package ui;

import lombok.Data;

import javax.swing.*;

public @Data class EmployeeMenu {
    private JPanel panel;
    private JTable atmTable;
    private JButton reponerSucursalButton;
    private JComboBox officeSelector;
    private JLabel infoTitle;
    private JLabel officeIdLabel;
    private JLabel addressLabel;
    private JLabel amountAvailableLabel;

    private void createUIComponents() {
        atmTable = new JTable();
    }

}