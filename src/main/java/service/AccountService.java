package service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.AccountDAO;
import entity.Account;
import entity.Employee;
import exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static util.Constant.SECURITY_ERROR;

public class AccountService {
    private final AccountDAO accountDAO = new AccountDAO();

    public Employee login(String username, String password) throws SecurityException, NotFoundException {
        Optional<Account> result = accountDAO.findByUsername(username);
        Account account = result.orElseThrow(() -> new NotFoundException(SECURITY_ERROR));

        System.out.println("Cuenta: "+account);

        if (account == null || account.getAttempts() == 3) {
            throw new SecurityException(SECURITY_ERROR);
        }

        if(!BCrypt.verifyer().verify(password.toCharArray(), account.getPassword()).verified) {
            account.setAttempts(account.getAttempts() + 1);
            accountDAO.save(account);
            throw new SecurityException(SECURITY_ERROR);
        }

        account.setAttempts(0);
        account.setLastLogin(LocalDateTime.now());
        accountDAO.save(account);

        return account.getEmployee();
    }

    public void register(String username, String password, Employee employee) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        account.setEmployee(employee);
        accountDAO.save(account);
    }
}
