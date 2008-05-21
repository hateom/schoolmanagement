/*
 * JEditTeacherDialog.java
 *
 * Created on 21 maj 2008, 15:11
 */

package schoolmanagement.dialogs;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmSubject;
import schoolmanagement.controller.DBAccess;
import schoolmanagement.controller.ErrorLogger;
import sun.security.jca.GetInstance;

/**
 *
 * @author  deely
 */
public class JEditTeacherDialog extends javax.swing.JFrame {
    
    private SmPerson m_person;

    /** Creates new form JEditTeacherDialog */
    public JEditTeacherDialog( SmPerson person ) {
        m_person = person;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpnSubjects = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlAll = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlTought = new javax.swing.JList();
        jbtnMove = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jbtnRemoveSelected = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();
        jpnClasses = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setName("Form"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jpnSubjects.setBorder(javax.swing.BorderFactory.createTitledBorder("Wybierz liste uczonych przedmiotow")); // NOI18N
        jpnSubjects.setName("jpnSubjects"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jlAll.setName("jlAll"); // NOI18N
        jScrollPane1.setViewportView(jlAll);

        jLabel1.setText("Przedmioty:"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Uczone przedmioty:"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jlTought.setName("jlTought"); // NOI18N
        jScrollPane2.setViewportView(jlTought);

        jbtnMove.setText(">>"); // NOI18N
        jbtnMove.setName("jbtnMove"); // NOI18N
        jbtnMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnMoveActionPerformed(evt);
            }
        });

        jSeparator1.setName("jSeparator1"); // NOI18N

        jbtnRemoveSelected.setText("Usun zaznaczone"); // NOI18N
        jbtnRemoveSelected.setName("jbtnRemoveSelected"); // NOI18N
        jbtnRemoveSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRemoveSelectedActionPerformed(evt);
            }
        });

        jbtnSave.setText("Zapisz zmiany"); // NOI18N
        jbtnSave.setName("jbtnSave"); // NOI18N
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnSubjectsLayout = new javax.swing.GroupLayout(jpnSubjects);
        jpnSubjects.setLayout(jpnSubjectsLayout);
        jpnSubjectsLayout.setHorizontalGroup(
            jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnSubjectsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addGroup(jpnSubjectsLayout.createSequentialGroup()
                        .addGroup(jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(jbtnMove)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addGroup(jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnSubjectsLayout.createSequentialGroup()
                        .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                        .addComponent(jbtnRemoveSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpnSubjectsLayout.setVerticalGroup(
            jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnSubjectsLayout.createSequentialGroup()
                .addGroup(jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnSubjectsLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jpnSubjectsLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jbtnMove)
                        .addGap(152, 152, 152)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnRemoveSelected)
                    .addComponent(jbtnSave))
                .addContainerGap())
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(JEditTeacherDialog.class);
        jTabbedPane1.addTab(resourceMap.getString("jpnSubjects.TabConstraints.tabTitle"), jpnSubjects); // NOI18N

        jpnClasses.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jpnClasses.border.title"))); // NOI18N
        jpnClasses.setName("jpnClasses"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Klasa", "Przedmiot"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jpnClassesLayout = new javax.swing.GroupLayout(jpnClasses);
        jpnClasses.setLayout(jpnClassesLayout);
        jpnClassesLayout.setHorizontalGroup(
            jpnClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnClassesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpnClassesLayout.setVerticalGroup(
            jpnClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnClassesLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab(resourceMap.getString("jpnClasses.TabConstraints.tabTitle"), jpnClasses); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
         
         updateLists();
    }//GEN-LAST:event_formComponentShown

    public void updateLists()
    {
        List<SmSubject> subs = DBAccess.GetInstance().getSubjectsList();
        
        jlAll.removeAll();
         
         DefaultListModel lm = new DefaultListModel();
         jlAll.setModel(lm);
        
         for( SmSubject sb : subs )
         {
             lm.addElement(sb);
         }
         
         lm = new DefaultListModel();
         jlTought.setModel(lm);
         
         List<SmSubject> tcht = DBAccess.GetInstance().getSubjectsForPerson(m_person);
         
         for( SmSubject sb : tcht )
         {
             lm.addElement(sb);
         }
    }
    
    private void jbtnMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnMoveActionPerformed
        int [] list = jlAll.getSelectedIndices();
        for( int k: list ) {
            SmSubject sb = (SmSubject)jlAll.getModel().getElementAt(k);
            if( !DBAccess.GetInstance().teacherHasSubject(m_person, sb)) {
                ((DefaultListModel)jlTought.getModel()).addElement( sb );
            }
        }
    }//GEN-LAST:event_jbtnMoveActionPerformed

    private void jbtnRemoveSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRemoveSelectedActionPerformed
        int [] list = jlTought.getSelectedIndices();
        for( int k: list ) {
            SmSubject sb = (SmSubject)((DefaultListModel)jlTought.getModel()).getElementAt(k);
            boolean removeTeachersSubject = DBAccess.GetInstance().removeTeachersSubject(m_person, sb);
            if(!removeTeachersSubject) 
                ErrorLogger.error("Could not add teachers subject!");
        }
        updateLists();
    }//GEN-LAST:event_jbtnRemoveSelectedActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        for( int k=0; k<jlTought.getModel().getSize(); ++k ) {
            SmSubject sb = (SmSubject)jlAll.getModel().getElementAt(k);
            if( !DBAccess.GetInstance().teacherHasSubject(m_person, sb)) {
                DBAccess.GetInstance().addTeachersSubject(m_person, sb);
            }
        }
    }//GEN-LAST:event_jbtnSaveActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JEditTeacherDialog( null ).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jbtnMove;
    private javax.swing.JButton jbtnRemoveSelected;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JList jlAll;
    private javax.swing.JList jlTought;
    private javax.swing.JPanel jpnClasses;
    private javax.swing.JPanel jpnSubjects;
    // End of variables declaration//GEN-END:variables
    
}
