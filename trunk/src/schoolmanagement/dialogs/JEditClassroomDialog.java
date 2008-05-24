/*
 * JEditClassroomDialog.java
 *
 * Created on 22 maj 2008, 18:55
 */

package schoolmanagement.dialogs;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import schoolmanagement.controller.DBAccess;
import schoolmanagement.controller.RoleType;
import schoolmanagement.entity.SmClassroom;
import schoolmanagement.entity.SmPerson;

/**
 *
 * @author  deely
 */
public class JEditClassroomDialog extends javax.swing.JFrame {
    
    private SmClassroom m_class;
    private SmPerson m_host;
    
    private JTable m_table;
    private int m_row;
    
    /** Creates new form JEditClassroomDialog */
    public JEditClassroomDialog( SmClassroom cr, JTable target, int row ) {
        initComponents();
        
        m_table = target;
        m_row = row;
        m_class = cr;
        
        setNo(cr.getClrId());
        setDesc(cr.getClrDescr());
        setHost(cr.getClrOwnerPerId());
    }
    
    public void setNo( int no )
    {
        jtbNo.setText( Integer.toString(no) );
    }
    
    public void setDesc( String desc )
    {
        jtbDesc.setText( desc );
    }
    
    public void setHost( SmPerson host )
    {
        m_host = host;
        if( m_host != null )
            jtbHost.setText( m_host.toString() );
        else
            jtbHost.setText( "" );
    }
    
    public int getNo()
    {
        return Integer.parseInt(jtbNo.getText());
    }
    
    public String getDesc()
    {
        return jtbDesc.getText();
    }
    
    public SmPerson getHost()
    {
        return m_host;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtbNo = new javax.swing.JTextField();
        jtbDesc = new javax.swing.JTextField();
        jtbHost = new javax.swing.JTextField();
        jBtnSelect = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jbtnSave = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();

        setLocationByPlatform(true);
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Edycja sali")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText("Numer:"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Sala:"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("Opiekun:"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(JEditClassroomDialog.class);
        jtbNo.setText(resourceMap.getString("jtbNo.text")); // NOI18N
        jtbNo.setName("jtbNo"); // NOI18N

        jtbDesc.setText(resourceMap.getString("jtbDesc.text")); // NOI18N
        jtbDesc.setName("jtbDesc"); // NOI18N

        jtbHost.setBackground(resourceMap.getColor("jtbHost.background")); // NOI18N
        jtbHost.setEditable(false);
        jtbHost.setText(resourceMap.getString("jtbHost.text")); // NOI18N
        jtbHost.setName("jtbHost"); // NOI18N

        jBtnSelect.setText("Wybierz..."); // NOI18N
        jBtnSelect.setName("jBtnSelect"); // NOI18N
        jBtnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSelectActionPerformed(evt);
            }
        });

        jLabel4.setForeground(resourceMap.getColor("jLabel4.foreground")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtbNo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addContainerGap())
                    .addComponent(jtbDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jtbHost, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnSelect))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtbNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtbDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jBtnSelect)
                    .addComponent(jtbHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jbtnSave.setText("Zapisz"); // NOI18N
        jbtnSave.setName("jbtnSave"); // NOI18N
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jbtnCancel.setText("Anuluj"); // NOI18N
        jbtnCancel.setName("jbtnCancel"); // NOI18N
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                        .addComponent(jbtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnCancel)
                    .addComponent(jbtnSave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jbtnCancelActionPerformed

    private void jBtnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSelectActionPerformed
        JSelectPersonByRoleDialog sp = new JSelectPersonByRoleDialog(this, true, DBAccess.GetInstance().getRoleByType(RoleType.ROLE_TEACHER));
        sp.setLocationRelativeTo(null);
        sp.setVisible(true);
        SmPerson result = sp.getResult();
        if( result == null ) return;
        
        setHost(result );
    }//GEN-LAST:event_jBtnSelectActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        DefaultTableModel tm = (DefaultTableModel)m_table.getModel();
        
        tm.setValueAt(m_class, m_row, 0);
        tm.setValueAt(m_class.getClrDescr(), m_row, 1);
        
        DBAccess.GetInstance().updateClassroom( m_class, getHost() );
        
        setVisible(false);
    }//GEN-LAST:event_jbtnSaveActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JEditClassroomDialog(null, null, 0).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JTextField jtbDesc;
    private javax.swing.JTextField jtbHost;
    private javax.swing.JTextField jtbNo;
    // End of variables declaration//GEN-END:variables
    
}
