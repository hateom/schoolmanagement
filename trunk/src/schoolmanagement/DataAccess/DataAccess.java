/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.DataAccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jasiu
 */
public class DataAccess {

    public void save(Object object) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("schoolmanagementPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public List getData() {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("schoolmanagementPU");
        return emf.createEntityManager().createQuery("SELECT p FROM SmPersons p").getResultList();
}

}
