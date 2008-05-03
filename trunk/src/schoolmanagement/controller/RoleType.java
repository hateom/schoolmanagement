/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package schoolmanagement.controller;

/**
 *
 * @author deely
 */
public enum RoleType {

    ROLE_UNDEFINED, ROLE_ADMIN, ROLE_PRINCIPAL, ROLE_TEACHER, ROLE_STUDENT;

    static RoleType GetRoleType(int db_role) {
        switch (db_role) {
            case 1:
                return ROLE_ADMIN;
            case 2:
                return ROLE_PRINCIPAL;
            case 3:
                return ROLE_TEACHER;
            case 4:
                return ROLE_STUDENT;
            default:
                return ROLE_UNDEFINED;
        }
    }
}
