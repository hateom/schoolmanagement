/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author deely
 */
public class ErrorLogger extends Observable{
    static private List<String> errorList = new ArrayList<String>();;
    
    static ErrorLogger instance = null;
    
    private ErrorLogger()
    {
    }
    
    static public ErrorLogger getInstance()
    {
        if(instance == null)
            instance = new ErrorLogger();
        return instance;
    }
    
    public int getCount()
    {
        return errorList.size();
    }
    public String getError(int no)
    {
        if( no < 0 || no >= getCount()) return "";
        return errorList.get(no);
    }
    public void error( String strError )
    {
        super.notifyObservers(strError);
        errorList.add(strError);
    }
    public boolean errorReported()
    {
        return getCount() > 0;
    }
    public void removeAll()
    {
        errorList.removeAll(errorList);
    }
    public String getLast()
    {
        return errorList.get(getCount()-1);
    }
    
    // observable methods
}
