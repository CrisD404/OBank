package service;

import entity.ATMTransaction;
import entity.AccountTransaction;
import entity.Atm;
import entity.SavingsAccount;
import exception.NoMoneyException;
import exception.NotFoundException;
import jakarta.transaction.Transactional;
import util.TransactionTypeE;

public class TransactionService {
    private SavingsAccountService savingsAccountService = new SavingsAccountService();
    private AtmService atmService = new AtmService();

    @Transactional
    public void deposit(Long accountId, Long atmId, Double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        SavingsAccount account = savingsAccountService.get(accountId);
        Atm atm = atmService.get(atmId);

        account.setBalance(account.getBalance() + amount);

        AccountTransaction transaction = new AccountTransaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionTypeE.DEPOSIT);
        account.getTransactions().add(transaction);

        atm.setAvailableMoney(atm.getAvailableMoney() + amount);
        ATMTransaction atmTransaction = new ATMTransaction();
        atmTransaction.setAmount(amount);
        atmTransaction.setType(TransactionTypeE.DEPOSIT);
        atm.getTransaction().add(atmTransaction);

        savingsAccountService.save(account);
        atmService.save(atm);
    }

    @Transactional
    public void withdraw(Long accountId, Long atmId, Double amount) throws NoMoneyException {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        SavingsAccount account = savingsAccountService.get(accountId);
        Atm atm = atmService.get(atmId);

        if(account.getBalance() < amount) {
            throw new NoMoneyException("No puede retirar esa cantidad de dinero, intente con un monto menor.");
        }

        if(atm.getAvailableMoney() < amount) {
            throw new NoMoneyException("Este cajero no tiene fondos suficientes para el retiro.");
        }

        account.setBalance(account.getBalance() - amount);

        AccountTransaction transaction = new AccountTransaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionTypeE.WITHDRAW);
        account.getTransactions().add(transaction);

        atm.setAvailableMoney(atm.getAvailableMoney() - amount);
        ATMTransaction atmTransaction = new ATMTransaction();
        atmTransaction.setAmount(amount);
        atmTransaction.setType(TransactionTypeE.WITHDRAW);
        atm.getTransaction().add(atmTransaction);

        savingsAccountService.save(account);
        atmService.save(atm);
    }

    @Transactional
    public void transfer(Long accountId, String alias, Double amount) throws NoMoneyException, NotFoundException {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        SavingsAccount account = savingsAccountService.get(accountId);
        SavingsAccount transferAccount = savingsAccountService.getByAlias(alias);

        if(transferAccount == null) {
            throw new NotFoundException("El usuario al que intentas transferir no existe.");
        }

        if(account.getBalance() < amount) {
            throw new NoMoneyException("No puedes transferir esa cantidad de dinero, intente con un monto menor.");
        }

        account.setBalance(account.getBalance() - amount);
        transferAccount.setBalance(transferAccount.getBalance() + amount);

        AccountTransaction transactionAccount = new AccountTransaction();
        transactionAccount.setAmount(amount);
        transactionAccount.setType(TransactionTypeE.TRANSFER);
        account.getTransactions().add(transactionAccount);

        AccountTransaction transactionTransferAccount = new AccountTransaction();
        transactionTransferAccount.setAmount(amount);
        transactionTransferAccount.setType(TransactionTypeE.DEPOSIT);
        transferAccount.getTransactions().add(transactionTransferAccount);

        savingsAccountService.save(account);
        savingsAccountService.save(transferAccount);
    }

}
