/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package schoolmanagement.observers;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 *
 * @author jasiu
 */
public class ErrorObserver implements Observer{

    public void update(Observable o, Object arg) 
    {
       JOptionPane.showMessageDialog(null, arg, "Błąd", JOptionPane.ERROR_MESSAGE);
    }

}
