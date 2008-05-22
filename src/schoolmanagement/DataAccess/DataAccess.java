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
import schoolmanagement.controller.ErrorLogger;
import schoolmanagement.controller.RoleType;
import schoolmanagement.controller.TeacherCollection;
import schoolmanagement.entity.SmClass;
import schoolmanagement.entity.SmMessage;
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
 * Data Base manipulation subsystem
 * provides access to underlying database using Java Persistence API
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
    public boolean save(Object object) {
        m_oEm.getTransaction().begin();
        try {
            m_oEm.persist(object);
            m_oEm.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            m_oEm.getTransaction().rollback();
            ErrorLogger.error(e.getLocalizedMessage());
        }
        return false;
    }
    
    public boolean delete(Object object)
    {
        m_oEm.getTransaction().begin();
        try {
            m_oEm.remove(object);
            m_oEm.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            m_oEm.getTransaction().rollback();
            ErrorLogger.error(e.getLocalizedMessage());
        }
        return false;
    }
    
 // USER METHODS -----------------------------------------------   
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
    
    public void modifyUser(SmUser a_oUser)
    {
        save(a_oUser);
    }
    
    public boolean modifyUserAndPerson(SmUser a_oUser, SmPerson a_oPerson)
    {
        m_oEm.getTransaction().begin();
        try {
            m_oEm.persist(a_oUser);
            m_oEm.persist(a_oPerson);
            m_oEm.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            m_oEm.getTransaction().rollback();
        }
        return false;
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
    
        /**
     * Szuka czlowieczka po nazwisku
     * @return liste odnalezionych ludzikow
     */
    public List<SmPerson> GetUserByName(String a_strUserName)
    {
        Query query = null;
        if( a_strUserName.equals("") || a_strUserName== null)
        {
            query = m_oEm.createQuery("SELECT p FROM SmPerson p").setHint("refresh", new Boolean(true));
        }
        else
            query = m_oEm.createQuery("SELECT p FROM SmPerson p WHERE p.perSurname LIKE ?1 OR p.perName LIKE ?1").setParameter(1, a_strUserName).setHint("refresh", new Boolean(true));
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    public List<SmPerson> GetUserByRole(RoleType a_rtRole)
    {
        Query query = m_oEm.createQuery("SELECT u FROM SmUser u INNER JOIN u.usrRolId Role WHERE Role.rolId = ?1").setParameter(1, a_rtRole.ordinal()).setHint("refresh", new Boolean(true));
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
    
    public SmUser addUser(SmPerson a_oPerID, SmRole a_oRole, String a_strLogin, String a_strPasswd)
    {
        SmUser usr = new SmUser();
        usr.setUsrRolId(a_oRole);
        usr.setUsrLogin(a_strLogin);
        usr.setUsrPerId(a_oPerID);
        usr.setUsrPasswd(a_strPasswd);
        if(save(usr))
        {
            a_oPerID.getSmUserCollection().add(usr);
            return usr;
        }
        return null;
    }
    
    public SmUser getUserByPerson(SmPerson a_oPerson)
    {
        Query query = m_oEm.createQuery("SELECT u FROM SmUser u WHERE u.usrPerId = ?1").setParameter(1, a_oPerson).setHint("refresh", new Boolean(true));
        try{
            return (SmUser)query.getSingleResult();
        }
        catch(Exception e)
        {}
        return null;
    }
    
//---------------END OF USER METHODS------------
    
//----------PERSON METHODS---------------
    public SmPerson addPerson( String a_strName, String a_strSurname, int a_nPesel, Integer a_nNip, Integer a_nPhone, String a_strAddress, String a_strEmail, boolean a_bSaveToDB)
    {
        SmPerson person = new SmPerson();
        person.setPerAdress(a_strAddress);
        person.setPerEmail(a_strEmail);
        person.setPerName(a_strName);
        person.setPerSurname(a_strSurname);
        person.setPerPhone(a_nPhone);
        person.setPerNip(a_nNip);
        person.setPerPesel(a_nPesel);
        if(a_bSaveToDB){
            if(save(person))
                return person;
            return null;
        }
        return person;
    }
    
    public void savePerson(SmPerson a_oPerson)
    {
        save(a_oPerson);
    }
    
    public SmRole getRoleByName(String a_StrRole)
    {
        Query query = m_oEm.createQuery("SELECT r FROM SmRole r WHERE r.rolName = :rolName").setParameter("rolName", a_StrRole);
        try{
        return (SmRole)query.getSingleResult();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    public List<SmSubject> getSubjectsForPerson( SmPerson a_oPerson )
    {
        Collection<SmTeacher> col = a_oPerson.getSmTeacherCollection();
        if( col != null ){
            List<SmSubject> subList = new ArrayList<SmSubject>();
            Iterator it = col.iterator();
            if(it != null)
            {
                while( it.hasNext() )
                {
                    subList.add( ((SmTeacher)it.next()).getTchSubId() );
                }
                return subList;
            }
        }
        return null;
    }
    
 //-------------------END OF PERSON METHODS-------------   
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
//------------    TEACHER STUFF -------------
    public List<TeacherCollection> getTeacherList()
    {
        Query query = m_oEm.createQuery("SELECT t FROM SmTeacher t").setHint("refresh", new Boolean(true));
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
    
    public boolean removeTeachersSubject(SmPerson a_oPerson, SmSubject a_oSubject)
    {
        Query query = m_oEm.createQuery("SELECT t FROM SmTeacher t WHERE t.tchPerId = ?1 AND t.tchSubId = ?2").setParameter(1, a_oPerson).setParameter(2, a_oSubject).setHint("refresh", new Boolean(true));
        SmTeacher teacher = null;
        try
        {
            teacher = (SmTeacher)query.getSingleResult();
            m_oEm.getTransaction().begin();
            a_oPerson.getSmTeacherCollection().remove(teacher);
            a_oSubject.getSmTeacherCollection().remove(teacher);
            m_oEm.remove(teacher);
            m_oEm.getTransaction().commit();
            return true;
        }
        catch(Exception e)
        {
            m_oEm.getTransaction().rollback();
            if( teacher != null )
            {
                a_oPerson.getSmTeacherCollection().add(teacher);
                a_oSubject.getSmTeacherCollection().add(teacher);
            }
            ErrorLogger.error(e.getLocalizedMessage());
        }
        return false;
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
            return teacherList;
        }
        return null;
    }
    
    public List<Object[]> getScheduleForTeacher( SmPerson a_oPerson )
    {
        Query query = m_oEm.createQuery("SELECT DISTINCT s.schClsId, s.schTchId FROM SmSchedule s WHERE s.schTchId.tchPerId = ?1").setParameter(1, a_oPerson);
        try{
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.error(e.getLocalizedMessage());
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
    public SmTeacher addTeachersSubject( SmPerson a_oPerson, SmSubject a_oSubject)
    {
        SmTeacher teacher = new SmTeacher();
        teacher.setTchPerId(a_oPerson);
        teacher.setTchSubId(a_oSubject);
        if(save(teacher))
        {
            //a_oSubject.getSmTeacherCollection().add(teacher);
            a_oPerson.getSmTeacherCollection().add(teacher);
            return teacher;
        }
        return null;
    }
    
    public boolean teacherHasSubject( SmPerson a_oPerson, SmSubject a_oSubject)
    {
        Query query = m_oEm.createQuery("SELECT COUNT(t) FROM SmTeacher t WHERE t.tchPerId = ?1 AND t.tchSubId = ?2").setParameter(1, a_oPerson).setParameter(2, a_oSubject).setHint("refresh", new Boolean(true));
        try{
        Long count = (Long)query.getSingleResult();
        if(count != null && count >0)
            return true;
        }
        catch(Exception e)
        {}
        return false;
    }
    
    
 //------------------------------- END OF TEACHER STUFF
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
    
//---------------------------- END OF NOTEZ STUFF
    
    
//=------------------------------CLASS STUFF
    
    /**
     * 
     * @return
     */
    public List<SmClass> GetAllClasses()
    {
        Query query = m_oEm.createQuery("SELECT c FROM SmClass c").setHint("refresh", new Boolean(true));
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    public boolean deleteClass( SmClass a_oClass )
    {
        return delete(a_oClass);
    }
    
    public SmClass createNewClass(Integer a_nClsNumber, String a_strClsAlph, String a_strClsDesc, SmPerson a_oTutor)
    {
        SmClass cls = new SmClass();
        cls.setClsNumber(a_nClsNumber);
        cls.setClsPerId(a_oTutor);
        cls.setClsDescription(a_strClsDesc);
        cls.setClsNumberAlph(a_strClsAlph);
        if(save(cls))
            return cls;
        return null;
    }
    
    public boolean saveChanges( SmClass a_oClass )
    {
        return save(a_oClass);
    }
    
    public boolean removePersonFromClass( SmClass a_oClass, SmPerson a_oPerson )
    {
        try
        {
            SmPerson2class p2c = (SmPerson2class) m_oEm.createQuery("SELECT").setParameter(1, a_oClass).setParameter(2, a_oPerson).setHint("refresh", new Boolean(true)).getSingleResult();
            if(delete(p2c))
            {
                a_oPerson.getSmPerson2classCollection().remove(p2c);
                a_oClass.getSmPerson2classCollection().remove(p2c);
            }
        }
        catch(Exception e)
        {}
        return false;
    }
    
    public boolean addPersonToClass(SmClass a_oClass, SmPerson a_oPerson)
    {
        SmPerson2class p2c = new SmPerson2class();
        p2c.setP2cPerId(a_oPerson);
        p2c.setP2cClsId(a_oClass);
        if( save(p2c) )
        {
            a_oPerson.getSmPerson2classCollection().add(p2c);
            a_oClass.getSmPerson2classCollection().add(p2c);
            return true;
        }
        return false;
    }
    
    public List<SmSubject> getSubjectsList()
    {
        Query query = m_oEm.createQuery("SELECT s FROM SmSubject s").setHint("refresh", new Boolean(true));
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
    
    public boolean classContains( SmClass a_oClass, SmPerson a_oPerson)
    {
        try
        {
            Long count = (Long)m_oEm.createQuery("SELECT COUNT(p2c) FROM SmPerson2class p2c WHERE p2c.p2cPerId = ?1 AND p2c.p2cClsId = ?2").setParameter(1, a_oPerson).setParameter(2, a_oClass).setHint("refresh", new Boolean(true)).getSingleResult();
           if (count != null) return count != 0L;
           return false;
        }
        catch(Exception e)
        {
        }
        return false;
    }
    
//----------------------------------END OF CLASS STUFF
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
    
    public SmRole getRoleByType(RoleType a_eRole)
    {
        List<SmRole> roles = GetRoles();
        switch(a_eRole)
        {
          case ROLE_TEACHER: { return getRoleByName("Nauczyciel");}
          case ROLE_ADMIN: { return getRoleByName("Nauczyciel");}
          case ROLE_PRINCIPAL: { return getRoleByName("Nauczyciel");}
          case ROLE_STUDENT: { return getRoleByName("Nauczyciel");}
            default: return null;
        }
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
//----------------------------------- MESSAGES UTILZ
    public List<SmMessage> getSentMessages(SmUser a_oCurrentUser, SmUser a_oSenderOrRecp)
    {
        Query query = null;        
        if( a_oSenderOrRecp != null )
        {
            query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?2 AND m.msgSenderUsrId = ?1 ORDER BY m.msgSendDate DESC").setParameter(1, a_oCurrentUser).setParameter(2, a_oSenderOrRecp).setHint("refresh", new Boolean(true));
        }
        else
        {
            query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgSenderUsrId = ?1 ORDER BY m.msgSendDate DESC").setParameter(1, a_oCurrentUser).setHint("refresh", new Boolean(true));
        }
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.error(e.getLocalizedMessage());
        }
        return null;
    }
    
    public List<SmMessage> getRecievedMessages(SmUser a_oCurrentUser, SmUser a_oSenderOrRecp, boolean a_bIsReaded)
    {
        Query query = null;        
        if( a_oSenderOrRecp != null )
        {
            if( !a_bIsReaded )
            {
                query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?1 AND m.msgSenderUsrId = ?2 AND m.msgReaded = ?3 ORDER BY m.msgSendDate DESC").setParameter(1, a_oCurrentUser).setParameter(2, a_oSenderOrRecp).setParameter(3, a_bIsReaded).setHint("refresh", new Boolean(true));
            }
            else
            {
                query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?1 AND m.msgSenderUsrId = ?2 ORDER BY m.msgSendDate DESC").setParameter(1, a_oCurrentUser).setParameter(2, a_oSenderOrRecp).setHint("refresh", new Boolean(true));
            }
        }
        else
        {
            if( !a_bIsReaded )
            {
                query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?1 AND m.msgReaded = ?2").setParameter(1, a_oCurrentUser).setParameter(2, a_bIsReaded).setHint("refresh", new Boolean(true));
            }
            else
            {
                query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?1").setParameter(1, a_oCurrentUser).setHint("refresh", new Boolean(true));
            }
        }
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.error(e.getLocalizedMessage());
        }
        return null;
    }
    
    public boolean sendMessage(SmUser a_oRecpUser, SmUser a_oSenderUser, String a_strBody, String a_strTopic, boolean a_bIsRespReq, int a_nSeverity )
    {
        SmMessage message = new SmMessage();
        message.setMsgBody(a_strBody);
        message.setMsgReaded(false);
        message.setMsgRecpUsrId(a_oRecpUser);
        message.setMsgSenderUsrId(a_oSenderUser);
        Date d = new Date();
        d.setTime(System.currentTimeMillis());
        message.setMsgSendDate(d);
        message.setMsgSeverity(a_nSeverity);
        message.setMsgTopic(a_strTopic);
        return save(message);
    }
    
    public boolean markAsRead(SmMessage a_oMessage, boolean a_bIsReaded)
    {
        a_oMessage.setMsgReaded(a_bIsReaded);
        return save(a_oMessage);
    }
    
    public boolean deleteMessage(SmMessage a_oMessage)
    {
        return delete(a_oMessage);
    }
//------------------------------------
}
