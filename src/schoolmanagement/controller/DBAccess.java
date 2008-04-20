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

class ConnectThread implements Runnable {
    Thread t;

    public ConnectThread() {
        t = new Thread( this, "DBConnector" );
    }
    
    public void start() {
        t.start();
    }
    
    public void run() {
        DBAccess.DoConnection();
    }
}
public class DBAccess {
    static private DataAccess da = null;
    static private ConnectThread ct = null;
    
    static public boolean DoConnection() {
        if( da != null ) return true;
        da = new DataAccess();
        return true;
    }
    
    static public boolean Connect() {
        if( da != null ) return true;
        if( ct != null && ct.t.isAlive() ) return false;
        
        if( !IsConnected() ) {
            ct = new ConnectThread();
            synchronized( ct ) {
                ct.start();
            }
        }
        
        return true;
    }
    
    static public boolean IsConnected() {
        return da != null;
    }
    
    static public void Dispose() {
        da.dispose();
        da = null;
    }
    
    static public DataAccess GetInstance() {
        return da;
    }
}
