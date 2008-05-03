/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.DataAccess;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmTeacher;
import schoolmanagement.entity.SmUser;

/**
 *
 * @author jasiu
 */
public class DataAccess {

    EntityManagerFactory m_oEmf;
    EntityManager m_oEm;
    
    /**
     * konstruktor tworzacy EntityManangera
     */
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
    
    /**
     * Logowanie uzytkownika
     */
    public SmUser loginUser(String a_strLogin, String a_strPassword)
    {
        Query sq = m_oEm.createQuery("SELECT u FROM SmUser u INNER JOIN FETCH u.usrRolId WHERE u.usrLogin = ?1 AND u.usrPasswd = ?2").setParameter(1, a_strLogin).setParameter(2, a_strPassword);
        //Query sq = m_oEm.createQuery("SELECT u FROM SmUser u WHERE u.usrLogin = ?1 AND u.usrPasswd = ?2").setParameter(1, a_strLogin).setParameter(2, a_strPassword);
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
    
    public List<SmTeacher> getTeacherList()
    {
        Query query = m_oEm.createQuery("SELECT t FROM SmTeacher t");
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    /**
     * Szuka czlowieczka po nazwisku
     * @return liste odnalezionych ludzikow
     */
    public List<SmPerson> GetUserByName(String a_strUserName)
    {
        Query query = m_oEm.createQuery("SELECT p FROM SmPerson p WHERE p.perSurname = ?1").setParameter(1, a_strUserName);
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    
    
    /**
     * 
     * @return
     */
    public List getData() {
        return m_oEm.createQuery("SELECT p FROM SmPersons p").getResultList();
    }
    
    /**
     * Zamkniecie sesji
     */
    public void dispose()
    {
        m_oEm.close();
    }

}
