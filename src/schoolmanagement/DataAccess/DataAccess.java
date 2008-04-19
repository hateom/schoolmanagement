/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.DataAccess;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import schoolmanagement.entity.SmUser;

/**
 *
 * @author jasiu
 */
public class DataAccess {

    EntityManagerFactory m_oEmf;
    EntityManager m_oEm;
    
    public DataAccess()
    {
     m_oEmf = javax.persistence.Persistence.createEntityManagerFactory("schoolmanagementPU");   
     m_oEm = m_oEmf.createEntityManager();
    }
    public void save(Object object) {
        m_oEm.getTransaction().begin();
        try {
            m_oEm.persist(object);
            m_oEm.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            m_oEm.getTransaction().rollback();
        }
    }
    
    public SmUser loginUser(String a_strLogin, String a_strPassword)
    {
        Query sq = m_oEm.createQuery("SELECT u FROM SmUser u INNER JOIN FETCH u.usrRolId WHERE u.usrLogin = ?1 AND u.usrPasswd = ?2").setParameter(1, a_strLogin).setParameter(2, a_strPassword);
        try{
            Object o = sq.getSingleResult();
            SmUser smUser = (SmUser)o;
            return smUser;
        }
        catch(Exception ex)
        {
            
        }
        return null;
    }
    
    
    public List getData() {
        return m_oEm.createQuery("SELECT p FROM SmPersons p").getResultList();
}

    public void persist(Object object) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("schoolmanagementPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    public void dispose()
    {
        m_oEm.close();
    }

}
