package service;

import dao.SavingsAccountDAO;
import entity.SavingsAccount;

public class SavingsAccountService {

    SavingsAccountDAO savingsAccountDAO = new SavingsAccountDAO();

    public SavingsAccount verify(String cardNum, Integer privateKey) {
        SavingsAccount savingsAccount = savingsAccountDAO.findByCardNum(cardNum);
        if (savingsAccount == null || !savingsAccount.getPrivateKey().equals(privateKey)) {
            throw new SecurityException("Error se seguridad");
        }
        return savingsAccount;
    }

    public void save(SavingsAccount savingsAccount) {
        savingsAccountDAO.save(savingsAccount);
    }

    public SavingsAccount get(Long id) {
        return savingsAccountDAO.get(id);
    }

    public SavingsAccount getByAlias(String alias) {
        return savingsAccountDAO.findByAlias(alias);
    }
}
