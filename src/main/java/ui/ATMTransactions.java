package ui;

import lombok.Data;

import javax.swing.*;

public @Data class ATMTransactions {

    private JPanel panel;
    private JTable table1;
    private JLabel titleLabel;
    private JPanel infoPanel;
    private JLabel totalTransactionsLabel;
    private JLabel infoTitle;
    private JLabel idAtmLabel;
    private JLabel totalTypeLabel;
    private JLabel totalAvailableLabel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
