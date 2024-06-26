package dao;

import entity.Account;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class AccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public AccountDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        this.entityManager = emf.createEntityManager();

    }

    @Transactional
    public void save(Account account) {
        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();
    }

    @Transactional
    public Optional<Account> findByUsername(String username) {
        TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.username = :username", Account.class);
        List<Account> results = query.setParameter("username", username).getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

}
