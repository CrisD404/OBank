package ui.component.Table;
import controller.EmployeeMenuController;
import entity.Atm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class ATMTable {

    private final String[] columnNames = {"id", "Dinero disponible", "Estado", "Acciones"};
    private final JTable table;
    private final EmployeeMenuController controller;

    public ATMTable(JTable table, List<Atm> atmList, EmployeeMenuController controller) {
        this.table = table;
        this.controller = controller;
        build(atmList);
    }

    public void build(List<Atm> atmList) {
        Object[][] atmListCast = new Object[atmList.size()][3];

        for (int i = 0; i < atmList.size(); i++) {
            Atm atm = atmList.get(i);
            atmListCast[i][0] = atm.getId();
            atmListCast[i][1] = atm.getAvailableMoney();
            atmListCast[i][2] = atm.getStatus();
        }

        //TODO: Create a generic DefaultTableModel.
        table.setModel((new DefaultTableModel(atmListCast, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return JPanel.class;
                }
                return String.class;
            }
        }));

        TableColumn actionsColumn = table.getColumn("Acciones");

        actionsColumn.setCellRenderer(new TableCellRenderer() {
            private final ButtonsPanel panel = new ButtonsPanel();
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                panel.updateButtons(value);
                return panel;
            }
        });

        actionsColumn.setCellEditor(new ButtonsEditor(table, controller));

        table.setRowHeight(38);
        TableColumn idColumn = table.getColumn("id");
        idColumn.setMinWidth(20);
        idColumn.setMaxWidth(20);
        TableColumn moneyColumn = table.getColumn("Dinero disponible");
        moneyColumn.setMinWidth(80);
        TableColumn statusColumn = table.getColumn("Estado");
        statusColumn.setMinWidth(120);
        statusColumn.setMaxWidth(120);

        table.getColumn("Acciones").setMinWidth(100);
    }
}
