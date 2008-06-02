/*
 * JSchedulePreviewDialog.java
 *
 * Created on 28 maj 2008, 17:09
 */

package schoolmanagement.dialogs;

/**
 *
 * @author  deely
 */
public class JSchedulePreviewDialog extends javax.swing.JFrame {
    
    /** Creates new form JSchedulePreviewDialog */
    public JSchedulePreviewDialog( String subject, String room, String teacher ) {
        initComponents();
        
        jLblSubject.setText(subject);
        jLblTeacher.setText(teacher);
        jLblRoom.setText(room);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLblSubject = new javax.swing.JLabel();
        jLblTeacher = new javax.swing.JLabel();
        jLblRoom = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(JSchedulePreviewDialog.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(102, 102, 102));
        setName("Form"); // NOI18N
        setResizable(false);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setName("jPanel1"); // NOI18N

        jLblSubject.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblSubject.setText(resourceMap.getString("jLblSubject.text")); // NOI18N
        jLblSubject.setName("jLblSubject"); // NOI18N

        jLblTeacher.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblTeacher.setText(resourceMap.getString("jLblTeacher.text")); // NOI18N
        jLblTeacher.setName("jLblTeacher"); // NOI18N

        jLblRoom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLblRoom.setText(resourceMap.getString("jLblRoom.text")); // NOI18N
        jLblRoom.setName("jLblRoom"); // NOI18N

        jLabel4.setText("Przedmiot:"); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("Nauczyciel:"); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("Sala:"); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblRoom)
                    .addComponent(jLblTeacher)
                    .addComponent(jLblSubject))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblSubject)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTeacher)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblRoom)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JSchedulePreviewDialog( "", "", "" ).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLblRoom;
    private javax.swing.JLabel jLblSubject;
    private javax.swing.JLabel jLblTeacher;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}