/*
 * JAddLessonDialog.java
 *
 * Created on 22 maj 2008, 21:27
 */

package schoolmanagement.dialogs;

import java.util.List;
import schoolmanagement.controller.DBAccess;
import schoolmanagement.entity.SmClassroom;
import schoolmanagement.entity.SmDay;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmRing;
import schoolmanagement.entity.SmSchedule;
import schoolmanagement.entity.SmSubject;
import schoolmanagement.entity.SmTeacher;

/**
 *
 * @author  deely
 */
public class JAddLessonDialog extends javax.swing.JDialog {
    
    private SmTeacher m_teacher;
    private SmClassroom m_classroom;
    private SmDay m_day;
    private SmRing m_ring;
    private SmSchedule m_lesson;
    private SmPerson m_person;
    private SmSubject m_subject;
    
    /** Creates new form JAddLessonDialog */
    public JAddLessonDialog(java.awt.Frame parent, boolean modal, SmDay day, SmRing ring, SmSchedule lesson ) {
        super(parent, modal);
        initComponents();
        
        m_teacher = null;
        m_classroom = null;
        m_ring = ring;
        m_day = day;
        m_lesson = lesson;
        
        if( m_lesson != null) {
            setPerson( m_lesson.getSchTchId().getTchPerId() );
            setSubject( m_lesson.getSchTchId().getTchSubId() );
            setClassroom( m_lesson.getSchClrId() );
        }
    }
    
    public SmClassroom getClassroom()
    {
        return m_classroom;
    }
    
    public SmTeacher getTeacher()
    {
        return m_teacher;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jcbSubjects = new javax.swing.JComboBox();
        jcbTeachers = new javax.swing.JComboBox();
        jcbRooms = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbtnCancel = new javax.swing.JButton();
        jbtnSave = new javax.swing.JButton();

        setLocationByPlatform(true);
        setName("Form"); // NOI18N
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dodaj lekcje")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jcbSubjects.setName("jcbSubjects"); // NOI18N
        jcbSubjects.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSubjectsActionPerformed(evt);
            }
        });

        jcbTeachers.setName("jcbTeachers"); // NOI18N
        jcbTeachers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTeachersActionPerformed(evt);
            }
        });

        jcbRooms.setName("jcbRooms"); // NOI18N

        jLabel1.setText("Przedmiot:"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(JAddLessonDialog.class);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcbSubjects, javax.swing.GroupLayout.Alignment.TRAILING, 0, 241, Short.MAX_VALUE)
                    .addComponent(jcbTeachers, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbRooms, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbTeachers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbRooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)))
        );

        jbtnCancel.setText("Anuluj"); // NOI18N
        jbtnCancel.setName("jbtnCancel"); // NOI18N
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        jbtnSave.setText("Zapisz"); // NOI18N
        jbtnSave.setName("jbtnSave"); // NOI18N
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
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
                        .addComponent(jbtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                        .addComponent(jbtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnCancelActionPerformed

    
    
    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed
        if( m_lesson != null )
        {
            //m_lesson = DBAccess.GetInstance().addSchedule(m_day, m_class, m_ring, m_teacher, m_classroom );
        }
        else
        {
            // create new lesson
        }
        setVisible(false);
    }//GEN-LAST:event_jbtnSaveActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        jcbSubjects.removeAllItems();
        List<SmSubject> list = DBAccess.GetInstance().getSubjectsList();
        
        for(SmSubject sb : list )
        {
            jcbSubjects.addItem(sb);
        }
    }//GEN-LAST:event_formComponentShown

    private void jcbSubjectsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSubjectsActionPerformed
        m_subject = (SmSubject) jcbSubjects.getSelectedItem();
        List<SmTeacher> list = DBAccess.GetInstance().getAvailableTeachers(m_subject, m_day, m_ring);
        jcbTeachers.removeAllItems();
        
        if( list == null ) return;
        
        for( SmTeacher t : list )
        {
            jcbTeachers.addItem(t);
        }
    }//GEN-LAST:event_jcbSubjectsActionPerformed

    private void jcbTeachersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTeachersActionPerformed
        List<SmClassroom> list = DBAccess.GetInstance().getAvailableClassrooms(m_day, m_ring);
        
        jcbRooms.removeAllItems();
        
        for(SmClassroom cl : list )
        {
            jcbRooms.addItem(cl);
        }
    }//GEN-LAST:event_jcbTeachersActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JAddLessonDialog dialog = new JAddLessonDialog(new javax.swing.JFrame(), true, null, null, null );
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox jcbRooms;
    private javax.swing.JComboBox jcbSubjects;
    private javax.swing.JComboBox jcbTeachers;
    // End of variables declaration//GEN-END:variables

    private void setClassroom(SmClassroom schClrId) {
        m_classroom = schClrId;
        jcbRooms.setSelectedItem(schClrId);
    }
    // End of variables declaration

    private void setPerson(SmPerson tchPerId) {
        m_person = tchPerId;
        jcbTeachers.setSelectedItem( tchPerId );
    }

    private void setSubject(SmSubject tchSubId) {
        m_subject = tchSubId;
        jcbSubjects.setSelectedItem(tchSubId);
    }
    
}
