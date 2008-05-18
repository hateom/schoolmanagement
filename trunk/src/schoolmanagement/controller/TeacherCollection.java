/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmTeacher;

/**
 *
 * @author jasiu
 */

public class TeacherCollection {
    private List<SmTeacher> m_lstTeachersEntities;
    
    public TeacherCollection()
    {
        m_lstTeachersEntities = new ArrayList<SmTeacher>();
    }
    
    public List<SmTeacher> getTeacherList()
    {
        return m_lstTeachersEntities;
    }
    
    public void setTeacherList(List<SmTeacher> a_lstTeacher)
    {
        m_lstTeachersEntities = a_lstTeacher;
    }
    
    @Override
    public String toString()
    {
        if(m_lstTeachersEntities != null && m_lstTeachersEntities.size() > 0)
            return m_lstTeachersEntities.get(0).getTchPerId().toString();
        return "TeacherCollection Class";
    }
    
    
    public boolean containsTeacher( SmTeacher o )
    {
            if(m_lstTeachersEntities != null && m_lstTeachersEntities.size() > 0)
            {
                SmPerson leftPer = m_lstTeachersEntities.get(0).getTchPerId();
                SmPerson rightPer = o.getTchPerId();
                if (leftPer == rightPer)
                    return true;
            }
        return false;
    }
    
    public SmPerson getPerson()
    {
        if(m_lstTeachersEntities != null && m_lstTeachersEntities.size() > 0)
        {
            return m_lstTeachersEntities.get(0).getTchPerId();
        }
        return null;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.m_lstTeachersEntities != null ? this.m_lstTeachersEntities.hashCode() : 0);
        return hash;
    }
}
