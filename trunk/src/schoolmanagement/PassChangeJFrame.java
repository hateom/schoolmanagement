/*
 * PassChangeJFrame.java
 *
 * Created on 20 kwiecień 2008, 15:02
 */

package schoolmanagement;

/**
 *
 * @author  deely
 */
public class PassChangeJFrame extends javax.swing.JFrame {
    
    /** Creates new form PassChangeJFrame */
    public PassChangeJFrame() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelPasswdChange = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jpfCurrent = new javax.swing.JPasswordField();
        jpfNew = new javax.swing.JPasswordField();
        jpfNew2 = new javax.swing.JPasswordField();
        jbtnCancel = new javax.swing.JButton();
        jbtnChangePassword = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);

        jpanelPasswdChange.setBorder(javax.swing.BorderFactory.createTitledBorder("Zmiana hasła"));
        jpanelPasswdChange.setName("jpanelPasswdChange"); // NOI18N

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Aktualne hasło:"); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Nowe hasło:"); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Nowe hasło (powtórnie):"); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jpfCurrent.setName("jpfCurrent"); // NOI18N

        jpfNew.setName("jpfNew"); // NOI18N

        jpfNew2.setName("jpfNew2"); // NOI18N

        javax.swing.GroupLayout jpanelPasswdChangeLayout = new javax.swing.GroupLayout(jpanelPasswdChange);
        jpanelPasswdChange.setLayout(jpanelPasswdChangeLayout);
        jpanelPasswdChangeLayout.setHorizontalGroup(
            jpanelPasswdChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelPasswdChangeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelPasswdChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelPasswdChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpfNew2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(jpfNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(jpfCurrent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)))
        );
        jpanelPasswdChangeLayout.setVerticalGroup(
            jpanelPasswdChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelPasswdChangeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelPasswdChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpfCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelPasswdChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(8, 8, 8)
                .addGroup(jpanelPasswdChangeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfNew2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(40, 40, 40))
        );

        jbtnCancel.setText("Anuluj"); // NOI18N
        jbtnCancel.setName("jbtnCancel"); // NOI18N
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        jbtnChangePassword.setText("Zmień hasło"); // NOI18N
        jbtnChangePassword.setName("jbtnChangePassword"); // NOI18N
        jbtnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnChangePasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpanelPasswdChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanelPasswdChange, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnChangePassword)
                    .addComponent(jbtnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnCancelActionPerformed

    private void jbtnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnChangePasswordActionPerformed
        //jpfCurrent;
        //jpfNew;
        //jpfNew2;// TODO add your handling code here:
        
        dispose();
    }//GEN-LAST:event_jbtnChangePasswordActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PassChangeJFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnChangePassword;
    private javax.swing.JPanel jpanelPasswdChange;
    private javax.swing.JPasswordField jpfCurrent;
    private javax.swing.JPasswordField jpfNew;
    private javax.swing.JPasswordField jpfNew2;
    // End of variables declaration//GEN-END:variables
    
}
