package ui;

import controller.*;
import entity.*;
import model.ATMModel;
import model.UserModel;
import service.*;
import util.TransactionTypeE;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Supplier;

public class Layout extends CardLayout {
    private static Layout instance;
    private final JPanel panels;
    private final Map<String, Supplier<JPanel>> panelSuppliers = new HashMap<>();
    private final Stack<String> panelHistory = new Stack<>();

    private Layout() {
        //Models
        UserModel userModel = new UserModel();
        ATMModel atmModel = new ATMModel();
        //Services
        OfficeService officeService = new OfficeService();
        SavingsAccountService savingsAccountService = new SavingsAccountService();
        TransactionService transactionService = new TransactionService();
        AtmService atmService = new AtmService();
        AccountService accountService = new AccountService();
        //HARDCODED EXAMPLES
        Atm atm1 = new Atm();
        atm1.setAvailableMoney(10000.0);
        ATMTransaction exTransaction1 = new ATMTransaction();
        exTransaction1.setAmount(50000.0);
        exTransaction1.setType(TransactionTypeE.WITHDRAW);
        exTransaction1.setDate(LocalDateTime.of(2024, 6, 10, 13, 50));
        atm1.getTransaction().add(exTransaction1);

        Atm atm2 = new Atm();
        atm2.setAvailableMoney(300000.0);

        Atm atm3 = new Atm();
        atm3.setAvailableMoney(149000.0);

        Atm atm4 = new Atm();
        atm4.setAvailableMoney(100.0);
        ATMTransaction exTransaction2 = new ATMTransaction();
        exTransaction2.setAmount(50000.0);
        exTransaction2.setType(TransactionTypeE.WITHDRAW);
        exTransaction2.setDate(LocalDateTime.of(2024, 5, 5, 22, 40));
        atm4.getTransaction().add(exTransaction2);

        ATMTransaction exTransaction3 = new ATMTransaction();
        exTransaction3.setAmount(190000.0);
        exTransaction3.setType(TransactionTypeE.DEPOSIT);
        exTransaction3.setDate(LocalDateTime.of(2024, 6, 30, 17, 8));
        atm4.getTransaction().add(exTransaction3);

        ATMTransaction exTransaction4 = new ATMTransaction();
        exTransaction4.setAmount(61000.0);
        exTransaction4.setType(TransactionTypeE.DEPOSIT);
        exTransaction4.setDate(LocalDateTime.of(2024, 4, 1, 15, 12));
        atm4.getTransaction().add(exTransaction4);

        Atm atm5 = new Atm();
        atm5.setAvailableMoney(19000.0);

        Office exOffice = new Office();
        exOffice.setName("OBank 9 de julio");
        exOffice.setAddress("Carlos Pellegrini 430 entre Av. Corrientes & Lavalle, CABA");
        exOffice.setAvailableMoney(800000.0);
        exOffice.getAtm().add(atm1);
        exOffice.getAtm().add(atm2);
        exOffice.getAtm().add(atm3);
        officeService.save(exOffice);

        Office exOffice2 = new Office();
        exOffice2.setName("OBank Palermo City");
        exOffice2.setAddress("Av. Palermicity 128, esq Falsiti, CABA");
        exOffice2.setAvailableMoney(10000.0);
        exOffice2.getAtm().add(atm4);
        exOffice2.getAtm().add(atm5);
        officeService.save(exOffice2);

        //HARDCODED EXAMPLE
        Client exClient = new Client();
        exClient.setId(40131123L);
        exClient.setName("Cristian");
        exClient.setLastName("Del Canto");
        exClient.setPhone("1132215487");
        exClient.setEmail("example@example.com");
        exClient.setAddress("example 123");
        Card exCard = new Card();
        exCard.setNumber("1234-4567-8910-1234");
        exCard.setCvc(123);
        exCard.setExpirationDate(LocalDate.now());
        SavingsAccount exSavingsAccount = new SavingsAccount();
        exSavingsAccount.setBalance(50000.0);
        exSavingsAccount.setAlias("COUNTER.NARUTO.MESSI");
        exSavingsAccount.setCurrency("ARS");
        exSavingsAccount.setClient(exClient);
        exSavingsAccount.setCard(exCard);
        exSavingsAccount.setPrivateKey(123456);
        exSavingsAccount.setClient(exClient);
        AccountTransaction exAccountTransaction1 = new AccountTransaction();
        AccountTransaction exAccountTransaction2 = new AccountTransaction();
        AccountTransaction exAccountTransaction3 = new AccountTransaction();
        exAccountTransaction1.setType(TransactionTypeE.DEPOSIT);
        exAccountTransaction1.setAmount(10000.0);
        exAccountTransaction1.setDate(LocalDateTime.of(2021, 8, 1, 11, 12));
        exSavingsAccount.getTransactions().add(exAccountTransaction1);
        exAccountTransaction2.setType(TransactionTypeE.DEPOSIT);
        exAccountTransaction2.setAmount(10000.0);
        exAccountTransaction2.setDate(LocalDateTime.of(2023, 1, 14, 1, 23));
        exSavingsAccount.getTransactions().add(exAccountTransaction2);
        exAccountTransaction3.setType(TransactionTypeE.DEPOSIT);
        exAccountTransaction3.setAmount(10000.0);
        exAccountTransaction3.setDate(LocalDateTime.of(2020, 12, 12, 15, 57));
        exSavingsAccount.getTransactions().add(exAccountTransaction3);
        savingsAccountService.save(exSavingsAccount);

        //hardcoded users
        Employee emp = new Employee();
        emp.setId(42148776);
        emp.setName("Cristian");
        emp.setLastName("Del Canto");
        accountService.register("crdelcanto", "hola123", emp);

        //set router
        panels = new JPanel(this);

        panelSuppliers.put("Welcome", () -> {
            WelcomeController welcomeController = new WelcomeController(userModel);
            return welcomeController.getView().getPanel();
        });

        panelSuppliers.put("SplashBeforeClientLogin", () -> {
            SplashBeforeClientLogin splashBeforeClientLogin = new SplashBeforeClientLogin();
            return splashBeforeClientLogin.getPanel();
        });

        panelSuppliers.put("ClientLogin", () -> {
            ClientLoginController clientLoginController = new ClientLoginController(savingsAccountService, userModel, atmService);
            return clientLoginController.getView().getPanel();
        });

        panelSuppliers.put("ClientMenu", () -> {
            ClientMenuController clientMenuController = new ClientMenuController(userModel, transactionService, savingsAccountService);
            return clientMenuController.getView().getPanel();
        });

        panelSuppliers.put("Login", () -> {
            LoginController loginController = new LoginController(userModel, accountService);
            return loginController.getView().getPanel();
        });

        panelSuppliers.put("EmployeeMenu", () -> {
            EmployeeMenuController employeeMenuController = new EmployeeMenuController(atmModel, officeService);
            return employeeMenuController.getView().getPanel();
        });

        panelSuppliers.put("ATMTransaction", () -> {
            ATMTransactionsController atmTransactionsController = new ATMTransactionsController(atmModel);
            return atmTransactionsController.getView().getPanel();
        });

        panelSuppliers.put("ClientHistory", () -> {
            ClientHistoryController clientHistoryController = new ClientHistoryController(userModel);
            return clientHistoryController.getView().getPanel();
        });

        showPanel("Welcome");
    }

    public void setMainFrame(JFrame mainFrame) {
        mainFrame.getContentPane().add(panels);
    }

    public static Layout getInstance() {
        if (instance == null) {
            instance = new Layout();
        }
        return instance;
    }

    public void showPanel(String panel) {
        JPanel panelToShow = panelSuppliers.get(panel).get();
        panels.add(panelToShow, panel);
        this.show(panels, panel);
        panelHistory.push(panel);
    }

    public void goBack() {
        if (!panelHistory.isEmpty() && panelHistory.size() > 1) {
            panelHistory.pop();
            String previousPanel = panelHistory.peek();
            this.show(panels, previousPanel);
        }
    }

    public String getActivePanel() {
        return panelHistory.peek();
    }
}
