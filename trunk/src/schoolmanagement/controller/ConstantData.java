/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import java.util.List;
import schoolmanagement.entity.SmRole;

/**
 *
 * @author jasiu
 */
public class ConstantData {
   private static List<Role> m_lstRoles;
   
    public static List<Role> GetRoles()
    {
        if( m_lstRoles == null)
        {
           List<SmRole> lst = DBAccess.GetInstance().GetRoles();
           for(SmRole role : lst)
           {
               m_lstRoles.add(new Role(role.getRolName(), RoleType.GetRoleType(role.getRolId())));
           }
        }
        return m_lstRoles;
    }
}
