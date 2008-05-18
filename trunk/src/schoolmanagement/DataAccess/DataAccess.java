/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.DataAccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import schoolmanagement.controller.RoleType;
import schoolmanagement.controller.TeacherCollection;
import schoolmanagement.entity.SmClass;
import schoolmanagement.entity.SmNote;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmPerson2class;
import schoolmanagement.entity.SmRing;
import schoolmanagement.entity.SmRole;
import schoolmanagement.entity.SmSchedule;
import schoolmanagement.entity.SmSubject;
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
    
    public boolean changeUserPassword(String a_strNewPasswd, String a_strOldPasswd, int a_nUsrId)
    {
        Query q = m_oEm.createNamedQuery("SmUser.findByUsrId").setParameter("usrId",a_nUsrId);
        SmUser user = (SmUser)q.getSingleResult();
        if( !user.getUsrPasswd().equals(a_strOldPasswd) )
        {
            return false;
        }
        try
        {
        user.setUsrPasswd(a_strNewPasswd);
        save(user);
        return true;
        }
        catch(Exception e)
        {
        }
        
        return false;
    }
    
    public SmPerson createNewPerson(  )
    {
        return null;
    }
    
    public boolean changeRingTime(SmRing a_oRing, Date a_oNewRingTime)
    {
        try
        {
            a_oRing.setRngTime(a_oNewRingTime);
            save(a_oRing);
            return true;
        }
        catch(Exception e)
        {
            
        }
        return false;
    }
    
    public List<TeacherCollection> getTeacherList()
    {
        Query query = m_oEm.createQuery("SELECT t FROM SmTeacher t");
        List<TeacherCollection> teachColList = new ArrayList<TeacherCollection>();
        try{
            List<SmTeacher> lst = query.getResultList();
            for( SmTeacher teacher : lst )
            {
                boolean found = false;
                int idx = 0;
                for( TeacherCollection tch : teachColList )
                {
                    if(tch.containsTeacher(teacher))
                    {
                        found = true;
                        break;
                    }
                    idx++;
                }
                if(!found)
                {
                    TeacherCollection col = new TeacherCollection();
                    col.getTeacherList().add(teacher);
                    teachColList.add(col);
                }
                else
                {
                  teachColList.get(idx).getTeacherList().add(teacher);
                }
            }
            return teachColList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<SmTeacher> getTeacherForSubject(SmSubject a_oSubject)
    {
        Collection<SmTeacher> teachCol = a_oSubject.getSmTeacherCollection();
        List<SmTeacher> teacherList = new ArrayList<SmTeacher>();
        if( teachCol != null )
        {
            Iterator it = teachCol.iterator();
            while( it.hasNext() )
            {
                teacherList.add( (SmTeacher)it.next() );
            }
        }
        return null;
    }
    
    public List<SmClass> getClassesForTeacher( TeacherCollection a_oTeacherCol )
    {
        List<SmClass> lstClass = new ArrayList<SmClass>();
        String lstID = "";
        for(SmTeacher teach : a_oTeacherCol.getTeacherList())
        {
            lstID += (teach.getTchId().toString()) + ",";
        }
        lstID = lstID.substring(0, lstID.length()-1);
        Query query = m_oEm.createQuery("SELECT s FROM SmSchedule s WHERE s.schTchId.tchId IN ("+ lstID +")");
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
            query = m_oEm.createQuery("SELECT p FROM SmPerson p WHERE p.perSurname LIKE ?1 OR p.perName LIKE ?1").setParameter(1, a_strUserName);
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    public List<SmNote> getNotes( SmSubject a_oSubject, SmClass a_oClass )
    {
        Query query = null;
        query = m_oEm.createQuery("SELECT n FROM SmNote n WHERE n.notTchId.tchSubId = ?1 AND n.notP2cId.p2cClsId = ?2").setParameter(1, a_oSubject).setParameter(2, a_oClass).setHint("refresh", new Boolean(true));
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    
    
    public List<SmNote> getNotes( TeacherCollection a_oTeacher, SmClass a_oClass, SmPerson a_oPupilID)
    {
        Query query = null;
        SmPerson teacherPerson = a_oTeacher.getPerson();
        if(teacherPerson != null){
            if( a_oPupilID == null )
            {
                query = m_oEm.createQuery("SELECT n FROM SmNote n WHERE n.notTchId.tchPerId = ?1 AND n.notP2cId.p2cClsId = ?2").setParameter(1, teacherPerson).setParameter(2, a_oClass).setHint("refresh", new Boolean(true));
                return query.getResultList();
            }
            else
            {

            }
        }
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
    
    public List<SmSubject> getSubjectsForClass( SmClass a_oClass )
    {
        Query query = m_oEm.createQuery("SELECT s FROM SmSchedule s WHERE s.schClsId = :class").setParameter("class", a_oClass);
        List<SmSubject> subjectList = new ArrayList<SmSubject>();
        try{ 
            List<SmSchedule> lst = query.getResultList();
            for(SmSchedule sh : lst)
            {
                if(!subjectList.contains(sh.getSchTchId().getTchSubId()))
                    subjectList.add(sh.getSchTchId().getTchSubId());
            }
            return subjectList;
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
    
    public List<SmRing> getRings()
    {
        return m_oEm.createQuery("SELECT r FROM SmRing r").getResultList();
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
