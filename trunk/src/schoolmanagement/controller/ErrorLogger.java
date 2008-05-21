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
    static String getError(int no)
    {
        if( no < 0 || no >= getCount()) return "";
        return errorList.get(no);
    }
    static void error( String strError )
    {
        errorList.add(strError);
    }
    boolean errorReported()
    {
        return getCount() > 0;
    }
    void removeAll()
    {
        errorList.removeAll(errorList);
    }
}
