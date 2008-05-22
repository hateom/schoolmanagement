/*
 * JEditClassDialog.java
 *
 * Created on 22 maj 2008, 12:44
 */

package schoolmanagement.dialogs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import schoolmanagement.controller.DBAccess;
import schoolmanagement.controller.ErrorLogger;
import schoolmanagement.controller.RoleType;
import schoolmanagement.entity.SmClass;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmRole;

/**
 *
 * @author  deely
 */
public class JEditClassDialog extends javax.swing.JFrame {
    SmClass m_class;
    /** Creates new form JEditClassDialog */
    public JEditClassDialog( SmClass iclass ) {
        initComponents();
        m_class = iclass;
        realodDB();
        
        setClassName( m_class.getClsNumber(), m_class.getClsNumberAlph(), m_class.getClsDescription() );
        setTutor( m_class.getClsPerId() );
    }
    
    public void realodDB()
    {
        List<SmPerson> list = DBAccess.GetInstance().GetUserByRole(RoleType.ROLE_TEACHER);
        
        jCbTutor.removeAllItems();
        for( SmPerson tch : list )
        {
            jCbTutor.addItem( tch );
        }
        
        list = DBAccess.GetInstance().GetPersonsForClass(m_class);
        
        DefaultTableModel dtm = (DefaultTableModel)jTblStudents.getModel();
        
        while( dtm.getRowCount() > 0 ) dtm.removeRow(0);
        
        for( SmPerson st : list )
        {
            dtm.addRow(new Object[] { st });
        }
    }
    
    public void setClassName( int nclass, String subClass, String desc )
    {
        jTbClass.setText( Integer.toString(nclass) );
        jTbClassExt.setText( subClass );
        jtbClassDesc.setText( desc );
    }
    
    public int getClassNum()
    {
        return Integer.parseInt(jTbClass.getText());
    }
    
    public String getSubClass()
    {
        return jTbClassExt.getText();
    }
    
    public String getClassDesc()
    {
        return jtbClassDesc.getText();
    }
    
    public void setTutor( SmPerson tut )
    {
        jCbTutor.setSelectedItem( tut );
    }
    
    public SmPerson getTutor()
    {
        return (SmPerson)jCbTutor.getSelectedItem();
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
        jTbClass = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jCbTutor = new javax.swing.JComboBox();
        jTbClassExt = new javax.swing.JTextField();
        jtbClassDesc = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblStudents = new javax.swing.JTable();
        kbtSchedule = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jBtnAdd = new javax.swing.JButton();
        jBtnDelete = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Edycja klasy")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(JEditClassDialog.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTbClass.setBackground(resourceMap.getColor("jTbClass.background")); // NOI18N
        jTbClass.setText(resourceMap.getString("jTbClass.text")); // NOI18N
        jTbClass.setName("jTbClass"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jCbTutor.setName("jCbTutor"); // NOI18N

        jTbClassExt.setText(resourceMap.getString("jTbClassExt.text")); // NOI18N
        jTbClassExt.setName("jTbClassExt"); // NOI18N

        jtbClassDesc.setText(resourceMap.getString("jtbClassDesc.text")); // NOI18N
        jtbClassDesc.setName("jtbClassDesc"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Uczen"
            }
        ));
        jTblStudents.setName("jTblStudents"); // NOI18N
        jScrollPane1.setViewportView(jTblStudents);

        kbtSchedule.setText("Plan zajec..."); // NOI18N
        kbtSchedule.setName("kbtSchedule"); // NOI18N

        jbtnSave.setText("Zapisz zmiany"); // NOI18N
        jbtnSave.setName("jbtnSave"); // NOI18N
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jBtnAdd.setText("Dodaj osobe do klasy"); // NOI18N
        jBtnAdd.setName("jBtnAdd"); // NOI18N
        jBtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddActionPerformed(evt);
            }
        });

        jBtnDelete.setText(resourceMap.getString("jBtnDelete.text")); // NOI18N
        jBtnDelete.setName("jBtnDelete"); // NOI18N
        jBtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCbTutor, 0, 449, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTbClass, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTbClassExt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtbClassDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jbtnSave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kbtSchedule, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jBtnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTbClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTbClassExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtbClassDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jCbTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnSave)
                    .addComponent(jBtnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kbtSchedule)
                    .addComponent(jBtnAdd)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
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

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        m_class.setClsNumber( getClassNum() );
        m_class.setClsNumberAlph( getSubClass() );
        m_class.setClsDescription(getClassDesc());
        m_class.setClsPerId( getTutor() );
      
        DBAccess.GetInstance().saveChanges(m_class);
        setVisible(false);
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void jBtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddActionPerformed
        SmRole role = DBAccess.GetInstance().getRoleByType( RoleType.ROLE_STUDENT );
        JSelectPersonByRoleDialog sp = new JSelectPersonByRoleDialog( this, true, role );
        SmPerson pers;
        if( ( pers = sp.getResult() ) == null ) return;
        
        DefaultTableModel tm = (DefaultTableModel)jTblStudents.getModel();
        tm.addRow( new Object[] { pers } );
        
        DBAccess.GetInstance().addPersonToClass(m_class, pers);
    }//GEN-LAST:event_jBtnAddActionPerformed

    private void jBtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDeleteActionPerformed
        DefaultTableModel tm = (DefaultTableModel)jTblStudents.getModel();
        int [] rows = jTblStudents.getSelectedRows();
        Arrays.sort( rows );
        SmPerson pers;
        for( int i=rows.length-1; i>=0; --i )
        {
            pers = (SmPerson)tm.getValueAt( rows[i], 0 );
            if( pers != null )
            {
                if( !DBAccess.GetInstance().removePersonFromClass(m_class, pers) )
                {
                    ErrorLogger.error( "Could not rmove person from class!" );
                }
            }
            tm.removeRow( rows[i] );
        }
    }//GEN-LAST:event_jBtnDeleteActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JEditClassDialog( null ).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAdd;
    private javax.swing.JButton jBtnDelete;
    private javax.swing.JComboBox jCbTutor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTbClass;
    private javax.swing.JTextField jTbClassExt;
    private javax.swing.JTable jTblStudents;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JTextField jtbClassDesc;
    private javax.swing.JButton kbtSchedule;
    // End of variables declaration//GEN-END:variables
    
}
