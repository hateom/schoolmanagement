/*
 * JMessageDialog.java
 *
 * Created on 21 maj 2008, 17:28
 */

package schoolmanagement.dialogs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import schoolmanagement.controller.ErrorLogger;
import schoolmanagement.entity.SmMessage;
import schoolmanagement.entity.SmUser;

/**
 *
 * @author  deely
 */
public class JMessageDialog extends javax.swing.JFrame {
    private SmMessage m_message;
    private SmUser m_sender;
    /** Creates new form JMessageDialog */
    public JMessageDialog( SmMessage msg ) {
        initComponents();
        m_message = msg;
        
        setSender( msg.getMsgSenderUsrId() );
        setSubject(m_message.getMsgTopic());
        setDate(m_message.getMsgSendDate());
        setBody(m_message.getMsgBody());
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
        jtbSender = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtbSubject = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtbDate = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtBody = new javax.swing.JTextArea();
        jbtnReply = new javax.swing.JButton();
        jbtnForward = new javax.swing.JButton();

        setName("Form"); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Wiadomosc"));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });

        jLabel1.setText("Nadawca:"); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jtbSender.setBackground(new java.awt.Color(204, 255, 204));
        jtbSender.setEditable(false);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(JMessageDialog.class);
        jtbSender.setText(resourceMap.getString("jtbSender.text")); // NOI18N
        jtbSender.setName("jtbSender"); // NOI18N

        jLabel2.setText("Temat:"); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtbSubject.setEditable(false);
        jtbSubject.setText(resourceMap.getString("jtbSubject.text")); // NOI18N
        jtbSubject.setName("jtbSubject"); // NOI18N

        jLabel3.setText("Wyslano:"); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jtbDate.setEditable(false);
        jtbDate.setText(resourceMap.getString("jtbDate.text")); // NOI18N
        jtbDate.setName("jtbDate"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jtBody.setColumns(20);
        jtBody.setEditable(false);
        jtBody.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jtBody.setRows(5);
        jtBody.setName("jtBody"); // NOI18N
        jScrollPane1.setViewportView(jtBody);

        jbtnReply.setText("Odpowiedz"); // NOI18N
        jbtnReply.setName("jbtnReply"); // NOI18N

        jbtnForward.setText("Przeslij dalej"); // NOI18N
        jbtnForward.setName("jbtnForward"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtbDate)
                            .addComponent(jtbSubject)
                            .addComponent(jtbSender, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(389, Short.MAX_VALUE)
                        .addComponent(jbtnForward, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jbtnReply, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtbSender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jtbSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtbDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnForward)
                    .addComponent(jbtnReply)))
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

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown
        setSender( m_message.getMsgSenderUsrId() );
    }//GEN-LAST:event_jPanel1ComponentShown
    
    public void setSender( SmUser sender )
    {
        m_sender = sender;
        jtbSender.setText( sender.getUsrPerId().toString() );
    }
    
    public void setSubject( String subject )
    {
        jtbSubject.setText(subject);
    }
    
    public void setBody( String body )
    {
        jtBody.setText(body);
    }
    
    public void setDate( Date date )
    {
        DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        jtbDate.setText( dfm.format(date) );
    }
    
    public Date getDate()
    {
        try {
            DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = dfm.parse(jtbDate.getText());
            return date;
        } catch (ParseException ex) {
            ErrorLogger.error("Date conversion error!");
        }
        
        return null;
    }
    
    public SmUser getSender()
    {
        return m_sender;
    }
    
    public String getSubject()
    {
        return jtbSubject.getText();
    }
    
    public String getBody()
    {
        return jtBody.getText();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JMessageDialog( null ).setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtnForward;
    private javax.swing.JButton jbtnReply;
    private javax.swing.JTextArea jtBody;
    private javax.swing.JTextField jtbDate;
    private javax.swing.JTextField jtbSender;
    private javax.swing.JTextField jtbSubject;
    // End of variables declaration//GEN-END:variables
    
}