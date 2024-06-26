package ui.component.Table;

import controller.EmployeeMenuController;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {
    private final ButtonsPanel panel = new ButtonsPanel();
    private final JTable table;
    private Object o;

    private class EditingStopHandler extends MouseAdapter implements ActionListener {
        @Override
        public void mousePressed(MouseEvent e) {
            Object o = e.getSource();
            if (o instanceof TableCellEditor) {
                actionPerformed(null);
            } else if (o instanceof JButton) {
                ButtonModel m = ((JButton) e.getComponent()).getModel();
                if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
                    panel.setBackground(table.getBackground());
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(ButtonsEditor.this::fireEditingStopped);
        }
    }

    public ButtonsEditor(JTable table, EmployeeMenuController controller) {
        super();
        this.table = table;

        panel.buttons.get(0).setAction(new AbstractAction(ActionsE.REPONER.toString()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.convertRowIndexToModel(table.getEditingRow());
                Long id = (Long) table.getModel().getValueAt(row, 0);
                controller.handleReplenish(id);
            }
        });

        panel.buttons.get(1).setAction(new AbstractAction(ActionsE.MOVIMIENTOS.toString()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.convertRowIndexToModel(table.getEditingRow());
                Long id = (Long) table.getModel().getValueAt(row, 0);
                controller.handleMovements(id);
            }
        });

        EditingStopHandler handler = new EditingStopHandler();
        for (JButton b : panel.buttons) {
            b.addMouseListener(handler);
            b.addActionListener(handler);
        }
        panel.addMouseListener(handler);
    }
    @Override public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {
        panel.setBackground(table.getSelectionBackground());
        panel.updateButtons(value);
        o = value;
        return panel;
    }
    @Override public Object getCellEditorValue() {
        return o;
    }
}
