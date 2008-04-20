/*
 * MainJFrame.java
 *
 * Created on 20 kwiecień 2008, 15:20
 */
package schoolmanagement;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author  deely
 */
public class MainJFrame extends javax.swing.JFrame {

    /** Creates new form MainJFrame */
    public MainJFrame() {
        initComponents();
        PrepareTree();
    }

    public void PrepareTree() {
        Object[] hierarchy = {
            "SchoolManagement",
            new Object[] { 
                "Szkola",
                "Osoby",
                "Oceny",
                "Poczta"
            },
            "Profil",
            "Opcje"
        };

        DefaultMutableTreeNode root = processHierarchy(hierarchy);

        jTree = new javax.swing.JTree(root);
        //jTree.setRootVisible(false);
        jTree.setName("jTree"); // NOI18N
        jScrollPane1.setViewportView(jTree);
        
        jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeValueChanged(evt);
            }
        });
    }

    private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
        DefaultMutableTreeNode node =
                new DefaultMutableTreeNode(hierarchy[0]);
        DefaultMutableTreeNode child;
        for (int i = 1; i < hierarchy.length; i++) {
            Object nodeSpecifier = hierarchy[i];
            if (nodeSpecifier instanceof Object[]) // Ie node with children
            {
                child = processHierarchy((Object[]) nodeSpecifier);
            } else {
                child = new DefaultMutableTreeNode(nodeSpecifier);
            } // Ie Leaf
            node.add(child);
        }
        return (node);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jStatus = new javax.swing.JPanel();
        jLblLogText = new javax.swing.JLabel();
        jLblLoggedAs = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        jLayers = new javax.swing.JLayeredPane();
        jPnlProfile = new javax.swing.JPanel();
        jPnlPeople = new javax.swing.JPanel();
        jPnlNotes = new javax.swing.JPanel();
        jPnlMail = new javax.swing.JPanel();
        jPnlOptions = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        jStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jStatus.setName("jStatus"); // NOI18N

        jLblLogText.setText("Zalogowany jako:"); // NOI18N
        jLblLogText.setName("jLblLogText"); // NOI18N

        jLblLoggedAs.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(MainJFrame.class);
        jLblLoggedAs.setText(resourceMap.getString("jLblLoggedAs.text")); // NOI18N
        jLblLoggedAs.setName("jLblLoggedAs"); // NOI18N

        javax.swing.GroupLayout jStatusLayout = new javax.swing.GroupLayout(jStatus);
        jStatus.setLayout(jStatusLayout);
        jStatusLayout.setHorizontalGroup(
            jStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblLogText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblLoggedAs)
                .addContainerGap(497, Short.MAX_VALUE))
        );
        jStatusLayout.setVerticalGroup(
            jStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jStatusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblLogText)
                    .addComponent(jLblLoggedAs))
                .addContainerGap())
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTree.setName("jTree"); // NOI18N
        jTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree);

        jLayers.setName("jLayers"); // NOI18N

        jPnlProfile.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPnlProfile.border.title"))); // NOI18N
        jPnlProfile.setName("jPnlProfile"); // NOI18N

        javax.swing.GroupLayout jPnlProfileLayout = new javax.swing.GroupLayout(jPnlProfile);
        jPnlProfile.setLayout(jPnlProfileLayout);
        jPnlProfileLayout.setHorizontalGroup(
            jPnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        jPnlProfileLayout.setVerticalGroup(
            jPnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jPnlProfile.setBounds(0, 0, 440, 480);
        jLayers.add(jPnlProfile, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlPeople.setBorder(javax.swing.BorderFactory.createTitledBorder("Osoby")); // NOI18N
        jPnlPeople.setName("jPnlPeople"); // NOI18N

        javax.swing.GroupLayout jPnlPeopleLayout = new javax.swing.GroupLayout(jPnlPeople);
        jPnlPeople.setLayout(jPnlPeopleLayout);
        jPnlPeopleLayout.setHorizontalGroup(
            jPnlPeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        jPnlPeopleLayout.setVerticalGroup(
            jPnlPeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jPnlPeople.setBounds(0, 0, 440, 480);
        jLayers.add(jPnlPeople, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlNotes.setBorder(javax.swing.BorderFactory.createTitledBorder("Oceny")); // NOI18N
        jPnlNotes.setName("jPnlNotes"); // NOI18N

        javax.swing.GroupLayout jPnlNotesLayout = new javax.swing.GroupLayout(jPnlNotes);
        jPnlNotes.setLayout(jPnlNotesLayout);
        jPnlNotesLayout.setHorizontalGroup(
            jPnlNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        jPnlNotesLayout.setVerticalGroup(
            jPnlNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jPnlNotes.setBounds(0, 0, 440, 480);
        jLayers.add(jPnlNotes, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlMail.setBorder(javax.swing.BorderFactory.createTitledBorder("Poczta")); // NOI18N
        jPnlMail.setName("jPnlMail"); // NOI18N

        javax.swing.GroupLayout jPnlMailLayout = new javax.swing.GroupLayout(jPnlMail);
        jPnlMail.setLayout(jPnlMailLayout);
        jPnlMailLayout.setHorizontalGroup(
            jPnlMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        jPnlMailLayout.setVerticalGroup(
            jPnlMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jPnlMail.setBounds(0, 0, 440, 480);
        jLayers.add(jPnlMail, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Opcje")); // NOI18N
        jPnlOptions.setName("jPnlOptions"); // NOI18N

        javax.swing.GroupLayout jPnlOptionsLayout = new javax.swing.GroupLayout(jPnlOptions);
        jPnlOptions.setLayout(jPnlOptionsLayout);
        jPnlOptionsLayout.setHorizontalGroup(
            jPnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );
        jPnlOptionsLayout.setVerticalGroup(
            jPnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        jPnlOptions.setBounds(0, 0, 440, 480);
        jLayers.add(jPnlOptions, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayers, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayers, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeValueChanged
        TreePath tp = jTree.getSelectionPath();
        String obj = tp.getLastPathComponent().toString();
        if( obj.compareTo("Opcje") == 0 ) {
            jLayers.moveToFront(jPnlOptions);
        } else if( obj.compareTo("Profil") == 0 ) {
            jLayers.moveToFront(jPnlProfile);
        } else if( obj.compareTo("Oceny") == 0 ) {
            jLayers.moveToFront(jPnlNotes);
        } else if( obj.compareTo("Osoby") == 0 ) {
            jLayers.moveToFront(jPnlPeople);
        } else if( obj.compareTo("Poczta") == 0 ) {
            jLayers.moveToFront(jPnlMail);
        }
    }//GEN-LAST:event_jTreeValueChanged
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayers;
    private javax.swing.JLabel jLblLogText;
    private javax.swing.JLabel jLblLoggedAs;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPnlMail;
    private javax.swing.JPanel jPnlNotes;
    private javax.swing.JPanel jPnlOptions;
    private javax.swing.JPanel jPnlPeople;
    private javax.swing.JPanel jPnlProfile;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jStatus;
    private javax.swing.JTree jTree;
    // End of variables declaration//GEN-END:variables
}
