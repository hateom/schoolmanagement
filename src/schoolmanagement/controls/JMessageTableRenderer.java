/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.controls;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import schoolmanagement.entity.SmMessage;

/**
 *
 * @author deely
 */
public class JMessageTableRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
        
        SmMessage msg = (SmMessage)table.getModel().getValueAt(row, 1);
        if( msg instanceof SmMessage )
        {
            if( msg.getMsgReaded() == false )
            {
                Font newFont = cell.getFont().deriveFont( Font.BOLD );
                cell.setFont( newFont );
            }
            else
            {
                Font newFont = cell.getFont().deriveFont( Font.PLAIN );
                cell.setFont( newFont );
            }
            
            if( isSelected ) 
                cell.setBackground( Color.LIGHT_GRAY );
            else
            {
                if( msg.getMsgSeverity() == 0 )
                {
                    cell.setBackground(Color.YELLOW);
                }
                else
                {
                    cell.setBackground( Color.white );
                }
            }
        }

        return cell;
    }
}
