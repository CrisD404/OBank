package controller;

import entity.*;
import lombok.Data;
import model.UserModel;
import service.AtmService;
import service.SavingsAccountService;
import ui.ClientLogin;
import ui.Layout;
import ui.component.ComboBox.AtmItem;
import ui.component.ComboBox.Item;
import util.TransactionTypeE;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public @Data class ClientLoginController {
    ClientLogin view = new ClientLogin();
    SavingsAccountService savingsAccountService;
    AtmService atmService;
    UserModel userModel;

    public ClientLoginController(SavingsAccountService savingsAccountService, UserModel userModel, AtmService atmService) {
        this.atmService = atmService;
        this.savingsAccountService = savingsAccountService;
        this.userModel = userModel;

        view.getCode().setFont(new Font("Monospaced", Font.PLAIN, 25));
        view.getCode().setHorizontalAlignment(JTextField.CENTER);
        view.getCode().setEchoChar('*');
        view.getCode().setColumns(6);
        view.getCode().setDocument(new PlainDocument() {
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null) {
                    return;
                }
                if ((getLength() + str.length()) <= 6 && str.matches("[0-9]+")) {
                    super.insertString(offset, str, attr);
                }
            }
        });

        view.getKeyboardPanel().setLayout(new GridLayout(4, 3));
        for (int i = 1; i <= 9; i++) {
            int input = i;
            agregarBoton(String.valueOf(i), e -> this.addDigit(String.valueOf(input)));
        }
        agregarBoton("CLEAR", e -> this.removeDigit());
        agregarBoton("0", e -> this.addDigit(String.valueOf(0)));
        agregarBoton("ENTER", e -> this.login());

        List<Atm> listAtm = atmService.get();
        view.getSelectAtm().removeAllItems();
        for (Atm atm: listAtm ) {
            view.getSelectAtm().addItem(new AtmItem(atm));
        }
    }



    private void addDigit(String digit) {
        String previousText = view.getCode().getText();
        if(previousText.length() < 6) {
            view.getCode().setText(previousText + digit);
        }
    }

    private void removeDigit() {
        view.getCode().setText("");
    }

    private void login() {
        String code = view.getCode().getText();
        if (!(code.length() == 6)) {
            JOptionPane.showMessageDialog(null, "La clave ingresada debe tener 6 digitos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            AtmItem atmItem = (AtmItem) view.getSelectAtm().getSelectedItem();
            if(atmItem == null) {
                JOptionPane.showMessageDialog(null, "El cajero está fuera de servicio, intente más tarde.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                SavingsAccount account = this.savingsAccountService.verify("1234-4567-8910-1234", Integer.valueOf(code));
                userModel.setAccount(account);
                userModel.setAtm(atmItem.getAtm());
                Layout.getInstance().showPanel("ClientMenu");
            }
        }
    }

    private void agregarBoton(String texto, ActionListener actionListener) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setSize(new Dimension(20, 20));
        button.addActionListener(actionListener);
        view.getKeyboardPanel().add(button);
    }
}
