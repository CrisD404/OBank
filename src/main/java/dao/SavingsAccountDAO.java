package dao;

import entity.SavingsAccount;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

public class SavingsAccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public SavingsAccountDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        this.entityManager = emf.createEntityManager();
    }

    public void save(SavingsAccount savingsAccount) {
        entityManager.getTransaction().begin();
        entityManager.merge(savingsAccount);
        entityManager.getTransaction().commit();
    }

    public SavingsAccount findByCardNum(String cardNum) {
        TypedQuery<SavingsAccount> query = entityManager.createQuery("SELECT a FROM SavingsAccount a WHERE a.card.number = :cardNum", SavingsAccount.class);
        query.setParameter("cardNum", cardNum);
        List<SavingsAccount> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.getFirst();
        }
        return null;
    }

    public SavingsAccount get(Long id) {
        SavingsAccount account = entityManager.find(SavingsAccount.class, id);
        entityManager.refresh(account);
        return account;
    }

    public SavingsAccount findByAlias(String alias) {
        TypedQuery<SavingsAccount> query = entityManager.createQuery("SELECT a FROM SavingsAccount a WHERE a.alias = :alias", SavingsAccount.class);
        query.setParameter("alias", alias);
        List<SavingsAccount> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.getFirst();
        }
        return null;
    }

}
