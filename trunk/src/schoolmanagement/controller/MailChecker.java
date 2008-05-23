/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controller;

import java.util.List;
import java.util.TimerTask;
import schoolmanagement.dialogs.JNewMailMessageDialog;
import schoolmanagement.entity.SmMessage;
import schoolmanagement.entity.SmUser;

/**
 *
 * @author deely
 */
public class MailChecker extends TimerTask {

    @Override
    public void run() {
        SmUser user = DBAccess.GetInstance().getUserByPerson(User.GetUserPerson());
        if( user == null || User.IsLogged() == false ) return;
        List<SmMessage> list = DBAccess.GetInstance().getRecievedMessages( user, null, false );
        
        for( SmMessage msg : list )
        {
            if( msg.getMsgReaded() == false && msg.getMsgSeverity() < 1 )
            {
                JNewMailMessageDialog nm = new JNewMailMessageDialog(null, true, msg);
                nm.setVisible(true);
            }
        }
    }

}
