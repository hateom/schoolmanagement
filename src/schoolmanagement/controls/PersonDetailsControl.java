/*
 * PersonDetailsControl.java
 *
 * Created on 20 kwiecień 2008, 15:32
 */

package schoolmanagement.controls;

import java.util.List;
import schoolmanagement.controller.DBAccess;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmRole;

/**
 *
 * @author  deely
 */
public class PersonDetailsControl extends javax.swing.JPanel {
    
    /** Creates new form PersonDetailsControl */
    public PersonDetailsControl() {
        initComponents();
    }
    
    public void readDBFields()
    {
        List<SmRole> list = DBAccess.GetInstance().GetRoles();
        jcbPickGroupForDetails.removeAllItems();
        for( SmRole role : list ) {
            jcbPickGroupForDetails.addItem(role);
        }
    }
    
    ///getters and setters
    
    public void fillFields( SmPerson person )
    {
        setAddress( person.getPerAdress() );
        setFirstName( person.getPerName() );
        setSurname( person.getPerSurname() );
        if(person.getPerNip() != null)
            setNip(person.getPerNip());
        setPesel(person.getPerPesel());
        if(person.getPerPhone() != null )
            setPhoneNumber(person.getPerPhone());
        setEmail(person.getPerEmail());
        setLogin( DBAccess.GetInstance().getUserByPerson(person).getUsrLogin());
        setGroupRole( DBAccess.GetInstance().getUserByPerson(person).getUsrRolId() );
    }
    
    public void readFields( SmPerson person )
    {
        person.setPerAdress(getAddress());
        person.setPerName(getFirstName());
        person.setPerSurname(getSurname());
        if( !getNip().trim().equals("") ) 
            person.setPerNip(getNip());
        else
            person.setPerNip(null);
        person.setPerPesel(getPesel());
        if(!getPhoneNumber().trim().equals(""))
            person.setPerPhone(getPhoneNumber());
        else
            person.setPerPhone(null);
        person.setPerEmail(getEmail());
        DBAccess.GetInstance().getUserByPerson(person).setUsrRolId(getGroupRole());
    }
    
    public void LoginRO( boolean ro )
    {
        jtbLogin.enableInputMethods(ro);
    }
    
    public void setGroup( String gr )
    {
        jcbPickGroupForDetails.setSelectedItem( gr );
    }
    
    public void setGroupRole( SmRole gr )
    {
        jcbPickGroupForDetails.setSelectedItem( gr );
    }
    
    public String getGroup()
    {
        return ((SmRole)(jcbPickGroupForDetails.getSelectedItem())).getRolName();
    }
    
    public SmRole getGroupRole()
    {
        return ((SmRole)(jcbPickGroupForDetails.getSelectedItem()));
    }
    
    public String getSurname()
    {
        return jtbSurnameNameDetails.getText();
    } 
    
    public String getFirstName()
    {
        return jtbNameDetails.getText();
    }
    
    public String getPesel()
    {
        return jtbPeselForDetail.getText();
    }
    
    public String getNip()
    {
        return jtbNipForDetail.getText();
    }
    
    public String getAddress()
    {
        return jtbAddressDetail.getText();
    }
    
    public String getPhoneNumber()
    {
        return jtbPhoneDetail.getText();
    }
    
    public void setPhoneNumber ( String a_strValue )
    {
        jtbPhoneDetail.setText(a_strValue);
    }
    
    public void setAddress ( String a_strValue )
    {
        jtbAddressDetail.setText(a_strValue);
    }
    
    public void setFirstName ( String a_strValue )
    {
        jtbNameDetails.setText(a_strValue);
    }
    
    public void setSurname ( String a_strValue )
    {
        jtbSurnameNameDetails.setText(a_strValue);
    }
    
    public void setNip ( String a_strValue )
    {
        jtbNipForDetail.setText(a_strValue);
    }
    
    public void setPesel ( String a_strValue )
    {
        jtbPeselForDetail.setText(a_strValue);
    }
    
    public void setGroups (Object[] a_arrObjects)
    {
        jcbPickGroupForDetails.removeAllItems();
        for(int i=0; i<a_arrObjects.length; ++i)
        {
           jcbPickGroupForDetails.addItem(a_arrObjects[i]); 
        }
    }
    
    public void addGroup(Object a_oGroup)
    {
        jcbPickGroupForDetails.addItem(a_oGroup);
    }
    
    public void removeGroup(Object a_oGroup)
    {
        jcbPickGroupForDetails.removeItem(a_oGroup);
    }
    
    public void setLoginVisible( boolean v )
    {
        jtbLogin.setVisible(v);
        jLabel2.setVisible(v);
    }
     
    public void setRoleVisible( boolean v )
    {
        jcbPickGroupForDetails.setVisible(v);
        jLabel16.setVisible(v);
    }
    
    public void setLogin( String login )
    {
        jtbLogin.setText( login );
    }
    
    public String getLogin()
    {
        return jtbLogin.getText();
    }
    
    public void setEmail( String email )
    {
        jtbEmail.setText(email);
    }
    
    public String getEmail()
    {
        return jtbEmail.getText();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtbPeselForDetail = new javax.swing.JTextField();
        jtbSurnameNameDetails = new javax.swing.JTextField();
        jtbNameDetails = new javax.swing.JTextField();
        jtbAddressDetail = new javax.swing.JTextField();
        jtbPhoneDetail = new javax.swing.JTextField();
        jtbNipForDetail = new javax.swing.JTextField();
        jcbPickGroupForDetails = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtbEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jtbLogin = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setName("Form"); // NOI18N

        jtbPeselForDetail.setName("jtbPeselForDetail"); // NOI18N

        jtbSurnameNameDetails.setName("jtbSurnameNameDetails"); // NOI18N

        jtbNameDetails.setName("jtbNameDetails"); // NOI18N

        jtbAddressDetail.setName("jtbAddressDetail"); // NOI18N

        jtbPhoneDetail.setName("jtbPhoneDetail"); // NOI18N

        jtbNipForDetail.setName("jtbNipForDetail"); // NOI18N

        jcbPickGroupForDetails.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dyrektor", "Nauczyciel", "Uczeń", "Admin" }));
        jcbPickGroupForDetails.setName("jcbPickGroupForDetails"); // NOI18N

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Grupa:"); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel23.setText("NIP:"); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Telefon:"); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel25.setText("PESEL:"); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Adres:"); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Imię:"); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nazwisko:"); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(PersonDetailsControl.class);
        jtbEmail.setText(resourceMap.getString("jtbEmail.text")); // NOI18N
        jtbEmail.setName("jtbEmail"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jtbLogin.setText(resourceMap.getString("jtbLogin.text")); // NOI18N
        jtbLogin.setName("jtbLogin"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addComponent(jLabel25))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtbLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
                            .addComponent(jtbEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jtbPhoneDetail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jtbNameDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jtbSurnameNameDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jtbPeselForDetail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jtbAddressDetail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jtbNipForDetail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbPickGroupForDetails, 0, 188, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtbSurnameNameDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtbNameDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jtbAddressDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jtbPeselForDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 20, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtbPhoneDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtbNipForDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtbEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtbLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbPickGroupForDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JComboBox jcbPickGroupForDetails;
    private javax.swing.JTextField jtbAddressDetail;
    private javax.swing.JTextField jtbEmail;
    private javax.swing.JTextField jtbLogin;
    private javax.swing.JTextField jtbNameDetails;
    private javax.swing.JTextField jtbNipForDetail;
    private javax.swing.JTextField jtbPeselForDetail;
    private javax.swing.JTextField jtbPhoneDetail;
    private javax.swing.JTextField jtbSurnameNameDetails;
    // End of variables declaration//GEN-END:variables
    
}
