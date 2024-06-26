package controller;

import entity.SavingsAccount;
import exception.NoMoneyException;
import exception.NotFoundException;
import lombok.Data;
import model.UserModel;
import service.SavingsAccountService;
import service.TransactionService;
import ui.ClientMenu;
import ui.Layout;
import ui.component.ClientTransferDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public @Data class ClientMenuController {
    ClientMenu view = new ClientMenu();
    UserModel userModel;
    TransactionService transactionService;
    SavingsAccountService savingsAccountService;

    public ClientMenuController(UserModel userModel, TransactionService transactionService, SavingsAccountService savingsAccountService) {
        this.savingsAccountService = savingsAccountService;
        this.userModel = userModel;
        this.transactionService = transactionService;
        view.getMenuPanel().setLayout(new GridLayout(2, 2));
        try {
            //TODO: MANAGE ONLY IMAGES ERRORS
            Icon movementsIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/icons/history-icon.png")));
            Icon depositIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/icons/deposit-icon.png")));
            Icon withdrawIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/icons/withdraw-icon.png")));
            Icon transferIcon = new ImageIcon(ImageIO.read(getClass().getResource("/assets/icons/transfer-icon.png")));

            JButton movementsBtn = createSquareButton("Movimientos", movementsIcon, e -> handleMovements());
            JButton depositBtn = createSquareButton("Depositar", depositIcon, e -> handleDeposit());
            JButton withdrawBtn = createSquareButton("Retirar", withdrawIcon, e -> handleWithdraw());
            JButton transferBtn = createSquareButton("Transferir", transferIcon, e -> handleTransfer());

            view.getMenuPanel().add(depositBtn);
            view.getMenuPanel().add(withdrawBtn);
            view.getMenuPanel().add(transferBtn);
            view.getMenuPanel().add(movementsBtn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado", "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.setBalanceLabel(userModel.getAccount().getBalance());
    }

    private void setBalanceLabel(Double balance) {
        System.out.println("Updating balance: "+ balance);
        view.getBalanceLabel().setText(STR."Dinero disponible: \{balance}");
    }

    private JButton createSquareButton(String text, Icon icon, ActionListener actionListener) {
        JButton button = new JButton(text, icon);
        Dimension size = new Dimension(100, 100);
        button.addActionListener(actionListener);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        return button;
    }

    private void handleMovements() {
        Layout.getInstance().showPanel("ClientHistory");
    }

    private void handleDeposit() {
        String input = JOptionPane.showInputDialog("Indique la cantidad que desea depositar");
        if(input == null || input.isEmpty()) {
            return;
        }

        try {
            Double amount = Double.parseDouble(input);
            this.transactionService.deposit(this.userModel.getAccount().getId(), this.userModel.getAtm().getId(), amount);
            SavingsAccount savingsAccount = savingsAccountService.get(userModel.getAccount().getId());
            this.userModel.setAccount(savingsAccount);
            this.setBalanceLabel(savingsAccount.getBalance());
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleWithdraw() {
        String input = JOptionPane.showInputDialog("Indique la cantidad que desea retirar");
        if(input == null || input.isEmpty()) {
            return;
        }

        try {
            Double amount = Double.parseDouble(input);
            this.transactionService.withdraw(this.userModel.getAccount().getId(), this.userModel.getAtm().getId(), amount);
            SavingsAccount savingsAccount = savingsAccountService.get(userModel.getAccount().getId());
            this.userModel.setAccount(savingsAccount);
            this.setBalanceLabel(savingsAccount.getBalance());
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.WARNING_MESSAGE);
        }
        catch (NoMoneyException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleTransfer() {
        new ClientTransferDialog(dialog -> {
            String alias = dialog.getAlias();
            String strAmount = dialog.getAmount();
            try {
                Double amount = Double.parseDouble(strAmount);
                this.transactionService.transfer(this.userModel.getAccount().getId(), alias, amount);
                SavingsAccount savingsAccount = savingsAccountService.get(userModel.getAccount().getId());
                this.userModel.setAccount(savingsAccount);
                this.setBalanceLabel(savingsAccount.getBalance());
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese un monto válido", "Error", JOptionPane.WARNING_MESSAGE);
            }
            catch (NoMoneyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            }
            catch (NotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "En este servicio no está disponible en este momento.", "Error", JOptionPane.ERROR);
            }
        });


    }

}
