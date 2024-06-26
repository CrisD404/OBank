package controller;

import entity.AccountTransaction;
import lombok.Data;
import model.UserModel;
import ui.ClientHistory;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public @Data class ClientHistoryController {
    ClientHistory view = new ClientHistory();

    public ClientHistoryController(UserModel userModel) {
        List<AccountTransaction> accountTransactions = userModel.getAccount().getTransactions();
        buildTableModel(accountTransactions);
    }

    private void buildTableModel(List<AccountTransaction> accountTransactions) {
        final String[] columnNames = {"id", "fecha", "hora", "Tipo", "Cantidad"};
        Object[][] atmListCast = new Object[accountTransactions.size()][5];

        for (int i = 0; i < accountTransactions.size(); i++) {
            AccountTransaction accountTransaction = accountTransactions.get(i);
            atmListCast[i][0] = accountTransaction.getId();
            atmListCast[i][1] = accountTransaction.getDate().toLocalDate();
            atmListCast[i][2] = accountTransaction.getDate().toLocalTime();
            atmListCast[i][3] = accountTransaction.getType();
            atmListCast[i][4] = accountTransaction.getAmount();
        }
        view.getTable1().setModel((new DefaultTableModel(atmListCast, columnNames)));
    }
}
