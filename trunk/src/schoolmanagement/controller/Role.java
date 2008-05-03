/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

/**
 *
 * @author deely
 */
public class Role 
{
    private String role_name;
    private RoleType role_type;
    
    public Role( String name, RoleType type ) {
        role_name = name;
        role_type = type;
    }
    
    public RoleType GetRoleType() {
        return role_type;
    }
    
    public String GetRoleName() {
        return role_name;
    }
    
    @Override
    public String toString()
    {
        return role_name;
    }
}
