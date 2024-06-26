package dao;

import entity.Atm;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

public class AtmDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public AtmDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        this.entityManager = emf.createEntityManager();
    }

    @Transactional
    public List<Atm> get() {
        return entityManager.createQuery("SELECT a FROM Atm a", Atm.class).getResultList();
    }

    @Transactional
    public Atm get(Long id) {
        return entityManager.find(Atm.class, id);
    }

    @Transactional
    public void save(Atm atm) {
        entityManager.getTransaction().begin();
        entityManager.merge(atm);
        entityManager.getTransaction().commit();
    }
}
