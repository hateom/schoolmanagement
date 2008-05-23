/*
 * JEditNotesDialog.java
 *
 * Created on 23 maj 2008, 17:29
 */

package schoolmanagement.dialogs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import schoolmanagement.controller.User;
import schoolmanagement.controller.DBAccess;
import schoolmanagement.controller.ErrorLogger;
import schoolmanagement.entity.SmClass;
import schoolmanagement.entity.SmNote;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmSubject;

/**
 *
 * @author  deely
 */
public class JEditNotesDialog extends javax.swing.JFrame {
    
    private SmPerson  m_person;
    private SmClass   m_class;
    private SmSubject m_subject;
    
    private JTable m_table;
    private int m_row;
    
    /** Creates new form JEditNotesDialog */
    public JEditNotesDialog( SmPerson person, SmClass cls, SmSubject subject, JTable target, int row ) {
        initComponents();
        
        m_person = person;
        m_class = cls;
        m_subject = subject;
        
        m_table = target;
        m_row = row;
        
        jLblStudentName.setText(m_person.toString());
        jLblSubjectName.setText(m_subject.getSubName());
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
        jLblStudentName = new javax.swing.JLabel();
        jBtnAdd = new javax.swing.JButton();
        jBtnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblNotes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLblSubjectName = new javax.swing.JLabel();
        jBtnClose = new javax.swing.JButton();

        setName("Form"); // NOI18N
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(JEditNotesDialog.class);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLblStudentName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblStudentName.setText(resourceMap.getString("jLblStudentName.text")); // NOI18N
        jLblStudentName.setName("jLblStudentName"); // NOI18N

        jBtnAdd.setText("Dodaj ocene"); // NOI18N
        jBtnAdd.setName("jBtnAdd"); // NOI18N
        jBtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddActionPerformed(evt);
            }
        });

        jBtnDelete.setText("Usun ocene"); // NOI18N
        jBtnDelete.setName("jBtnDelete"); // NOI18N
        jBtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDeleteActionPerformed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jtblNotes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Ocena", "Adnotacja"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblNotes.setName("jtblNotes"); // NOI18N
        jtblNotes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblNotesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblNotes);

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLblSubjectName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblSubjectName.setText(resourceMap.getString("jLblSubjectName.text")); // NOI18N
        jLblSubjectName.setName("jLblSubjectName"); // NOI18N

        jBtnClose.setText("Zamknij"); // NOI18N
        jBtnClose.setName("jBtnClose"); // NOI18N
        jBtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLblStudentName)
                .addGap(143, 143, 143)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLblSubjectName)
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBtnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(jBtnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLblStudentName)
                    .addComponent(jLabel2)
                    .addComponent(jLblSubjectName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnDelete)
                    .addComponent(jBtnAdd)
                    .addComponent(jBtnClose)))
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

    private void jBtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddActionPerformed
        JAddNoteDialog an = new JAddNoteDialog(this, true, null );
        an.setVisible(true);
        
        if( !an.isSaved() ) return;
        
        SmNote note;
        SmPerson teacher = User.GetUserPerson();
        if( teacher == null )
        {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        note = DBAccess.GetInstance().addNote( teacher, m_subject, m_person, m_class, an.getNote(), an.getDesc() );
        if( note == null )
        {
            ErrorLogger.getInstance().error( "Could not add note!");
        }
        else
        {
            reloadNotes();
        }
    }//GEN-LAST:event_jBtnAddActionPerformed

    private void jBtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDeleteActionPerformed
        DefaultTableModel tm = (DefaultTableModel) jtblNotes.getModel();
        
        int [] rows = jtblNotes.getSelectedRows();
        for( int row : rows )
        {
            SmNote note = (SmNote)tm.getValueAt( row, 1 );
            if( !DBAccess.GetInstance().deleteNote(note) )
            {
                ErrorLogger.getInstance().error("Could not delete note!");
            }
        }
        
        reloadNotes();
    }//GEN-LAST:event_jBtnDeleteActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        reloadNotes();
    }//GEN-LAST:event_formComponentShown

    private void jtblNotesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblNotesMouseClicked
        if( evt.getClickCount() != 2 ) return;
        DefaultTableModel tm = (DefaultTableModel) jtblNotes.getModel();
        
        int row = jtblNotes.getSelectedRow();
        SmNote note = (SmNote)tm.getValueAt( row, 1 );
        if( note == null ) return;
        
        JAddNoteDialog an = new JAddNoteDialog(this, true, note);
        an.setVisible(true);
        
        if( an.isSaved() == false ) return;
        if( DBAccess.GetInstance().saveNote( an.getResult() ) == false )
        {
            ErrorLogger.getInstance().error("Could not save note!");
        }
    }//GEN-LAST:event_jtblNotesMouseClicked

    private void jBtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCloseActionPerformed
        
        setVisible(false);
    }//GEN-LAST:event_jBtnCloseActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JEditNotesDialog( null, null, null, null, 0 ).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAdd;
    private javax.swing.JButton jBtnClose;
    private javax.swing.JButton jBtnDelete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLblStudentName;
    private javax.swing.JLabel jLblSubjectName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtblNotes;
    // End of variables declaration//GEN-END:variables

    private void reloadNotes() {
        DefaultTableModel tm = (DefaultTableModel) jtblNotes.getModel();
        
        while( tm.getRowCount() > 0 ) tm.removeRow(0);
        
        List<SmNote> list = DBAccess.GetInstance().getNotes( m_person, m_class, m_subject );
        
        for( SmNote note : list )
        {
            DateFormat dfm = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            tm.addRow(new Object[] { dfm.format(note.getNotDate()), note, note.getNotComment() });
        }
    }
    
}
