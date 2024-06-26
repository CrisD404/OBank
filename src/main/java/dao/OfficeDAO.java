package dao;

import entity.Office;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class OfficeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public OfficeDAO() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        this.entityManager = emf.createEntityManager();
    }

    @Transactional
    public void save(Office office) {
        entityManager.getTransaction().begin();
        entityManager.persist(office);
        entityManager.getTransaction().commit();
    }

    @Transactional
    public List<Office> get() {
        return entityManager.createQuery("SELECT o FROM Office o", Office.class).getResultList();
    }

    @Transactional
    public Office get(Long id) {
        return entityManager.find(Office.class, id);
    }
}
