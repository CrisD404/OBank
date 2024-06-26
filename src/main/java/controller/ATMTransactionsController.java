package controller;

import entity.ATMTransaction;
import lombok.Data;
import model.ATMModel;
import ui.ATMTransactions;
import util.TransactionTypeE;

import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.List;

public @Data class ATMTransactionsController {
    ATMTransactions view = new ATMTransactions();

    public ATMTransactionsController(ATMModel atmModel) {
        List<ATMTransaction> atmTransactionsList = atmModel.getAtm().getTransaction();

        view.getIdAtmLabel().setText(view.getIdAtmLabel().getText().replace("$VALUE", atmModel.getAtm().getId().toString()));
        view.getTotalAvailableLabel().setText(view.getTotalAvailableLabel().getText().replace("$VALUE", atmModel.getAtm().getAvailableMoney().toString()));
        view.getTotalTransactionsLabel().setText(view.getTotalTransactionsLabel().getText().replace("$VALUE", STR."\{atmTransactionsList.size()}"));

        List<ATMTransaction> depositTransactions = atmTransactionsList.stream()
                .filter(t -> t.getType().equals(TransactionTypeE.DEPOSIT))
                .toList();

        List<ATMTransaction> withdrawalTransactions = atmTransactionsList.stream()
                .filter(t -> t.getType().equals(TransactionTypeE.WITHDRAW))
                .toList();

        DecimalFormat df = new DecimalFormat("#.##");
        double depositPercentage = ((double) depositTransactions.size() / atmTransactionsList.size()) * 100;
        double withdrawalPercentage = ((double) withdrawalTransactions.size() / atmTransactionsList.size()) * 100;

        String totalTypeLabel = view
                .getTotalTypeLabel()
                .getText()
                .replace("$VALUE1", STR."\{depositTransactions.size()} - \{df.format(depositPercentage)}%")
                .replace("$VALUE2", STR."\{withdrawalTransactions.size()} - \{df.format(withdrawalPercentage)}%");

        view.getTotalTypeLabel().setText(totalTypeLabel);

        buildTableModel(atmTransactionsList);
    }

    private void buildTableModel(List<ATMTransaction> atmTransactionsList) {
        final String[] columnNames = {"id", "fecha", "hora", "Tipo", "Cantidad"};
        Object[][] atmListCast = new Object[atmTransactionsList.size()][5];

        for (int i = 0; i < atmTransactionsList.size(); i++) {
            ATMTransaction atmTransaction = atmTransactionsList.get(i);
            atmListCast[i][0] = atmTransaction.getId();
            atmListCast[i][1] = atmTransaction.getDate().toLocalDate();
            atmListCast[i][2] = atmTransaction.getDate().toLocalTime();
            atmListCast[i][3] = atmTransaction.getType();
            atmListCast[i][4] = atmTransaction.getAmount();
        }

        view.getTable1().setModel((new DefaultTableModel(atmListCast, columnNames)));
    }
}
