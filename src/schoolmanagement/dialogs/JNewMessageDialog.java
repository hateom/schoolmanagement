/*
 * JNewMessageDialog.java
 *
 * Created on 21 maj 2008, 17:32
 */

package schoolmanagement.dialogs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import schoolmanagement.controller.User;
import schoolmanagement.controller.DBAccess;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmUser;

/**
 *
 * @author  deely
 */
public class JNewMessageDialog extends javax.swing.JFrame {
    
    List<SmPerson> m_receivers;
    
    public void addReceiver( SmPerson rec )
    {
        if( m_receivers == null ) m_receivers = new ArrayList<SmPerson>();
        m_receivers.add(rec);
    }
    
    public void updateReceiversText()
    {
        String recv = "";
            
        if( m_receivers == null ) return;
        for( SmPerson p : m_receivers )
        {
            if( recv.compareTo("") != 0 ) {
                recv += ", ";
            }
            recv += p.toString();
        }

        jtbReceiver.setText(recv);
    }
    
    /** Creates new form JNewMessageDialog */
    public JNewMessageDialog() {
        initComponents();
        m_receivers = null;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtbReceiver = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtbSubject = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jbtSearch = new javax.swing.JButton();
        jbtSend = new javax.swing.JButton();
        jbtCancel = new javax.swing.JButton();
        jcbPriority = new javax.swing.JCheckBox();

        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Wiadomosc"));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText("Do:"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jtbReceiver.setBackground(new java.awt.Color(204, 255, 204));
        jtbReceiver.setEditable(false);
        jtbReceiver.setName("jtbReceiver"); // NOI18N

        jLabel2.setText("Temat:"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtbSubject.setName("jtbSubject"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jbtSearch.setText("Szukaj"); // NOI18N
        jbtSearch.setName("jbtSearch"); // NOI18N
        jbtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSearchActionPerformed(evt);
            }
        });

        jbtSend.setText("Wyslij"); // NOI18N
        jbtSend.setName("jbtSend"); // NOI18N
        jbtSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSendActionPerformed(evt);
            }
        });

        jbtCancel.setText("Anuluj"); // NOI18N
        jbtCancel.setName("jbtCancel"); // NOI18N
        jbtCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelActionPerformed(evt);
            }
        });

        jcbPriority.setText("Oznacz te wiadomosc jako wazna (odbiorca dostanie powiadomienie)"); // NOI18N
        jcbPriority.setName("jcbPriority"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtbReceiver, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtbSubject, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                            .addComponent(jcbPriority))
                        .addGap(83, 83, 83)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jbtSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jbtSearch)
                            .addComponent(jtbReceiver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtbSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtCancel)))
                .addGap(3, 3, 3)
                .addComponent(jcbPriority)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSearchActionPerformed
        JSelectPersonDialog spd = new JSelectPersonDialog( this, true );
        spd.setLocationRelativeTo(null);
        spd.setVisible(true);
        
        List<SmPerson> list;
        
        if( ( list = spd.getResult() ) != null ) {
            m_receivers = list;
            
            updateReceiversText();
        }
    }//GEN-LAST:event_jbtSearchActionPerformed

    private void jbtCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jbtCancelActionPerformed

    private void jbtSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSendActionPerformed
        if( m_receivers == null ) return;
        SmUser me;
        SmUser rec;
        
        me = DBAccess.GetInstance().getUserByPerson(User.GetUserPerson());
        
        for( SmPerson p : m_receivers )
        {
            rec = DBAccess.GetInstance().getUserByPerson(p);
            if( rec == null ) continue;
            
            int prt = 2;
            if( jcbPriority.isSelected() ) prt = 0;
            DBAccess.GetInstance().sendMessage(rec, me, jTextArea1.getText(), jtbSubject.getText(), false, prt );
        }
        
        setVisible(false);
    }//GEN-LAST:event_jbtSendActionPerformed
    
   
    public void setSubject( String subject )
    {
        jtbSubject.setText(subject);
    }
    
    public void setBody( String body )
    {
        jTextArea1.setText(body);
    }
    
  
    public String getSubject()
    {
        return jtbSubject.getText();
    }
    
    public String getBody()
    {
        return jTextArea1.getText();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JNewMessageDialog().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton jbtCancel;
    private javax.swing.JButton jbtSearch;
    private javax.swing.JButton jbtSend;
    private javax.swing.JCheckBox jcbPriority;
    private javax.swing.JTextField jtbReceiver;
    private javax.swing.JTextField jtbSubject;
    // End of variables declaration//GEN-END:variables
    
}
