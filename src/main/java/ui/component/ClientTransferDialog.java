package ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class ClientTransferDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel aliasLabel;
    private JLabel amountLabel;
    private JLabel title;
    private JTextField aliasField;
    private JTextField amountField;

    public ClientTransferDialog(Consumer<ClientTransferDialog> onConfirm) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400, 200);
        setLocationRelativeTo(null);

        buttonOK.addActionListener(e -> onConfirm.accept(this));
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        this.setVisible(true);
    }

    public String getAlias() {
        return aliasField.getText();
    }

    public String getAmount() {
        return amountField.getText();
    }

    private void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
