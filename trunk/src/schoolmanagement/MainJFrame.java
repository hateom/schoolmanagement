/*
 * MainJFrame.java
 *
 * Created on 20 kwiecień 2008, 15:20
 */
package schoolmanagement;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import schoolmanagement.observers.ErrorObserver;
import schoolmanagement.controller.*;
import schoolmanagement.controls.JMessageTableRenderer;
import schoolmanagement.dialogs.*;
import schoolmanagement.entity.SmClass;
import schoolmanagement.entity.SmPerson;
import schoolmanagement.entity.SmRing;
import schoolmanagement.entity.SmSubject;
import schoolmanagement.entity.SmTeacher;
import schoolmanagement.entity.SmUser;
import schoolmanagement.dialogs.JNewMessageDialog;
import schoolmanagement.entity.SmClassroom;
import schoolmanagement.entity.SmDay;
import schoolmanagement.entity.SmDay;
import schoolmanagement.entity.SmMessage;
import schoolmanagement.entity.SmNote;
import schoolmanagement.entity.SmSchedule;
import schoolmanagement.controller.Commander;

/**
 *
 * @author  deely
 */
public class MainJFrame extends javax.swing.JFrame implements Commander {

    static private MainJFrame instance;
    static public MainJFrame getInstance() {
        return instance;
    }
    
    static void setInstance( MainJFrame inst ) {
        instance = inst;
    }
    
    /** Creates new form MainJFrame */
    public MainJFrame() {
        initComponents();
        Thread.setDefaultUncaughtExceptionHandler( ErrorLogger.getInstance() );
        ErrorLogger.getInstance().addObserver(new ErrorObserver());
        PrepareTree();
        jTbRole.removeAllItems();
        jTbClass.removeAllItems();
        List<Role> lstRole = ConstantData.GetRoles();
        for( Role role : lstRole)
        {
            jTbRole.addItem(role);
        }
        List<SmClass> lstClass = DBAccess.GetInstance().GetAllClasses();
        for( SmClass smclass : lstClass)
        {
            jTbClass.addItem(smclass);
        }
        jLblLoggedAs.setText(User.GetUserPerson().getPerName() + " " + User.GetUserPerson().getPerSurname());
        
        Timer timer = new Timer();
        timer.schedule( new MailChecker(), 3000, 60000 );
    }

    public void PrepareTree() {
        Object[] hierarchy = {
            "SchoolManagement",
            new Object[] { 
                "Szkoła",
                "Osoby",
                "Nauczyciele",
                "Oceny",
                "Plan Zajęc",
                "Klasy",
                "Sale",
                "Dzwonki",
                "Przedmioty"
            },
            new Object[] {
                "Poczta",
                "Odebrane",
                "Wysłane"
                },
            "Profil"
            //"Opcje"
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
        
        ShowLayer( "Profil" );
        expandAll( jTree, new TreePath(jTree.getModel().getRoot()), true );
    }

    private String compileNotes(SmPerson pupil, SmClass cls, SmSubject subject) {
        List<SmNote> list = DBAccess.GetInstance().getNotes(pupil, cls, subject);
        String result = "";
        for( SmNote note : list )
        {
            if( result.compareTo("") != 0 ) result += ", ";
            result += note.getNotNote();
        }
        
        return result;
    }

    private void doSearchPeople() {
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        
        while( model.getRowCount() > 0 ) model.removeRow(0);
        
        List<SmPerson> list = new ArrayList<SmPerson>();
        if(jRbSurname.isSelected()){
            list = DBAccess.GetInstance().GetUserByName(jTbName.getText().trim());
        }
        else if(jRbRole.isSelected()){
            list = DBAccess.GetInstance().GetUserByRole(((Role)jTbRole.getSelectedItem()).GetRoleType());

        }
        else if(jRbClass.isSelected()){
            list = DBAccess.GetInstance().GetPersonsForClass((SmClass)jTbClass.getSelectedItem());
        }
        else{
            throw new RuntimeException();
        }
        
        for(SmPerson person : list){
            model.addRow(new Object[]{person, DBAccess.GetInstance().getUserRole(person)});
        }
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
    
        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    private void loadScheduleFor(SmClass selectedItem) {
        DefaultTableModel tm = (DefaultTableModel) jtblScheldue.getModel();
        
        while( tm.getRowCount() > 0 ) tm.removeRow(0);
        
        List<SmRing> rings = DBAccess.GetInstance().getRings();
        List<SmDay> days = DBAccess.GetInstance().getAllDays();
        
        int column = 1;
        int row = 0;
        for( SmRing ring : rings )
        {
            tm.addRow(new Object[] {ring, null, null, null, null, null });
        }
        
        for(SmDay day : days )
        {
            List<SmSchedule> list = DBAccess.GetInstance().getSchedule( day, selectedItem );
            if( list == null ) {
                column++;
                continue;
            }
            row = 0;
            for( SmRing ring : rings )
            {
                for( SmSchedule lesson : list )
                {
                    if( lesson.getSchRngId() == ring )
                    {
                        tm.setValueAt(lesson.getSchSubId(), row, column);
                    }
                    else
                    {
                        // zostawiamy puste pole jak nie ma lekcji
                    }
                }
                row++;
            }
            column++;
            if( column > 5 ) break;
        }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jStatus = new javax.swing.JPanel();
        jLblLogText = new javax.swing.JLabel();
        jLblLoggedAs = new javax.swing.JLabel();
        jbtnShowConsole = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree = new javax.swing.JTree();
        jLayers = new javax.swing.JLayeredPane();
        jPnlProfile = new javax.swing.JPanel();
        jBtnSave = new javax.swing.JButton();
        jBtnPassCHange = new javax.swing.JButton();
        personDetailsControl1 = new schoolmanagement.controls.PersonDetailsControl();
        jSeparator1 = new javax.swing.JSeparator();
        jPnlNotes = new javax.swing.JPanel();
        jpanelProporties = new javax.swing.JPanel();
        jcbPickSubjectForNotes = new javax.swing.JComboBox();
        jcbPickClassForNotes = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtblPupilNotes = new javax.swing.JTable();
        jPnlMail = new javax.swing.JPanel();
        jBtnNewMail = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPnlOptions = new javax.swing.JPanel();
        jPnlSchedule = new javax.swing.JPanel();
        jpanelClassSelect = new javax.swing.JPanel();
        jcbSelectClass = new javax.swing.JComboBox();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jtblScheldue = new javax.swing.JTable();
        jbtnEdit = new javax.swing.JButton();
        jbtnReload = new javax.swing.JButton();
        jPnlInbox = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JSeparator();
        jBtnNewMail3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlReceived = new javax.swing.JTable();
        jbtReceive = new javax.swing.JButton();
        jbtnDeleteMsg = new javax.swing.JButton();
        jPnlOutbox = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jBtnNewMail2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jlSent = new javax.swing.JTable();
        jbtnDelSentmsg = new javax.swing.JButton();
        jPnlPeople = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jRbSurname = new javax.swing.JRadioButton();
        jRbRole = new javax.swing.JRadioButton();
        jRbClass = new javax.swing.JRadioButton();
        jTbName = new javax.swing.JTextField();
        jTbRole = new javax.swing.JComboBox();
        jTbClass = new javax.swing.JComboBox();
        jBtnSearch = new javax.swing.JButton();
        jBtnAddPerson = new javax.swing.JButton();
        jBtnDeletePerson = new javax.swing.JButton();
        jPnlRings = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblRings = new javax.swing.JTable();
        jPnlTeachers = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTblTeachers = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jRbSurname1 = new javax.swing.JRadioButton();
        jRbSubject = new javax.swing.JRadioButton();
        jTbName1 = new javax.swing.JTextField();
        jTbSubs = new javax.swing.JComboBox();
        jBtnSearch2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPnlClasses = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jtblClasses = new javax.swing.JTable();
        jbtAddClass = new javax.swing.JButton();
        jBtnReload = new javax.swing.JButton();
        jPnlRooms = new javax.swing.JPanel();
        jbtAddRoom = new javax.swing.JButton();
        jBtnReload1 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jtblRooms = new javax.swing.JTable();
        jPnlSubjects = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jBtnDeleteSubject = new javax.swing.JButton();
        jBtnAddSubject = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jtblSubjects = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jStatus.setName("jStatus"); // NOI18N

        jLblLogText.setText("Zalogowany jako:"); // NOI18N
        jLblLogText.setName("jLblLogText"); // NOI18N

        jLblLoggedAs.setFont(new java.awt.Font("Tahoma", 1, 11));
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(schoolmanagement.SchoolmanagementApp.class).getContext().getResourceMap(MainJFrame.class);
        jLblLoggedAs.setText(resourceMap.getString("jLblLoggedAs.text")); // NOI18N
        jLblLoggedAs.setName("jLblLoggedAs"); // NOI18N

        jbtnShowConsole.setText("Pokaz okno bledow"); // NOI18N
        jbtnShowConsole.setName("jbtnShowConsole"); // NOI18N
        jbtnShowConsole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnShowConsoleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jStatusLayout = new javax.swing.GroupLayout(jStatus);
        jStatus.setLayout(jStatusLayout);
        jStatusLayout.setHorizontalGroup(
            jStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblLogText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLblLoggedAs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 345, Short.MAX_VALUE)
                .addComponent(jbtnShowConsole, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jStatusLayout.setVerticalGroup(
            jStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jStatusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblLogText)
                    .addComponent(jLblLoggedAs)
                    .addComponent(jbtnShowConsole, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        jPnlProfile.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPnlProfileComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlProfileComponentShown(evt);
            }
        });

        jBtnSave.setText("Zapisz zmiany"); // NOI18N
        jBtnSave.setName("jBtnSave"); // NOI18N
        jBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSaveActionPerformed(evt);
            }
        });

        jBtnPassCHange.setText("Zmien haslo..."); // NOI18N
        jBtnPassCHange.setName("jBtnPassCHange"); // NOI18N
        jBtnPassCHange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPassCHangeActionPerformed(evt);
            }
        });

        personDetailsControl1.setName("personDetailsControl1"); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N

        javax.swing.GroupLayout jPnlProfileLayout = new javax.swing.GroupLayout(jPnlProfile);
        jPnlProfile.setLayout(jPnlProfileLayout);
        jPnlProfileLayout.setHorizontalGroup(
            jPnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlProfileLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(personDetailsControl1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addGroup(jPnlProfileLayout.createSequentialGroup()
                        .addComponent(jBtnPassCHange, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jBtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
                .addGap(119, 119, 119))
        );
        jPnlProfileLayout.setVerticalGroup(
            jPnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlProfileLayout.createSequentialGroup()
                .addComponent(personDetailsControl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSave)
                    .addComponent(jBtnPassCHange))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jPnlProfile.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlProfile, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlNotes.setBorder(javax.swing.BorderFactory.createTitledBorder("Oceny"));
        jPnlNotes.setName("jPnlNotes"); // NOI18N
        jPnlNotes.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlNotesComponentShown(evt);
            }
        });

        jpanelProporties.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpanelProporties.setName("jpanelProporties"); // NOI18N

        jcbPickSubjectForNotes.setName("jcbPickSubjectForNotes"); // NOI18N
        jcbPickSubjectForNotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPickSubjectForNotesActionPerformed(evt);
            }
        });

        jcbPickClassForNotes.setName("jcbPickClassForNotes"); // NOI18N
        jcbPickClassForNotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPickClassForNotesActionPerformed(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        javax.swing.GroupLayout jpanelProportiesLayout = new javax.swing.GroupLayout(jpanelProporties);
        jpanelProporties.setLayout(jpanelProportiesLayout);
        jpanelProportiesLayout.setHorizontalGroup(
            jpanelProportiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelProportiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelProportiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelProportiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jcbPickClassForNotes, 0, 0, Short.MAX_VALUE)
                    .addComponent(jcbPickSubjectForNotes, 0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpanelProportiesLayout.setVerticalGroup(
            jpanelProportiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelProportiesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelProportiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbPickClassForNotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelProportiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbPickSubjectForNotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jtblPupilNotes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nazwisko ucznia", "Oceny"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblPupilNotes.setName("jtblPupilNotes"); // NOI18N
        jtblPupilNotes.getTableHeader().setReorderingAllowed(false);
        jtblPupilNotes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblPupilNotesMouseClicked(evt);
            }
        });
        jtblPupilNotes.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jtblPupilNotesPropertyChange(evt);
            }
        });
        jScrollPane7.setViewportView(jtblPupilNotes);

        javax.swing.GroupLayout jPnlNotesLayout = new javax.swing.GroupLayout(jPnlNotes);
        jPnlNotes.setLayout(jPnlNotesLayout);
        jPnlNotesLayout.setHorizontalGroup(
            jPnlNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlNotesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jpanelProporties, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPnlNotesLayout.setVerticalGroup(
            jPnlNotesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlNotesLayout.createSequentialGroup()
                .addComponent(jpanelProporties, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPnlNotes.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlNotes, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlMail.setBorder(javax.swing.BorderFactory.createTitledBorder("Poczta"));
        jPnlMail.setName("jPnlMail"); // NOI18N

        jBtnNewMail.setText("Nowa wiadomosc"); // NOI18N
        jBtnNewMail.setName("jBtnNewMail"); // NOI18N
        jBtnNewMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewMailActionPerformed(evt);
            }
        });

        jSeparator2.setName("jSeparator2"); // NOI18N

        javax.swing.GroupLayout jPnlMailLayout = new javax.swing.GroupLayout(jPnlMail);
        jPnlMail.setLayout(jPnlMailLayout);
        jPnlMailLayout.setHorizontalGroup(
            jPnlMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlMailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jBtnNewMail))
                .addContainerGap())
        );
        jPnlMailLayout.setVerticalGroup(
            jPnlMailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlMailLayout.createSequentialGroup()
                .addComponent(jBtnNewMail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(394, Short.MAX_VALUE))
        );

        jPnlMail.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlMail, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Opcje"));
        jPnlOptions.setName("jPnlOptions"); // NOI18N

        javax.swing.GroupLayout jPnlOptionsLayout = new javax.swing.GroupLayout(jPnlOptions);
        jPnlOptions.setLayout(jPnlOptionsLayout);
        jPnlOptionsLayout.setHorizontalGroup(
            jPnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );
        jPnlOptionsLayout.setVerticalGroup(
            jPnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );

        jPnlOptions.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlOptions, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlSchedule.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPnlSchedule.border.title"))); // NOI18N
        jPnlSchedule.setName("jPnlSchedule"); // NOI18N
        jPnlSchedule.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlScheduleComponentShown(evt);
            }
        });

        jpanelClassSelect.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpanelClassSelect.setName("jpanelClassSelect"); // NOI18N

        jcbSelectClass.setName("jcbSelectClass"); // NOI18N
        jcbSelectClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSelectClassActionPerformed(evt);
            }
        });

        jLabel24.setText("Klasa:"); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        javax.swing.GroupLayout jpanelClassSelectLayout = new javax.swing.GroupLayout(jpanelClassSelect);
        jpanelClassSelect.setLayout(jpanelClassSelectLayout);
        jpanelClassSelectLayout.setHorizontalGroup(
            jpanelClassSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelClassSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jcbSelectClass, 0, 337, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelClassSelectLayout.setVerticalGroup(
            jpanelClassSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelClassSelectLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelClassSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jcbSelectClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        jtblScheldue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Godzina", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblScheldue.setName("jtblScheldue"); // NOI18N
        jtblScheldue.setRowHeight(22);
        jtblScheldue.setRowSelectionAllowed(false);
        jtblScheldue.getTableHeader().setReorderingAllowed(false);
        jScrollPane8.setViewportView(jtblScheldue);

        jbtnEdit.setText("Edytuj"); // NOI18N
        jbtnEdit.setName("jbtnEdit"); // NOI18N
        jbtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditActionPerformed(evt);
            }
        });

        jbtnReload.setText("Odswiez"); // NOI18N
        jbtnReload.setName("jbtnReload"); // NOI18N
        jbtnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlScheduleLayout = new javax.swing.GroupLayout(jPnlSchedule);
        jPnlSchedule.setLayout(jPnlScheduleLayout);
        jPnlScheduleLayout.setHorizontalGroup(
            jPnlScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlScheduleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jpanelClassSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPnlScheduleLayout.createSequentialGroup()
                        .addComponent(jbtnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addComponent(jbtnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPnlScheduleLayout.setVerticalGroup(
            jPnlScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlScheduleLayout.createSequentialGroup()
                .addComponent(jpanelClassSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnEdit)
                    .addComponent(jbtnReload)))
        );

        jPnlSchedule.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlSchedule, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlInbox.setBorder(javax.swing.BorderFactory.createTitledBorder("Odebrane"));
        jPnlInbox.setName("jPnlInbox"); // NOI18N
        jPnlInbox.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlInboxComponentShown(evt);
            }
        });

        jSeparator5.setName("jSeparator5"); // NOI18N

        jBtnNewMail3.setText("Nowa wiadomosc"); // NOI18N
        jBtnNewMail3.setName("jBtnNewMail3"); // NOI18N
        jBtnNewMail3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewMail3ActionPerformed(evt);
            }
        });

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jlReceived.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Od", "Temat", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jlReceived.setName("jlReceived"); // NOI18N
        jlReceived.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inboxTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jlReceived);

        jbtReceive.setText("Odbierz"); // NOI18N
        jbtReceive.setName("jbtReceive"); // NOI18N
        jbtReceive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtReceiveActionPerformed(evt);
            }
        });

        jbtnDeleteMsg.setText("Usun"); // NOI18N
        jbtnDeleteMsg.setName("jbtnDeleteMsg"); // NOI18N
        jbtnDeleteMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteMsgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlInboxLayout = new javax.swing.GroupLayout(jPnlInbox);
        jPnlInbox.setLayout(jPnlInboxLayout);
        jPnlInboxLayout.setHorizontalGroup(
            jPnlInboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlInboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlInboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addGroup(jPnlInboxLayout.createSequentialGroup()
                        .addComponent(jBtnNewMail3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                        .addComponent(jbtReceive, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnDeleteMsg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPnlInboxLayout.setVerticalGroup(
            jPnlInboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlInboxLayout.createSequentialGroup()
                .addGroup(jPnlInboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNewMail3)
                    .addComponent(jbtReceive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDeleteMsg)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPnlInbox.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlInbox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlOutbox.setBorder(javax.swing.BorderFactory.createTitledBorder("Wyslane"));
        jPnlOutbox.setName("jPnlOutbox"); // NOI18N
        jPnlOutbox.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlOutboxComponentShown(evt);
            }
        });

        jSeparator4.setName("jSeparator4"); // NOI18N

        jBtnNewMail2.setText("Nowa wiadomosc"); // NOI18N
        jBtnNewMail2.setName("jBtnNewMail2"); // NOI18N
        jBtnNewMail2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewMail2ActionPerformed(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jlSent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Od", "Temat", "Data"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jlSent.setName("jlSent"); // NOI18N
        jlSent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                outboxTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jlSent);

        jbtnDelSentmsg.setText("Usun"); // NOI18N
        jbtnDelSentmsg.setName("jbtnDelSentmsg"); // NOI18N
        jbtnDelSentmsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDelSentmsgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlOutboxLayout = new javax.swing.GroupLayout(jPnlOutbox);
        jPnlOutbox.setLayout(jPnlOutboxLayout);
        jPnlOutboxLayout.setHorizontalGroup(
            jPnlOutboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlOutboxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlOutboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jBtnNewMail2)
                    .addComponent(jbtnDelSentmsg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPnlOutboxLayout.setVerticalGroup(
            jPnlOutboxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlOutboxLayout.createSequentialGroup()
                .addComponent(jBtnNewMail2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDelSentmsg)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jPnlOutbox.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlOutbox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlPeople.setBorder(javax.swing.BorderFactory.createTitledBorder("Osoby"));
        jPnlPeople.setName("jPnlPeople"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nazwisko i Imię", "Grupa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setName("jTable4"); // NOI18N
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MainJFrame.this.mouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable4);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setName("jPanel2"); // NOI18N

        buttonGroup1.add(jRbSurname);
        jRbSurname.setSelected(true);
        jRbSurname.setText("Nazwisko"); // NOI18N
        jRbSurname.setActionCommand(resourceMap.getString("jRbSurname.actionCommand")); // NOI18N
        jRbSurname.setName("jRbSurname"); // NOI18N

        buttonGroup1.add(jRbRole);
        jRbRole.setText("Grupa:"); // NOI18N
        jRbRole.setName("jRbRole"); // NOI18N

        buttonGroup1.add(jRbClass);
        jRbClass.setText("Klasa:"); // NOI18N
        jRbClass.setName("jRbClass"); // NOI18N

        jTbName.setName("jTbName"); // NOI18N
        jTbName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTbNameFocusGained(evt);
            }
        });

        jTbRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dyrekcja", "Nauczyciele", "Uczniowie" }));
        jTbRole.setName("jTbRole"); // NOI18N
        jTbRole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTbRoleActionPerformed(evt);
            }
        });

        jTbClass.setName("jTbClass"); // NOI18N
        jTbClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTbClassActionPerformed(evt);
            }
        });

        jBtnSearch.setText("Szukaj"); // NOI18N
        jBtnSearch.setName("jBtnSearch"); // NOI18N
        jBtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSearchActionPerformed(evt);
            }
        });

        jBtnAddPerson.setText(resourceMap.getString("jBtnAddPerson.text")); // NOI18N
        jBtnAddPerson.setName("jBtnAddPerson"); // NOI18N
        jBtnAddPerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddPersonActionPerformed(evt);
            }
        });

        jBtnDeletePerson.setText(resourceMap.getString("jBtnDeletePerson.text")); // NOI18N
        jBtnDeletePerson.setName("jBtnDeletePerson"); // NOI18N
        jBtnDeletePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDeletePersonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRbClass)
                    .addComponent(jRbSurname)
                    .addComponent(jRbRole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTbName, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(jTbRole, 0, 173, Short.MAX_VALUE)
                    .addComponent(jTbClass, 0, 173, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnDeletePerson, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnAddPerson, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRbSurname)
                    .addComponent(jTbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRbRole)
                    .addComponent(jTbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDeletePerson))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRbClass)
                    .addComponent(jTbClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAddPerson))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnlPeopleLayout = new javax.swing.GroupLayout(jPnlPeople);
        jPnlPeople.setLayout(jPnlPeopleLayout);
        jPnlPeopleLayout.setHorizontalGroup(
            jPnlPeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlPeopleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlPeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPnlPeopleLayout.setVerticalGroup(
            jPnlPeopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlPeopleLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPnlPeople.setBounds(0, 0, 440, 470);
        jLayers.add(jPnlPeople, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlRings.setBorder(javax.swing.BorderFactory.createTitledBorder("Dzwonki"));
        jPnlRings.setName("jPnlRings"); // NOI18N
        jPnlRings.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlRingsComponentShown(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTblRings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numer", "Początek lekcji", "Koniec lekcji", "Przerwa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblRings.setName("jTblRings"); // NOI18N
        jTblRings.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTblRingsPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(jTblRings);

        javax.swing.GroupLayout jPnlRingsLayout = new javax.swing.GroupLayout(jPnlRings);
        jPnlRings.setLayout(jPnlRingsLayout);
        jPnlRingsLayout.setHorizontalGroup(
            jPnlRingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlRingsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPnlRingsLayout.setVerticalGroup(
            jPnlRingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlRingsLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPnlRings.setBounds(0, 0, 440, 470);
        jLayers.add(jPnlRings, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlTeachers.setBorder(javax.swing.BorderFactory.createTitledBorder("Nauczyciele"));
        jPnlTeachers.setName("jPnlTeachers"); // NOI18N
        jPnlTeachers.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlTeachersComponentShown(evt);
            }
        });

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTblTeachers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nazwisko i Imię", "Przedmiot"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblTeachers.setName("jTblTeachers"); // NOI18N
        jTblTeachers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblTeachersMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTblTeachers);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setName("jPanel3"); // NOI18N

        buttonGroup2.add(jRbSurname1);
        jRbSurname1.setSelected(true);
        jRbSurname1.setText("Nazwisko:"); // NOI18N
        jRbSurname1.setActionCommand(resourceMap.getString("jRbSurname1.actionCommand")); // NOI18N
        jRbSurname1.setName("jRbSurname1"); // NOI18N

        buttonGroup2.add(jRbSubject);
        jRbSubject.setText("Przedmiot:"); // NOI18N
        jRbSubject.setName("jRbSubject"); // NOI18N

        jTbName1.setName("jTbName1"); // NOI18N

        jTbSubs.setName("jTbSubs"); // NOI18N

        jBtnSearch2.setText("Szukaj"); // NOI18N
        jBtnSearch2.setName("jBtnSearch2"); // NOI18N
        jBtnSearch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSearch2ActionPerformed(evt);
            }
        });

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRbSurname1)
                    .addComponent(jRbSubject))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTbSubs, 0, 165, Short.MAX_VALUE)
                    .addComponent(jTbName1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                    .addComponent(jBtnSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRbSurname1)
                    .addComponent(jBtnSearch2)
                    .addComponent(jTbName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRbSubject)
                    .addComponent(jTbSubs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnlTeachersLayout = new javax.swing.GroupLayout(jPnlTeachers);
        jPnlTeachers.setLayout(jPnlTeachersLayout);
        jPnlTeachersLayout.setHorizontalGroup(
            jPnlTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlTeachersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPnlTeachersLayout.setVerticalGroup(
            jPnlTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlTeachersLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPnlTeachers.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlTeachers, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlClasses.setBorder(javax.swing.BorderFactory.createTitledBorder("Klasy"));
        jPnlClasses.setName("jPnlClasses"); // NOI18N
        jPnlClasses.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlClassesComponentShown(evt);
            }
        });

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        jtblClasses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Klasa", "Opis"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblClasses.setName("jtblClasses"); // NOI18N
        jtblClasses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblClassesmouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jtblClasses);

        jbtAddClass.setText("Dodaj klase"); // NOI18N
        jbtAddClass.setName("jbtAddClass"); // NOI18N
        jbtAddClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAddClassActionPerformed(evt);
            }
        });

        jBtnReload.setText("Przeladuj"); // NOI18N
        jBtnReload.setName("jBtnReload"); // NOI18N
        jBtnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlClassesLayout = new javax.swing.GroupLayout(jPnlClasses);
        jPnlClasses.setLayout(jPnlClassesLayout);
        jPnlClassesLayout.setHorizontalGroup(
            jPnlClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlClassesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPnlClassesLayout.createSequentialGroup()
                        .addComponent(jBtnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addComponent(jbtAddClass, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPnlClassesLayout.setVerticalGroup(
            jPnlClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlClassesLayout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtAddClass)
                    .addComponent(jBtnReload))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPnlClasses.setBounds(0, 0, 440, 470);
        jLayers.add(jPnlClasses, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlRooms.setBorder(javax.swing.BorderFactory.createTitledBorder("Sale"));
        jPnlRooms.setName("jPnlRooms"); // NOI18N
        jPnlRooms.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlRoomsComponentShown(evt);
            }
        });

        jbtAddRoom.setText("Dodaj sale"); // NOI18N
        jbtAddRoom.setName("jbtAddRoom"); // NOI18N
        jbtAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAddRoomActionPerformed(evt);
            }
        });

        jBtnReload1.setText("Przeladuj"); // NOI18N
        jBtnReload1.setName("jBtnReload1"); // NOI18N
        jBtnReload1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReload1ActionPerformed(evt);
            }
        });

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        jtblRooms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numer sali", "Sala"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblRooms.setName("jtblRooms"); // NOI18N
        jtblRooms.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblRoomsmouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jtblRooms);

        javax.swing.GroupLayout jPnlRoomsLayout = new javax.swing.GroupLayout(jPnlRooms);
        jPnlRooms.setLayout(jPnlRoomsLayout);
        jPnlRoomsLayout.setHorizontalGroup(
            jPnlRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlRoomsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPnlRoomsLayout.createSequentialGroup()
                        .addComponent(jBtnReload1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                        .addComponent(jbtAddRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPnlRoomsLayout.setVerticalGroup(
            jPnlRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlRoomsLayout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlRoomsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtAddRoom)
                    .addComponent(jBtnReload1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPnlRooms.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlRooms, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPnlSubjects.setName("jPnlSubjects"); // NOI18N
        jPnlSubjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPnlSubjectsMouseClicked(evt);
            }
        });
        jPnlSubjects.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPnlSubjectsComponentShown(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Przedmioty"));
        jPanel1.setName("jPanel1"); // NOI18N

        jBtnDeleteSubject.setText("Usun przedmiot"); // NOI18N
        jBtnDeleteSubject.setName("jBtnDeleteSubject"); // NOI18N
        jBtnDeleteSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDeleteSubjectActionPerformed(evt);
            }
        });

        jBtnAddSubject.setText("Dodaj przedmiot"); // NOI18N
        jBtnAddSubject.setName("jBtnAddSubject"); // NOI18N
        jBtnAddSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddSubjectActionPerformed(evt);
            }
        });

        jScrollPane11.setName("jScrollPane11"); // NOI18N

        jtblSubjects.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Przedmiot"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblSubjects.setName("jtblSubjects"); // NOI18N
        jtblSubjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblSubjectsMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(jtblSubjects);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBtnAddSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(jBtnDeleteSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAddSubject)
                    .addComponent(jBtnDeleteSubject)))
        );

        javax.swing.GroupLayout jPnlSubjectsLayout = new javax.swing.GroupLayout(jPnlSubjects);
        jPnlSubjects.setLayout(jPnlSubjectsLayout);
        jPnlSubjectsLayout.setHorizontalGroup(
            jPnlSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlSubjectsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPnlSubjectsLayout.setVerticalGroup(
            jPnlSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlSubjectsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPnlSubjects.setBounds(0, 0, 440, 460);
        jLayers.add(jPnlSubjects, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
                .addComponent(jLayers, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayers, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ShowLayer( String layer ) {
        Component c = null;
         if( layer.compareTo("Opcje") == 0 ) {
            c = jPnlOptions;
        } else if( layer.compareTo("Profil") == 0 ) {
            c = jPnlProfile;
        } else if( layer.compareTo("Oceny") == 0 ) {
            c = jPnlNotes;
        } else if( layer.compareTo("Osoby") == 0 ) {
            c = jPnlPeople;
        } else if( layer.compareTo("Poczta") == 0 ) {
            c = jPnlMail;
        } else if( layer.compareTo("Odebrane") == 0 ) {
            c = jPnlInbox;
        } else if( layer.compareTo("Wysłane") == 0 ) {
            c = jPnlOutbox;
        } else if( layer.compareTo("Dzwonki") == 0 ) {
            c = jPnlRings;
        } else if( layer.compareTo("Plan Zajęc") == 0 ) {
            c = jPnlSchedule;
        } else if( layer.compareTo("Nauczyciele") == 0 ) {
            c = jPnlTeachers;
        } else if( layer.compareTo("Klasy") == 0 ) {
            c = jPnlClasses;
        } else if( layer.compareTo("Sale") == 0 ) {
            c = jPnlRooms;
        } else if( layer.compareTo("Przedmioty") == 0 ) {
            c = jPnlSubjects;
        }
        
        for( Component k: jLayers.getComponents() ) {
            k.setVisible(false);
        }
        if(c != null)
        {
            jLayers.moveToFront(c);
            c.setVisible(true);
        }
    }
    
    private void jTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeValueChanged
        
        TreePath tp = jTree.getSelectionPath();
        String obj = tp.getLastPathComponent().toString();
        ShowLayer( obj );
    }//GEN-LAST:event_jTreeValueChanged

    private void jBtnPassCHangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPassCHangeActionPerformed
        JPasswordChangeDialog jpDlg = new JPasswordChangeDialog( this, true );
        jpDlg.setLocationRelativeTo(null);
        jpDlg.setVisible(true);
    }//GEN-LAST:event_jBtnPassCHangeActionPerformed

    private void jBtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSearchActionPerformed
        doSearchPeople();
    }//GEN-LAST:event_jBtnSearchActionPerformed

    private void jPnlNotesComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlNotesComponentShown
        /*jcbPickTeacherForNotes.removeAllItems();
        List<TeacherCollection> lstSmTeacher = DBAccess.GetInstance().getTeacherList();
        for( TeacherCollection smTeacher : lstSmTeacher)
        {
            jcbPickTeacherForNotes.addItem(smTeacher);
        }*/
        
        jcbPickClassForNotes.removeAllItems();
        List<SmClass> classes = DBAccess.GetInstance().GetAllClasses();
        for( SmClass cls : classes )
        {
            jcbPickClassForNotes.addItem(cls);        
        }
    }//GEN-LAST:event_jPnlNotesComponentShown

    private void jcbPickSubjectForNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPickSubjectForNotesActionPerformed
        reloadNotes();
    }//GEN-LAST:event_jcbPickSubjectForNotesActionPerformed

    private void jcbPickClassForNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPickClassForNotesActionPerformed
        if( (SmClass)jcbPickClassForNotes.getSelectedItem() == null)
            return;     
        
        SmClass smclass = (SmClass)jcbPickClassForNotes.getSelectedItem();
        List<SmSubject> subjs = DBAccess.GetInstance().getSubjectsForClass(smclass);
        
        jcbPickSubjectForNotes.removeAllItems();
        for( SmSubject sbj : subjs )
        {
            jcbPickSubjectForNotes.addItem( sbj );
        }

    }//GEN-LAST:event_jcbPickClassForNotesActionPerformed

    RoleType getRole()
    {
        return User.GetRole().GetRoleType();
    }
    
    private void jPnlProfileComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlProfileComponentShown
        personDetailsControl1.readDBFields();
        personDetailsControl1.fillFields( User.GetUserPerson() );
        
        if( getRole() != RoleType.ROLE_ADMIN ) jBtnSave.setEnabled(false); else jBtnSave.setEnabled(true);
    }//GEN-LAST:event_jPnlProfileComponentShown

    private void jtblPupilNotesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtblPupilNotesPropertyChange
        // dziwne wartosci w evt ;/
    }//GEN-LAST:event_jtblPupilNotesPropertyChange

 class NonEditableTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    if(columnIndex==1) return true;
    else
        return false;
    }
}
    
private void jPnlRingsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlRingsComponentShown
    /*
    DefaultTableModel model = new DefaultTableModel();
        jtblPupilNotes.setModel(model);
        model.addColumn("Nazwisko");
        model.addColumn("Oceny");
                for(SmNote note : notes){
            model.addRow(new Object[]{note.getNotP2cId().getP2cPerId(), note.getNotNote()});
        }
     **/
    
    DefaultTableModel model = new NonEditableTableModel();
    jTblRings.setModel(model);
    
    model.addTableModelListener(new TableModelListener() {
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            TableModel model = (TableModel)e.getSource();
            String columnName = model.getColumnName(column);
            if(row == -1 || column == -1 || model.getValueAt(row, column) == null)
                return;
            
            
            String data = model.getValueAt(row, column).toString();
            SmRing ring = (SmRing)model.getValueAt(row, 0);
            
            //SmRing ringEnd = (SmRing)model.getValueAt(row, 2);
            //SmRing ringPrevBreak = (SmRing)model.getValueAt(row-1, 3);
            
            SimpleDateFormat ssdf = new SimpleDateFormat("HH:mm");
            long time = 0;
            Date outDate = (Date)ring.getRngTime().clone();
            try {
                time = ssdf.parse(data).getTime();
                outDate.setTime(time);
                DBAccess.GetInstance().changeRingTime(ring, outDate );
            
                //-----------------
                    
                    DefaultTableModel model2 = (DefaultTableModel)jTblRings.getModel();
                    while(model2.getRowCount()>0) model2.removeRow(0);
                            
                    List<SmRing> rings = DBAccess.GetInstance().getRings();
                    int i;
                    Date lStart = new Date();
                    Date lEnd = new Date();
                    Date lNext = new Date();
                    long ringDuration = 0;
                    int hours = 0;

                    Calendar cal = new GregorianCalendar();

                    
                        for( i=0; i<rings.size()-1; ++i )
                        {
                            hours = 0;
                            lStart = rings.get(i).getRngTime();
                            cal.setTime(lStart);
                            cal.add(Calendar.MINUTE, 45);
                            lEnd = cal.getTime();
                            lNext = rings.get(i+1).getRngTime();
                            ringDuration = (lNext.getTime() - lEnd.getTime())/(1000*60);
                            while(ringDuration >= 60){
                                hours++;
                                ringDuration -= 60;
                            }

                            Date ringDate = new GregorianCalendar(0, 0, 0, hours, (int)ringDuration).getTime();

                            cal.setTime(lStart);
                            if(cal.get(Calendar.MINUTE) ==0 && cal.get(Calendar.HOUR) ==0){
                                lEnd = new GregorianCalendar(0,0,0).getTime();
                            }
                            
                            if(ringDuration<0){
                                ringDate = new GregorianCalendar(0,0,0).getTime();
                                if(lNext.getTime()>100){
                                    JRingsOverlapping msg = new JRingsOverlapping(null, true);
                                    msg.setVisible(true);
                                }
                            }

                            model2.addRow( new Object[] { 
                               rings.get(i), //
                               dateToTimeString( lStart ),
                               dateToTimeString( lEnd ),
                               dateToTimeString( ringDate )
                            } );



                        }
                
                //--------------
            } catch( ParseException ex )
            {
            }
        }
    });
    model.addColumn("Nr");
    model.addColumn( "Początek lekcji" );
    model.addColumn( "Koniec lekcji" );
    model.addColumn( "Przerwa" );

    
    List<SmRing> rings = DBAccess.GetInstance().getRings();
    int i;
    Date lStart = new Date();
    Date lEnd = new Date();
    Date lNext = new Date();
    long ringDuration = 0;
    int hours = 0;
    
    Calendar cal = new GregorianCalendar();
    
    
        for( i=0; i<rings.size()-1; ++i )
        {
            hours = 0;
            lStart = rings.get(i).getRngTime();
            cal.setTime(lStart);
            cal.add(Calendar.MINUTE, 45);
            lEnd = cal.getTime();
            lNext = rings.get(i+1).getRngTime();
            ringDuration = (lNext.getTime() - lEnd.getTime())/(1000*60);
            while(ringDuration >= 60){
                hours++;
                ringDuration -= 60;
            }
            
            Date ringDate = new GregorianCalendar(0, 0, 0, hours, (int)ringDuration).getTime();
            
            if(ringDuration<0){
                ringDate = new GregorianCalendar(0,0,0).getTime();
            }  
            
            cal.setTime(lStart);
            if(cal.get(Calendar.MINUTE) ==0 && cal.get(Calendar.HOUR) ==0){
                lEnd = new GregorianCalendar(0,0,0).getTime();
            }

            
            model.addRow( new Object[] { 
               rings.get(i), //
               dateToTimeString( lStart ),
               dateToTimeString( lEnd ),
               dateToTimeString( ringDate )
            } );
            
            

        }
    

    
    /*i = rings.size()-1;
    model.addRow( new Object[] { 
        rings.get(i+1).getRngTime(),
        rings.get(i+1).getRngTime(),
        ""
    } );*/
}//GEN-LAST:event_jPnlRingsComponentShown

private void jBtnSearch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSearch2ActionPerformed
    reloadTeachers();
}//GEN-LAST:event_jBtnSearch2ActionPerformed

private void jPnlTeachersComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlTeachersComponentShown
    List<SmSubject> sList = DBAccess.GetInstance().getSubjectsList();
    
    jTbSubs.removeAllItems();
    for( SmSubject ss : sList )
    {
        jTbSubs.addItem( ss );
    }    // TODO add your handling code here:
}//GEN-LAST:event_jPnlTeachersComponentShown

private void jBtnAddPersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddPersonActionPerformed
    JNewPersonDialog jNewPesonDlg = new JNewPersonDialog();
    jNewPesonDlg.setLocationRelativeTo(null);
    jNewPesonDlg.setVisible(true);
}//GEN-LAST:event_jBtnAddPersonActionPerformed

private void mouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked
    // TODO add your handling code here:

    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) return;
    
    if (evt.getClickCount() == 2) {
      JTable target = (JTable)evt.getSource();
      int row = target.getSelectedRow();
      Object o = target.getValueAt(row, 0);
      if(o == null)
          return;
      SmPerson person = (SmPerson)target.getValueAt(row, 0);
      JEditPersonDialog dlg = new JEditPersonDialog( person, new Commander() {
                public void execute() {
                    doSearchPeople();
                }
            } );
      dlg.setLocationRelativeTo(null);
      dlg.setVisible(true);
   }
}//GEN-LAST:event_mouseClicked

private void jBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSaveActionPerformed
    personDetailsControl1.readFields( User.GetUserPerson() );
    SmUser user = DBAccess.GetInstance().getUserByPerson( User.GetUserPerson() );
    DBAccess.GetInstance().modifyUserAndPerson( user, User.GetUserPerson() );
    personDetailsControl1.fillFields( User.GetUserPerson() );
}//GEN-LAST:event_jBtnSaveActionPerformed

private void TblTeachersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblTeachersMouseClicked
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) return;
    
    // TODO add your handling code here:
    if (evt.getClickCount() == 2) {
      JTable target = (JTable)evt.getSource();
      int row = target.getSelectedRow();
      Object o = target.getValueAt(row, 0);
      if(o == null)
          return;
      SmPerson person = (SmPerson)target.getValueAt(row, 0);
      JEditTeacherDialog dlg = new JEditTeacherDialog( person, new Commander() {
                public void execute() {
                    reloadTeachers();
                }
      } );
      dlg.setLocationRelativeTo(null);
      dlg.setVisible(true);
      }
}//GEN-LAST:event_TblTeachersMouseClicked

private void jBtnNewMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewMailActionPerformed
    JNewMessageDialog nmd = new JNewMessageDialog();
    nmd.setLocationRelativeTo(null);
    nmd.setVisible(true);
}//GEN-LAST:event_jBtnNewMailActionPerformed

private void jBtnNewMail3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewMail3ActionPerformed
    JNewMessageDialog nmd = new JNewMessageDialog();
    nmd.setLocationRelativeTo(null);
    nmd.setVisible(true);
}//GEN-LAST:event_jBtnNewMail3ActionPerformed

private void jBtnNewMail2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewMail2ActionPerformed
    JNewMessageDialog nmd = new JNewMessageDialog();
    nmd.setLocationRelativeTo(null);
    nmd.setVisible(true);
}//GEN-LAST:event_jBtnNewMail2ActionPerformed

private void jPnlInboxComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlInboxComponentShown
    refreshInbox();
}//GEN-LAST:event_jPnlInboxComponentShown

public void refreshInbox()
{
    SmUser me = DBAccess.GetInstance().getUserByPerson(User.GetUserPerson());
    List<SmMessage> list = DBAccess.GetInstance().getRecievedMessages(me, null, true);

    DefaultTableModel dm = (DefaultTableModel) jlReceived.getModel();

    try {
        jlReceived.setDefaultRenderer(Class.forName("java.lang.Object"), new JMessageTableRenderer());
    } catch (ClassNotFoundException ex) {
        ErrorLogger.getInstance().error("Renderer failed...");
    }

    while (dm.getRowCount() > 0) {
        dm.removeRow(0);
    }
    for (SmMessage msg : list) {
        Object perId = (msg.getMsgRecpUsrId() != null) ? msg.getMsgRecpUsrId().getUsrPerId() : "(Użytkownik usunięty)";
        dm.addRow(new Object[]{perId, msg, msg.getMsgSendDate().toString()});
    }
}

private void jPnlOutboxComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlOutboxComponentShown
    reloadOutbox();
}//GEN-LAST:event_jPnlOutboxComponentShown

private void reloadOutbox()
{
    SmUser me = DBAccess.GetInstance().getUserByPerson(User.GetUserPerson());
    List<SmMessage> list = DBAccess.GetInstance().getSentMessages( me, null );
    
    DefaultTableModel dm = (DefaultTableModel)jlSent.getModel();
    
    while(dm.getRowCount() > 0) dm.removeRow(0);
    
    for( SmMessage msg : list )
    {
        Object perId = (msg.getMsgRecpUsrId() != null) ? msg.getMsgRecpUsrId().getUsrPerId() : "(Użytkownik usunięty)";
        dm.addRow(new Object[] { perId, msg, msg.getMsgSendDate().toString() } );
    }
}

private void jbtReceiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtReceiveActionPerformed
    refreshInbox();        // TODO add your handling code here:
}//GEN-LAST:event_jbtReceiveActionPerformed

private void inboxTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inboxTableMouseClicked
    // TODO add your handling code here:
    if (evt.getClickCount() == 2) {
      JTable target = (JTable)evt.getSource();
      int row = target.getSelectedRow();
      Object o = target.getValueAt(row, 1);
      if(o == null)
          return;
      SmMessage msg = (SmMessage)o;
      JMessageDialog dlg = new JMessageDialog(msg);
      DBAccess.GetInstance().markAsRead(msg, true);
      dlg.setLocationRelativeTo(null);
      dlg.setVisible(true);
   }
}//GEN-LAST:event_inboxTableMouseClicked

private void outboxTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_outboxTableMouseClicked
    // TODO add your handling code here:
    if (evt.getClickCount() == 2) {
      JTable target = (JTable)evt.getSource();
      int row = target.getSelectedRow();
      Object o = target.getValueAt(row, 1);
      if(o == null)
          return;
      SmMessage msg = (SmMessage)o;
      JMessageDialog dlg = new JMessageDialog(msg);
      dlg.setLocationRelativeTo(null);
      dlg.setVisible(true);
   }
}//GEN-LAST:event_outboxTableMouseClicked

private void jTbNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTbNameFocusGained
    jRbSurname.setSelected(true);
}//GEN-LAST:event_jTbNameFocusGained

private void jTbRoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTbRoleActionPerformed
    jRbRole.setSelected(true);
}//GEN-LAST:event_jTbRoleActionPerformed

private void jTbClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTbClassActionPerformed
    jRbClass.setSelected(true);
}//GEN-LAST:event_jTbClassActionPerformed

private void jtblClassesmouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblClassesmouseClicked
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) return;
    
    // TODO add your handling code here:
    if (evt.getClickCount() == 2) {
      JTable target = (JTable)evt.getSource();
      int row = target.getSelectedRow();
      Object o = target.getValueAt(row, 0);
      if(o == null)
          return;
      SmClass cls = (SmClass)o;
      JEditClassDialog dlg = new JEditClassDialog(cls, new Commander() {
                public void execute() {
                    reloadClassData();
                }
            });
      dlg.setLocationRelativeTo(null);
      dlg.setVisible(true);
   }
}//GEN-LAST:event_jtblClassesmouseClicked

private void jPnlClassesComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlClassesComponentShown
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) jbtAddClass.setEnabled( false ); else jbtAddClass.setEnabled( true );
    
    reloadClassData();
}//GEN-LAST:event_jPnlClassesComponentShown

public void reloadClassData()
{
    List<SmClass> classes = DBAccess.GetInstance().GetAllClasses();
    DefaultTableModel dtm = (DefaultTableModel)jtblClasses.getModel();
    
    while( dtm.getRowCount() > 0 ) dtm.removeRow(0);
    
    for( SmClass cl : classes )
    {
        dtm.addRow( new Object[] { cl, cl.getClsDescription() } );
    }
}

private void jbtAddClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAddClassActionPerformed
    // TODO add your handling code here:
    JNewClassDialog dlg = new JNewClassDialog();
    dlg.setLocationRelativeTo(null);
    dlg.setVisible(true);
}//GEN-LAST:event_jbtAddClassActionPerformed

private void jBtnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReloadActionPerformed
    reloadClassData();    // TODO add your handling code here:
}//GEN-LAST:event_jBtnReloadActionPerformed

private void jBtnDeletePersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDeletePersonActionPerformed
    // TODO add your handling code here:
    for(int nRowIndex : jTable4.getSelectedRows())
    {
        SmPerson person = (SmPerson)jTable4.getValueAt(nRowIndex,0);
        DBAccess.GetInstance().deletePerson(person);
    }
}//GEN-LAST:event_jBtnDeletePersonActionPerformed

private void jbtAddRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAddRoomActionPerformed
    JNewClassroomDialog nc = new JNewClassroomDialog();
    nc.setLocationRelativeTo(null);
    nc.setVisible(true);
}//GEN-LAST:event_jbtAddRoomActionPerformed

private void jBtnReload1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReload1ActionPerformed
    reloadClassrooms();
}//GEN-LAST:event_jBtnReload1ActionPerformed

private void jtblRoomsmouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblRoomsmouseClicked
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) return;
    
    if( evt.getClickCount() != 2 ) return;
    if( jtblRooms.getSelectedRowCount() != 1 ) return;
    int row = jtblRooms.getSelectedRow();
    Object o = jtblRooms.getValueAt(row, 0);
    if(o == null) return;
    SmClassroom classroom = (SmClassroom)jtblRooms.getValueAt(row, 0);
    JEditClassroomDialog ec = new JEditClassroomDialog(classroom, new Commander() {
            public void execute() {
                reloadClassrooms();
            }
        });
    ec.setLocationRelativeTo(null);
    ec.setVisible(true);
}//GEN-LAST:event_jtblRoomsmouseClicked

private void jPnlRoomsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlRoomsComponentShown
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) jbtAddRoom.setEnabled( false ); else jbtAddRoom.setEnabled( true );
    
    // zaladuj sale
    reloadClassrooms();
}//GEN-LAST:event_jPnlRoomsComponentShown

private void jPnlScheduleComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlScheduleComponentShown
    
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) jbtnEdit.setEnabled( false ); else jbtnEdit.setEnabled( true );
    List<SmClass> list = DBAccess.GetInstance().GetAllClasses();
    
    jcbSelectClass.removeAllItems();
    for( SmClass cl : list )
    {
        jcbSelectClass.addItem( cl );
    }
}//GEN-LAST:event_jPnlScheduleComponentShown

private void jcbSelectClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSelectClassActionPerformed
    loadScheduleFor( (SmClass)jcbSelectClass.getSelectedItem() );
}//GEN-LAST:event_jcbSelectClassActionPerformed

private void jbtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditActionPerformed
      JEditScheduleDialog sd = new JEditScheduleDialog(this, true, (SmClass)jcbSelectClass.getSelectedItem() );
      sd.setLocationRelativeTo(null);
      sd.setVisible(true);
}//GEN-LAST:event_jbtnEditActionPerformed

private void jbtnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReloadActionPerformed
    loadScheduleFor( (SmClass)jcbSelectClass.getSelectedItem() );
}//GEN-LAST:event_jbtnReloadActionPerformed

private void jbtnShowConsoleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnShowConsoleActionPerformed
    ErrorLogger.getInstance().showWindow(true);
}//GEN-LAST:event_jbtnShowConsoleActionPerformed

private void jtblPupilNotesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblPupilNotesMouseClicked
    if( evt.getClickCount() != 2 ) return;
    if( getRole() == RoleType.ROLE_STUDENT ) return;
    DefaultTableModel tm = (DefaultTableModel) jtblPupilNotes.getModel();
    
    if( jtblPupilNotes.getSelectedRowCount() != 1 ) return;
    
    int row = jtblPupilNotes.getSelectedRow();
    SmPerson pupil = (SmPerson) tm.getValueAt(row, 0 );
    SmSubject subject = (SmSubject) jcbPickSubjectForNotes.getSelectedItem();
    SmClass cls = (SmClass) jcbPickClassForNotes.getSelectedItem();
    
    if( subject == null || cls == null ) return;
    
    JEditNotesDialog en = new JEditNotesDialog(pupil, cls, subject, this );
    en.setLocationRelativeTo(null);
    en.setVisible(true);
    
}//GEN-LAST:event_jtblPupilNotesMouseClicked

private void jPnlProfileComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlProfileComponentResized
    // TODO add your handling code here:
}//GEN-LAST:event_jPnlProfileComponentResized

private void jBtnAddSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddSubjectActionPerformed
    JAddSubjectDialog as = new JAddSubjectDialog(this, true, null );
    as.setLocationRelativeTo(null);
    as.setVisible(true);
    
    if( as.isSaved() )
    {
        SmSubject sb;
        sb = DBAccess.GetInstance().addSubject( as.getResult() );
        if( sb == null )
        {
            ErrorLogger.getInstance().error("Could not add subject!");
        }
    }
    
    realodSubjectsList();
}//GEN-LAST:event_jBtnAddSubjectActionPerformed

private void jPnlSubjectsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPnlSubjectsComponentShown
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) 
    {
        jBtnAddSubject.setEnabled(false);
        jBtnDeleteSubject.setEnabled(false);
    }
    else
    {
        jBtnAddSubject.setEnabled(true);
        jBtnDeleteSubject.setEnabled(true);
    }
    realodSubjectsList();
}//GEN-LAST:event_jPnlSubjectsComponentShown

private void jtblSubjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblSubjectsMouseClicked
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) return;
    if( evt.getClickCount() != 2 ) return;
    
    DefaultTableModel tm = (DefaultTableModel) jtblSubjects.getModel();
    if( jtblSubjects.getSelectedRowCount() != 1 ) return;
    
    int row = jtblSubjects.getSelectedRow();
    SmSubject sb;
    sb = (SmSubject) tm.getValueAt(row, 0);
    List<SmTeacher> tList = DBAccess.GetInstance().getTeacherForSubject(sb);
    ArrayList<String> al = new ArrayList<String>();
    for(SmTeacher t: tList){
        al.add(t.getTchPerId().getPerSurname() + " " + t.getTchPerId().getPerName()); 
    }
    
    
    JEditSubjectDialog es = new JEditSubjectDialog(this, true, sb.getSubName(), al.toArray() );
    es.setLocationRelativeTo(null);
    es.setVisible( true );
    
    if( es.isSaved() == false ) return;
    sb.setSubName(es.getResult() );
    if( !DBAccess.GetInstance().saveSubject( sb ) )
    {
        ErrorLogger.getInstance().error( "Could not save subject!" );
    }
}//GEN-LAST:event_jtblSubjectsMouseClicked

private void jBtnDeleteSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDeleteSubjectActionPerformed
    DefaultTableModel tm = (DefaultTableModel) jtblSubjects.getModel();
    int [] rows = jtblSubjects.getSelectedRows();
    
    SmSubject sb;
    
    for( int row : rows )
    {
        sb = (SmSubject) tm.getValueAt(row, 0);
        DBAccess.GetInstance().removeSubject(sb);
    }
    realodSubjectsList();
}//GEN-LAST:event_jBtnDeleteSubjectActionPerformed

private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    if(getRole() != RoleType.ROLE_ADMIN) jbtnShowConsole.setVisible(false); else jbtnShowConsole.setVisible(true);
}//GEN-LAST:event_formComponentShown

private void jTblRingsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTblRingsPropertyChange
    // TODO add your handling code here:
}//GEN-LAST:event_jTblRingsPropertyChange

private void jbtnDeleteMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteMsgActionPerformed
    DefaultTableModel tm = (DefaultTableModel) jlReceived.getModel();
    
    if( jlReceived.getSelectedRowCount() < 1 ) return;
    int [] rows = jlReceived.getSelectedRows();
    
    SmMessage msg;
    
    for( int row : rows )
    {
        msg = (SmMessage) tm.getValueAt( row, 1 );
        if( !DBAccess.GetInstance().deleteMessage(msg) )
        {
            ErrorLogger.getInstance().error("Could not delete message!");
        }
    }
    
    refreshInbox();
}//GEN-LAST:event_jbtnDeleteMsgActionPerformed

private void jbtnDelSentmsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDelSentmsgActionPerformed
    DefaultTableModel tm = (DefaultTableModel) jlSent.getModel();
    
    if( jlSent.getSelectedRowCount() < 1 ) return;
    int [] rows = jlSent.getSelectedRows();
    
    SmMessage msg;
    
    for( int row : rows )
    {
        msg = (SmMessage) tm.getValueAt( row, 1 );
        if( !DBAccess.GetInstance().deleteMessage(msg) )
        {
            ErrorLogger.getInstance().error("Could not delete message!");
        }
    }
    
    reloadOutbox();
}//GEN-LAST:event_jbtnDelSentmsgActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    if( getRole() != RoleType.ROLE_ADMIN && getRole() != RoleType.ROLE_PRINCIPAL ) return;

      int row = jTblTeachers.getSelectedRow();
      if(row==-1) return;
      
      Object o = jTblTeachers.getValueAt(row, 0);
      if(o == null)
          return;
      SmPerson person = (SmPerson)jTblTeachers.getValueAt(row, 0);
      JEditTeacherDialog dlg = new JEditTeacherDialog( person, new Commander() {
                public void execute() {
                    reloadTeachers();
                }
      } );
      dlg.setLocationRelativeTo(null);
      dlg.setVisible(true);
   
}//GEN-LAST:event_jButton1ActionPerformed

private void jPnlSubjectsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPnlSubjectsMouseClicked
    // TODO add your handling code here:
}//GEN-LAST:event_jPnlSubjectsMouseClicked



public void reloadClassrooms()
{
    DefaultTableModel tm = (DefaultTableModel)jtblRooms.getModel();
    
    while( tm.getRowCount() > 0 ) tm.removeRow(0);
    List<SmClassroom> list = DBAccess.GetInstance().getAllClasrooms();
    
    for( SmClassroom cl : list )
    {
        tm.addRow(new Object[] { cl, cl.getClrDescr() });
    }
}

String dateToTimeString( Date date )
{
    return String.format("%tH:%tM",date,date);
}

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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jBtnAddPerson;
    private javax.swing.JButton jBtnAddSubject;
    private javax.swing.JButton jBtnDeletePerson;
    private javax.swing.JButton jBtnDeleteSubject;
    private javax.swing.JButton jBtnNewMail;
    private javax.swing.JButton jBtnNewMail2;
    private javax.swing.JButton jBtnNewMail3;
    private javax.swing.JButton jBtnPassCHange;
    private javax.swing.JButton jBtnReload;
    private javax.swing.JButton jBtnReload1;
    private javax.swing.JButton jBtnSave;
    private javax.swing.JButton jBtnSearch;
    private javax.swing.JButton jBtnSearch2;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLayeredPane jLayers;
    private javax.swing.JLabel jLblLogText;
    private javax.swing.JLabel jLblLoggedAs;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPnlClasses;
    private javax.swing.JPanel jPnlInbox;
    private javax.swing.JPanel jPnlMail;
    private javax.swing.JPanel jPnlNotes;
    private javax.swing.JPanel jPnlOptions;
    private javax.swing.JPanel jPnlOutbox;
    private javax.swing.JPanel jPnlPeople;
    private javax.swing.JPanel jPnlProfile;
    private javax.swing.JPanel jPnlRings;
    private javax.swing.JPanel jPnlRooms;
    private javax.swing.JPanel jPnlSchedule;
    private javax.swing.JPanel jPnlSubjects;
    private javax.swing.JPanel jPnlTeachers;
    private javax.swing.JRadioButton jRbClass;
    private javax.swing.JRadioButton jRbRole;
    private javax.swing.JRadioButton jRbSubject;
    private javax.swing.JRadioButton jRbSurname;
    private javax.swing.JRadioButton jRbSurname1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPanel jStatus;
    private javax.swing.JTable jTable4;
    private javax.swing.JComboBox jTbClass;
    private javax.swing.JTextField jTbName;
    private javax.swing.JTextField jTbName1;
    private javax.swing.JComboBox jTbRole;
    private javax.swing.JComboBox jTbSubs;
    private javax.swing.JTable jTblRings;
    private javax.swing.JTable jTblTeachers;
    private javax.swing.JTree jTree;
    private javax.swing.JButton jbtAddClass;
    private javax.swing.JButton jbtAddRoom;
    private javax.swing.JButton jbtReceive;
    private javax.swing.JButton jbtnDelSentmsg;
    private javax.swing.JButton jbtnDeleteMsg;
    private javax.swing.JButton jbtnEdit;
    private javax.swing.JButton jbtnReload;
    private javax.swing.JButton jbtnShowConsole;
    private javax.swing.JComboBox jcbPickClassForNotes;
    private javax.swing.JComboBox jcbPickSubjectForNotes;
    private javax.swing.JComboBox jcbSelectClass;
    private javax.swing.JTable jlReceived;
    private javax.swing.JTable jlSent;
    private javax.swing.JPanel jpanelClassSelect;
    private javax.swing.JPanel jpanelProporties;
    private javax.swing.JTable jtblClasses;
    private javax.swing.JTable jtblPupilNotes;
    private javax.swing.JTable jtblRooms;
    private javax.swing.JTable jtblScheldue;
    private javax.swing.JTable jtblSubjects;
    private schoolmanagement.controls.PersonDetailsControl personDetailsControl1;
    // End of variables declaration//GEN-END:variables

    public void execute() {
        reloadNotes();
    }

    private void realodSubjectsList() {
        List<SmSubject> list = DBAccess.GetInstance().getAllSubjects();
        DefaultTableModel tm = (DefaultTableModel)jtblSubjects.getModel();
        
        while( tm.getRowCount() > 0 ) tm.removeRow(0);
        
        for( SmSubject s : list )
        {
            tm.addRow(new Object[] { s } );
        }
    }

    private void reloadNotes() {
        SmSubject subject = (SmSubject) jcbPickSubjectForNotes.getSelectedItem();
        SmClass cls = (SmClass) jcbPickClassForNotes.getSelectedItem();
        
        if( cls == null || subject == null) return;
        
        DefaultTableModel tm = (DefaultTableModel) jtblPupilNotes.getModel();
        while( tm.getRowCount() > 0 ) tm.removeRow(0);
        
        List<SmPerson> list = DBAccess.GetInstance().GetPersonsForClass(cls);
        
        String notes;
        for( SmPerson pupil : list )
        {
            if( getRole() == RoleType.ROLE_STUDENT && User.GetUserPerson() != pupil ) continue;
            notes = compileNotes( pupil, cls, subject );
            tm.addRow(new Object[] { pupil, notes } );
        }
    }

    private void reloadTeachers() {
        DefaultTableModel model = (DefaultTableModel)jTblTeachers.getModel();
        while( model.getRowCount() > 0 ) model.removeRow(0);
        if(jRbSurname1.isSelected()){
            List<SmPerson> list;
            list = DBAccess.GetInstance().GetUserByName(jTbName.getText().trim());

            for( SmPerson pers : list )
            {
                if( DBAccess.GetInstance().getUserRole(pers).getRolName().compareTo( "Nauczyciel" ) != 0 ) continue;

                List<SmSubject> subs = DBAccess.GetInstance().getSubjectsForPerson( pers );
                String strSubs = "";
                for( SmSubject sb : subs )
                {
                    if( strSubs.compareTo("") != 0 ) strSubs += ", ";
                    strSubs += sb.getSubName();
                }
                model.addRow(new Object[] { pers, strSubs } );

            }
        }
        else if(jRbSubject.isSelected()){
            List<SmTeacher> list = DBAccess.GetInstance().getTeacherForSubject( (SmSubject)jTbSubs.getSelectedObjects()[0] );

            for( SmTeacher tr : list )
            {
                model.addRow(new Object[] { 
                    tr.getTchPerId(),
                    tr.getTchSubId()
                });
            }
        }
    }
}
