/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.DataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import schoolmanagement.controller.ErrorLogger;
import schoolmanagement.controller.RoleType;
import schoolmanagement.controller.TeacherCollection;
import schoolmanagement.entity.SmClass;
import schoolmanagement.entity.SmClassroom;
import schoolmanagement.entity.SmDay;
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
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return false;
    }
    
    public boolean isConnected()
    {
        if(m_oEm != null)
            return m_oEm.isOpen();
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
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return false;
    }
    
    
    
 // USER METHODS -----------------------------------------------   
    /**
     * Logowanie uzytkownika
     */
    public SmUser loginUser(String a_strLogin, String a_strPassword)
    {
        //Query sq = m_oEm.createQuery("SELECT u FROM SmUser u WHERE u.usrLogin = ?1 AND u.usrPasswd = ?2").setParameter(1, a_strLogin).setParameter(2, a_strPassword);
        try{
            Query sq = m_oEm.createQuery("SELECT u FROM SmUser u WHERE u.usrLogin = ?1 AND u.usrPasswd = ?2").setParameter(1, a_strLogin).setParameter(2, a_strPassword);
            Object o = sq.getSingleResult();
            SmUser smUser = (SmUser)o;
            return smUser;
        }
        catch(Exception ex)
        {
           ex.printStackTrace();
        }
        return null;
    }
    
    public SmRole getUserRole(SmPerson a_oPerson)
    {
        try
        {
            Query query = m_oEm.createQuery("SELECT u.usrRolId FROM SmUser u WHERE u.usrPerId = ?1").setParameter(1, a_oPerson).setHint("refresh", new Boolean(true));
            return (SmRole)query.getSingleResult();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
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
        try
        {
            Query query = null;
            if( a_strUserName.equals("") || a_strUserName== null)
            {
                query = m_oEm.createQuery("SELECT p FROM SmPerson p").setHint("refresh", new Boolean(true));
            }
            else
                query = m_oEm.createQuery("SELECT p FROM SmPerson p WHERE p.perSurname LIKE ?1 OR p.perName LIKE ?1").setParameter(1, a_strUserName).setHint("refresh", new Boolean(true));

            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
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
        try{
            Query query = m_oEm.createQuery("SELECT r FROM SmRole r WHERE r.rolName = :rolName").setParameter("rolName", a_StrRole);
        return (SmRole)query.getSingleResult();
        }
        catch(Exception e)
        {}
        return null;
    }
    
    public List<SmSubject> getSubjectsForPerson( SmPerson a_oPerson )
    {
        try{
            Query query = m_oEm.createQuery("SELECT t.tchSubId FROM SmTeacher t WHERE t.tchPerId = ?1").setParameter(1, a_oPerson).setHint("refresh", new Boolean(true));
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
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
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return false;
    }
    
    public SmTeacher getTeacher( SmSubject a_oSubject, SmPerson a_oPerson)
    {
        try{
            Query query = m_oEm.createQuery("SELECT t FROM SmTeacher t WHERE t.tchSubId = ?1 AND t.tchPerId = ?2").setParameter(1, a_oSubject).setParameter(2, a_oPerson).setHint("refresh", new Boolean(true));
            return (SmTeacher) query.getSingleResult();
        }
        catch(NoResultException e)
        {
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    
    public List<SmTeacher> getTeacherForSubject(SmSubject a_oSubject)
    {
        try{
            Query query = m_oEm.createQuery("SELECT t FROM SmTeacher t WHERE t.tchSubId = ?1").setParameter(1, a_oSubject).setHint("refresh", new Boolean(true));
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    
    public List<Object[]> getScheduleForTeacher( SmPerson a_oPerson )
    {
        Query query = m_oEm.createQuery("SELECT DISTINCT s.schClsId, s.schTchId FROM SmSchedule s WHERE s.schTchId.tchPerId = ?1").setParameter(1, a_oPerson).setHint("refresh", new Boolean(true));
        try{
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    
    public SmTeacher addTeachersSubject( SmPerson a_oPerson, SmSubject a_oSubject)
    {
        SmTeacher teacher = new SmTeacher();
        teacher.setTchPerId(a_oPerson);
        teacher.setTchSubId(a_oSubject);
        if(save(teacher))
        {
           a_oSubject.getSmTeacherCollection().add(teacher);
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
    
    public List<SmTeacher> getAvailableTeachers(SmSubject a_oSubject, SmDay a_oDay , SmRing a_oRing)
    {
        try
        {
            List<SmPerson> lst = m_oEm.createQuery("SELECT s.schTchPerId FROM SmSchedule s WHERE s.schDayId = ?1 AND s.schRngId = ?2").setParameter(1, a_oDay).setParameter(2, a_oRing).setHint("refresh", new Boolean(true)).getResultList();
            String str ="";
            Query query = null;
            for(SmPerson p : lst)
            {
                str+= p.getPerId()+",";
            }
            if(str.length()>0)
            {
                str = str.substring(0, str.length()-1);
                 query = m_oEm.createQuery("SELECT t FROM SmTeacher t WHERE t.tchSubId = ?1 AND t.tchPerId.perId NOT IN("+str+")").setParameter(1, a_oSubject).setHint("refresh", new Boolean(true));
            }
            else
            {
                query = m_oEm.createQuery("SELECT t FROM SmTeacher t WHERE t.tchSubId = ?1").setParameter(1, a_oSubject).setHint("refresh", new Boolean(true));
            }
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
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
            SmPerson2class p2c = (SmPerson2class) m_oEm.createQuery("SELECT p2c FROM SmPerson2class p2c WHERE p2c.p2cPerId = ?2 AND p2c.p2cClsId = ?1").setParameter(1, a_oClass).setParameter(2, a_oPerson).setHint("refresh", new Boolean(true)).getSingleResult();
            if(delete(p2c))
            {
                a_oPerson.getSmPerson2classCollection().remove(p2c);
                a_oClass.getSmPerson2classCollection().remove(p2c);
            }
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
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
                if(!subjectList.contains(sh.getSchSubId()))
                    subjectList.add(sh.getSchSubId());
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
          case ROLE_ADMIN: { return getRoleByName("Admin");}
          case ROLE_PRINCIPAL: { return getRoleByName("Dyrektor");}
          case ROLE_STUDENT: { return getRoleByName("Uczeń");}
            default: return null;
        }
    }
    
    public List<SmPerson> GetPersonsForClass(SmClass a_oClass)
    {
        try
        {
            Query query = m_oEm.createQuery("SELECT p2c.p2cPerId FROM SmPerson2class p2c WHERE p2c.p2cClsId = ?1 ORDER BY p2c.p2cPerId.perSurname").setParameter(1, a_oClass).setHint("refresh", new Boolean(true));
            return query.getResultList();
        }
        catch(Exception e)
        {
        }
        return null;
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
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
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
                query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?1 AND m.msgSenderUsrId = ?2").setParameter(1, a_oCurrentUser).setParameter(2, a_oSenderOrRecp).setHint("refresh", new Boolean(true));
            }
        }
        else
        {
            if( !a_bIsReaded )
            {
                query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?1 AND m.msgReaded = ?2 ORDER BY m.msgSendDate DESC").setParameter(1, a_oCurrentUser).setParameter(2, a_bIsReaded).setHint("refresh", new Boolean(true));
            }
            else
            {
                query = m_oEm.createQuery("SELECT m FROM SmMessage m WHERE m.msgRecpUsrId = ?1 ORDER BY m.msgSendDate DESC").setParameter(1, a_oCurrentUser).setHint("refresh", new Boolean(true));
            }
        }
        try{
        return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
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
    
    
    
    ////--------------------------------CLASSROM METHODS----------------
    public List<SmClassroom> getAllClasrooms()
    {
        try
        {
            Query query = m_oEm.createQuery("SELECT c FROM SmClassroom c").setHint("refresh", new Boolean(true));
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    
    public boolean isClassroomAvailable(SmClassroom a_oClr, SmDay a_oDay, SmRing a_oRing)
    {
        try
        {
            Query query = m_oEm.createQuery("SELECT s.schClrId FROM SmSchedule s WHERE s.schDayId = ?1 AND s.schRngId = ?2").setParameter(1, a_oDay).setParameter(2, a_oRing).setHint("refresh", new Boolean(true));
            SmClassroom clr = (SmClassroom) query.getSingleResult();
            return clr.getClrId() != a_oClr.getClrId();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return true;
    }
    
    public List<SmClassroom> getAvailableClassrooms(SmDay a_oDay , SmRing a_oRing)
    {
        try
        {
            List<SmClassroom> lst = m_oEm.createQuery("SELECT s.schClrId FROM SmSchedule s WHERE s.schDayId = ?1 AND s.schRngId = ?2").setParameter(1, a_oDay).setParameter(2, a_oRing).setHint("refresh", new Boolean(true)).getResultList();
            String str ="";
            Query query = null;
            for(SmClassroom clr : lst)
            {
                str+= clr.getClrId()+",";
            }
            if(str.length()>0)
            {
                str = str.substring(0, str.length()-1);
                 query = m_oEm.createQuery("SELECT c FROM SmClassroom c WHERE c.clrId NOT IN("+str+")").setHint("refresh", new Boolean(true));
            }
            else
            {
                query = m_oEm.createQuery("SELECT c FROM SmClassroom c").setHint("refresh", new Boolean(true));
            }
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    
    public SmClassroom addClassRom( int a_nClassroomNum, SmPerson a_oOwnerPer, String a_strDescription )
    {
        try
        {
            SmClassroom clsr = new SmClassroom();
            clsr.setClrId(a_nClassroomNum);
            clsr.setClrOwnerPerId(a_oOwnerPer);
            clsr.setClrDescr(a_strDescription);
            if(save(clsr))
                return clsr;
            return null;
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    public boolean updateClassroom(SmClassroom a_oClsRoom)
    {
        try
        {
            return save(a_oClsRoom);
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return false;
    }
    public boolean removeClassroom(SmClassroom a_oClsr)
    {
        try
        {
            return delete(a_oClsr);
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return false;
    }
    
    public List<SmClassroom> getClassroomByName( String a_oStrName )
    {
        try
        {
            Query query = m_oEm.createQuery("SELECT c FROM SmClassroom c WHERE c.clrDescr LIKE ?1").setParameter(1, a_oStrName).setHint("refresh", new Boolean(true));
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    ////--------------------------------END OF CLASSROM ----------------
    
    
    
    ////-------------------------------SCHEDULE--------------------------
    
    public List<SmDay> getAllDays()
    {
        try
        {
            Query query = m_oEm.createQuery("SELECT d FROM SmDay d");
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    
    public List<SmSchedule> getSchedule( SmDay a_oDay, SmClass a_oClass)
    {
        try
        {
            Query query = m_oEm.createQuery("SELECT s FROM SmSchedule s WHERE s.schDayId = ?1 AND s.schClsId=?2 ORDER BY s.schRngId.rngTime ASC").setParameter(1, a_oDay).setParameter(2, a_oClass).setHint("refresh", new Boolean(true));
            return query.getResultList();
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    
    public SmSchedule addSchedule( SmDay a_smDay, SmClass a_smClass, SmRing a_smRing, SmTeacher a_smTeacher, SmClassroom a_oClassroom)
    {
        try
        {
            SmSchedule sch = new SmSchedule();
            sch.setSchClrId(a_oClassroom);
            sch.setSchClsId(a_smClass);
            sch.setSchDayId(a_smDay);
            sch.setSchRngId(a_smRing);
            sch.setSchSubId(a_smTeacher.getTchSubId());
            sch.setSchTchPerId(a_smTeacher.getTchPerId());
            if(save(sch))
                return sch;
        }
        catch(Exception e)
        {
            ErrorLogger.getInstance().error(e.getLocalizedMessage()+" at:\n"+e.getStackTrace()[0].toString());
        }
        return null;
    }
    public boolean removeSchedule( SmSchedule a_oSchedule )
    {
        return delete(a_oSchedule);
    }
    
    public boolean updateSchedule( SmSchedule a_oSchedule )
    {
        return save(a_oSchedule);
    }
    
    ////-------------------------------END OF SCHEDULE ------------------
}
