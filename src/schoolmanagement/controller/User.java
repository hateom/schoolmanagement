/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmUser;

/**
 *
 * @author deely
 */
public class User 
{
    static private String   user_name;
    static private int      user_id;
    static private boolean  is_logged;
    static private Role     user_role;
    static private SmPerson user_person;
    
    static public boolean Login( String user, String password )
    {
        //String passmd5 = Crypto.MD5Sum(password);
        if( !DBAccess.IsConnected() ) return false;
        SmUser usr = DBAccess.GetInstance().loginUser(user, password);
        if( usr == null ) return false;
        
        user_name = user;
        user_id = usr.getUsrId(); //
        is_logged = true;
        user_role = new Role( usr.getUsrRolId().getRolName(), RoleType.GetRoleType( usr.getUsrRolId().getRolId() ) );
        user_person = usr.getUsrPerId();
        
        return true;
    }
    
    static public void Logout()
    {
        is_logged = false;
        user_id = -1;
        user_name = "";
        user_person = null;
    }
    
    static public boolean IsLogged()
    {
        return is_logged;
    }
    
    static public Role GetRole() {
        return user_role;
    }
    
    static public String GetUserName() {
        return user_name;
    }
    
    static public int GetUserId() {
        return user_id;
    }
    
    static public SmPerson GetUserPerson() {
        return user_person;
    }
}
