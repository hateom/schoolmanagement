/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import schoolmanagement.DataAccess.DataAccess;

/**
 *
 * @author deely
 */
public class DBAccess {
    static private DataAccess da = null;
    
    static public boolean Connect() {
        if( da != null ) return true;
        da = new DataAccess();
        return true;
    }
    
    static public boolean IsConnected() {
        return da != null;
    }
    
    static public void Dsipose() {
        da.dispose();
        da = null;
    }
}
