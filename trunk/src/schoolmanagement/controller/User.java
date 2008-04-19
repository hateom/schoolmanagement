/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

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
    
    static boolean Login( String user, String password )
    {
        String passmd5 = Crypto.MD5Sum(password);
        user_name = user;
        user_id = 0; //
        is_logged = true;
        user_role = new Role( "", RoleType.GetRoleType( 0 ) );
        return true;
    }
    
    static void Logout()
    {
        is_logged = false;
        user_id = -1;
        user_name = "";
    }
    
    static boolean isLogged()
    {
        return is_logged;
    }
    
    static Role GetRole() {
        return user_role;
    }
    
    static String GetUserName() {
        return user_name;
    }
    
    static int GetUserId() {
        return user_id;
    }
}
