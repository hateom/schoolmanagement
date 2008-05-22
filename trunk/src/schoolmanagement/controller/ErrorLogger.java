/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import schoolmanagement.dialogs.JErrorLoggerDialog;

/**
 *
 * @author deely
 */
public class ErrorLogger extends Observable{
    static private List<String> errorList = new ArrayList<String>();;
    private JErrorLoggerDialog errorDlg;
    
    static ErrorLogger instance = null;
    
    private ErrorLogger()
    {
        errorDlg = new JErrorLoggerDialog();
    }
    
    static public ErrorLogger getInstance()
    {
        if(instance == null)
            instance = new ErrorLogger();
        return instance;
    }
    
    public void showWindow( boolean flag )
    {
        errorDlg.setVisible(flag);
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
        errorDlg.reloadErrors();
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
