package controller;

import entity.Employee;
import exception.NotFoundException;
import lombok.Data;
import model.UserModel;
import service.AccountService;
import ui.Layout;
import ui.Login;

import javax.swing.*;

import static util.Constant.DEFAULT_ERROR;

public @Data class LoginController {
    Login view = new Login();
    AccountService accountService;
    UserModel userModel;

    public LoginController(UserModel userModel, AccountService accountService) {
        this.accountService = accountService;
        this.userModel = userModel;
        view.getLoginButton().addActionListener(e -> this.handleLogin(view.getUserField().getText(), String.valueOf(view.getPassField().getPassword())));


    }

    public void handleLogin(String username, String password) {
        //TODO: Validate fields
        try {
            Employee employee = accountService.login(username, password);
            this.userModel.setUser(employee);
            Layout.getInstance().showPanel("EmployeeMenu");
        }
        catch (SecurityException | NotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de seguridad", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex) {
            System.out.println("LoginController: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, DEFAULT_ERROR, "Error de seguridad", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void handleRegister(String username, String password, Employee employee) {
        accountService.register(username, password, employee);
    }

}
