
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class AccountDao {
    private EntityManagerFactory emf;

    public AccountDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void addNewAccount(Account acc) {
        System.out.println(acc);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(acc);
            em.getTransaction().commit();
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void mergeAccount(Account acc) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Account buffer = acc;
            em.merge(buffer);
            em.getTransaction().commit();
        }finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    public List getAllAccounts() {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT OBJECT(o) from Account o");
            return q.getResultList();
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    };

    public List getAllAccounts(double balance) {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT OBJECT(o) from Account o where o.balance > " + balance);
            return q.getResultList();
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    };

    public void deleteAll() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("DELETE FROM Account");
            q.executeUpdate();
            em.getTransaction().commit();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

}