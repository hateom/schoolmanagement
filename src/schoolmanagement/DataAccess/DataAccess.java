/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.DataAccess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import schoolmanagement.controller.RoleType;
import schoolmanagement.entity.SmClass;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmPerson2class;
import schoolmanagement.entity.SmRole;
import schoolmanagement.entity.SmSchedule;
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
    
    public List<SmClass> getClassesForTeacher( SmTeacher a_oTeacher )
    {
        List<SmClass> lstClass = new ArrayList<SmClass>();
        Query query = m_oEm.createQuery("SELECT s FROM SmSchedule s WHERE s.schTchId = ?1").setParameter(1, a_oTeacher);
        try{
            List<SmSchedule> lst = query.getResultList();
            Iterator iter = lst.iterator();
            while(iter.hasNext())
            {
                SmClass cls = (SmClass)((SmSchedule)iter.next()).getSchClsId();
                if(!lstClass.contains(cls))
                {
                    lstClass.add(cls);
                }
            }
            return lstClass;
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
        Query query = null;
        if( a_strUserName.equals("") || a_strUserName== null)
        {
            query = m_oEm.createQuery("SELECT p FROM SmPerson p");
        }
        else
            query = m_oEm.createQuery("SELECT p FROM SmPerson INNER JOIN SmUser u.usrPerId user WHERE u.usrRolId = ?1").setParameter(1, a_strUserName);
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    public List<SmPerson> GetUserByRole(RoleType a_rtRole)
    {
        Query query = m_oEm.createQuery("SELECT u FROM SmUser u INNER JOIN u.usrRolId Role WHERE Role.rolId = ?1").setParameter(1, a_rtRole.ordinal());
        List<SmPerson> perList = new ArrayList<SmPerson>();
        try{
            List<SmUser> lst = query.getResultList();
            for(SmUser user : lst)
            {
                perList.add(user.getUsrPerId());
            }
            return perList;
        }
        catch(Exception e)
        {}
        return null;
    }
    
    /**
     * 
     * @return
     */
    public List<SmClass> GetAllClasses()
    {
        Query query = m_oEm.createQuery("SELECT c FROM SmClass c");
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    public List<SmRole> GetRoles()
    {
        Query query = m_oEm.createQuery("SELECT r FROM SmRole r");
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    public List<SmPerson> GetPersonsForClass(SmClass a_oClsId)
    {
        List lstPerson = new ArrayList<SmPerson>();
        Iterator it = a_oClsId.getSmPerson2classCollection().iterator();
       while(it.hasNext())
       {
           SmPerson2class osmPerson2Class = (SmPerson2class)it.next();
           lstPerson.add(osmPerson2Class.getP2cPerId());
       }
        return lstPerson;
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
