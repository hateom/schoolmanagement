/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deely
 */
public class ErrorLogger {
    static private List<String> errorList = new ArrayList<String>();;
    static public int getCount()
    {
        return errorList.size();
    }
    static public String getError(int no)
    {
        if( no < 0 || no >= getCount()) return "";
        return errorList.get(no);
    }
    static public void error( String strError )
    {
        errorList.add(strError);
    }
    static public boolean errorReported()
    {
        return getCount() > 0;
    }
    static public void removeAll()
    {
        errorList.removeAll(errorList);
    }
    static public String getLast()
    {
        return errorList.get(getCount()-1);
    }
}
