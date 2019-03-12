/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Optional.empty;
import java.util.TreeMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import nf.Lit;
import nf.Orientation;
import nf.Patient;
import nf.PersonnelMedical;

import nf.RechercherInfo;
import nf.Sejour;
import nf.Specialite;

import nf.SecretaireMedicale;
import nf.Service;
import static ui.SecretaireMedicaleUrgence.selection;

/**
 * !!
 *
 * @author poite
 */
public class PH extends JFrame implements ActionListener {
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    DefaultListModel m = new DefaultListModel();
    RechercherInfo inf = new RechercherInfo();
    ArrayList<Patient> Lp;
    ArrayList<Patient> listePatient;
    nf.PersonnelMedical perso;
    String PatientSelection = "";
    public static String selection="";
    DefaultListModel DLM = new DefaultListModel();
    nf.PH ph = new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
    nf.Patient patient = new nf.Patient("bluff", "bluff");
    nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
    private boolean phref = false;
    private  DefaultTreeCellRenderer tCellRenderer = new  DefaultTreeCellRenderer();
    private DefaultMutableTreeNode racine;

 
    private int compteur=0;
    /**
     * Creates new form PH
     */
    public PH(nf.PersonnelMedical p) {

        initComponents();
        setSize(700, 600);
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }

        jButton1.addActionListener(this);
        this.perso = p;
        String s = "Mme/M. " + p.getNom() + " " + p.getPrenom();
        jLabel1.setText(s);
        jLabel2.setText(perso.getSpecialite().toString());

        jButton7.setEnabled(false);
        jButton3.setEnabled(false);

        listePatient = secrMed.afficherListePatientParService(perso.getSpecialite());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, ERROR, ABORT, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.URGENCE);
            String ipp = patient.ippPatientListe(element);

            String idDernierSejour = ph.idSejourPatientSelection(ipp);
            System.out.println("L LLLLLLLLLLLLLL");

            System.out.println(idDernierSejour);
            if (sejourBluff.sejourEnCours(idDernierSejour)) {
                DLM.addElement(element);
            }
            jList1.setModel(DLM);
            jList1.repaint();
        }

    }
    
    public PH() {
        
    }

    public String getPrenom() {
        compteur = getNom().length()+9;
        System.out.println("le compteur est de : "+compteur);
        System.out.println("et la lettre à cet endroit est : "+selection.charAt(compteur));
        String prenom="";
        while (!(String.valueOf(selection.charAt(compteur))).equals(" ")){
            prenom = prenom + selection.charAt(compteur);
            compteur++;
        }
        compteur++;
        System.out.println("prenom = "+prenom);
        return prenom;
        
    }
    
    public String getNom() {
        String nom="";
        System.out.println("ceci est la séléction : "+selection);
        compteur=0;
        while (!(String.valueOf(selection.charAt(compteur))).equals(" ")){
            System.out.println(String.valueOf(selection.charAt(compteur)));
            System.out.println((String.valueOf(selection.charAt(compteur))).equals(" "));
            nom = nom + selection.charAt(compteur);
            compteur++;
        }
        compteur++;
        System.out.println("nom = "+nom);
        return nom;
    }
    
    public String getDateNaissance(String nom, String prenom){
        compteur = getNom().length()+getPrenom().length() +19;
        System.out.println("le compteur est : "+compteur);
        System.out.println("et la lettre à cet endroit est : "+selection.charAt(compteur));
        System.out.println("longueur de : "+selection.length());
        String dateNaissance="";
        while (compteur<selection.length()){
            System.out.println("compteur de : "+compteur);
            dateNaissance= dateNaissance + selection.charAt(compteur);
            compteur++;
        }
        System.out.println(" date naissance = "+dateNaissance);
        return dateNaissance;
    }
    /**
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initRenderer() {
        //Instanciation
       
        this.tCellRenderer.setClosedIcon(null);
        this.tCellRenderer.setOpenIcon(null);
        this.tCellRenderer.setLeafIcon(null);
    }

    public void buildTree() {
    DM.setCellRenderer(this.tCellRenderer);
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();

        }
        List<String> listeIdSejours = sejourCourant.listeSejour(patient.ippPatientListe(PatientSelection));
        List<String> listedateSaisie;
        TreeMap<String, String> listeInfos;

        javax.swing.tree.DefaultMutableTreeNode racine = new javax.swing.tree.DefaultMutableTreeNode("Mme/M." + patient.patientListe(PatientSelection));

        for (int i = 0; i < listeIdSejours.size(); i++) {
            listedateSaisie = sejourCourant.listeSaisie(listeIdSejours.get(i));

            javax.swing.tree.DefaultMutableTreeNode sejour = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSejourtoString(listeIdSejours.get(i)));

            for (int j = 0; j < listedateSaisie.size(); j++) {
                javax.swing.tree.DefaultMutableTreeNode saisie = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSaisietoString(listedateSaisie.get(j), listeIdSejours.get(i)));
                sejour.add(saisie);
                listeInfos = sejourCourant.listeInfos(listedateSaisie.get(j), listeIdSejours.get(i));

                for (Map.Entry mapentry : listeInfos.entrySet()) {
                    String[] tab = mapentry.getKey().toString().split("X");
                    for (int k = 1; k < tab.length; k++) {
                        javax.swing.tree.DefaultMutableTreeNode info = new javax.swing.tree.DefaultMutableTreeNode(tab[k] + " : " + mapentry.getValue());
                        saisie.add(info);
                    }
                }
            }

            racine.add(sejour);

        }

        DefaultTreeModel arbre = new DefaultTreeModel(racine);
        DM.setModel(arbre);

    }

    public class bouton {

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ptsm = null;
        Patient patientSelection;

        /**
         *
         * @return
         */
        public ResultSet rechercher() {
            try {
                //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
                //pstm = con.prepareStatement("select * from patients where nom= ?  and prenom= ?");
                String query = "select * from patients where nom= ?  and prenom= ?";
                ptsm = con.prepareStatement(query);
                rs = ptsm.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("nom");
                    String lastName = rs.getString("prenom");
                    m.addElement(name);
                    m.addElement(lastName);
                    // pstm.setString(1, s1);
                    //pstm.setString(2, s2);
                    //m.addElement(s1);
                    //m.addElement(s2);

                }
                jList1.setModel(m);
            } catch (Exception ex) {
                System.out.println("error :" + ex);
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            return rs;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        interfacePH = new javax.swing.JTabbedPane();
        panelAccueil = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelInformationsPatient = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane7 = new javax.swing.JScrollPane();
        DM = new javax.swing.JTree(racine);
        jLabel16 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        panelCompleter = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textObservations = new javax.swing.JTextArea();
        jLabelObs = new javax.swing.JLabel();
        textTitreOperation = new javax.swing.JTextField();
        jLabelOp = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        textDetailsOperations = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        textResultats = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        textPrescriptions = new javax.swing.JTextArea();
        boutonPaneCompleter = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelDemandeDePrestation = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        textPrestation = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        medPres = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panelSortie = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        textLettreDeSortie = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 600));

        interfacePH.setBackground(new java.awt.Color(255, 255, 255));
        interfacePH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        interfacePH.setPreferredSize(new java.awt.Dimension(700, 600));
        interfacePH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                interfacePHMouseClicked(evt);
            }
        });

        panelAccueil.setBackground(new java.awt.Color(255, 255, 255));
        panelAccueil.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Dr DUPONT Laurent");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(0, 153, 153));
        jLabel2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel2.setText("ONCOLOGIE");
        jLabel2.setOpaque(true);

        jScrollPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste de patients", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jList1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jToggleButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jToggleButton1.setText("Suivant");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Rechercher ...");

        jTextField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Nom");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Prénom");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Date de naissance");

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFormattedTextField1.setToolTipText("");
        jFormattedTextField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Archiver");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("Déconnexion");

        jButton6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton6.setText("Changer le mot de passe");

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        javax.swing.GroupLayout panelAccueilLayout = new javax.swing.GroupLayout(panelAccueil);
        panelAccueil.setLayout(panelAccueilLayout);
        panelAccueilLayout.setHorizontalGroup(
            panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAccueilLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22))
            .addGroup(panelAccueilLayout.createSequentialGroup()
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(panelAccueilLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton6)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelAccueilLayout.createSequentialGroup()
                                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelAccueilLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(171, 171, 171)
                                        .addComponent(jLabel6)
                                        .addGap(154, 154, 154)
                                        .addComponent(jLabel7))
                                    .addGroup(panelAccueilLayout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1)))
                                .addGap(0, 36, Short.MAX_VALUE))
                            .addGroup(panelAccueilLayout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        panelAccueilLayout.setVerticalGroup(
            panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccueilLayout.createSequentialGroup()
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(31, 31, 31)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22))
        );

        interfacePH.addTab("ACCUEIL", panelAccueil);

        panelInformationsPatient.setBackground(new java.awt.Color(255, 255, 255));
        panelInformationsPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelInformationsPatientMouseClicked(evt);
            }
        });

        jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Administratif", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jTree1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane6.setViewportView(jTree1);

        DM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Médical", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        DM.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane7.setViewportView(DM);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("  ");

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout panelInformationsPatientLayout = new javax.swing.GroupLayout(panelInformationsPatient);
        panelInformationsPatient.setLayout(panelInformationsPatientLayout);
        panelInformationsPatientLayout.setHorizontalGroup(
            panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationsPatientLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformationsPatientLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformationsPatientLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))))
        );
        panelInformationsPatientLayout.setVerticalGroup(
            panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformationsPatientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(3, 3, 3)
                .addGroup(panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addComponent(jLabel24))
        );

        interfacePH.addTab("INFORMATIONS PATIENT", panelInformationsPatient);

        panelCompleter.setBackground(new java.awt.Color(255, 255, 255));
        panelCompleter.setPreferredSize(new java.awt.Dimension(666, 476));

        textObservations.setColumns(20);
        textObservations.setRows(5);
        jScrollPane2.setViewportView(textObservations);

        jLabelObs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelObs.setForeground(new java.awt.Color(0, 153, 153));
        jLabelObs.setText("Observations :");

        textTitreOperation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabelOp.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabelOp.setForeground(new java.awt.Color(0, 153, 153));
        jLabelOp.setText("Opérations :");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 153));
        jLabel10.setText("Titre :");

        textDetailsOperations.setColumns(20);
        textDetailsOperations.setRows(5);
        jScrollPane3.setViewportView(textDetailsOperations);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 153));
        jLabel11.setText("Détails :");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 153, 153));
        jLabel12.setText("Résultats :");

        textResultats.setColumns(20);
        textResultats.setRows(5);
        jScrollPane4.setViewportView(textResultats);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 153));
        jLabel13.setText("Prescriptions :");

        textPrescriptions.setColumns(20);
        textPrescriptions.setRows(5);
        jScrollPane5.setViewportView(textPrescriptions);

        boutonPaneCompleter.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        boutonPaneCompleter.setText("OK");
        boutonPaneCompleter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completerSejour(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(0, 153, 153));
        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("  ");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voirLesInfosPatient(evt);
            }
        });

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N
        jLabel23.setToolTipText("");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        javax.swing.GroupLayout panelCompleterLayout = new javax.swing.GroupLayout(panelCompleter);
        panelCompleter.setLayout(panelCompleterLayout);
        panelCompleterLayout.setHorizontalGroup(
            panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompleterLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel23))
            .addGroup(panelCompleterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane5)
                    .addGroup(panelCompleterLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addGroup(panelCompleterLayout.createSequentialGroup()
                        .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelObs)
                            .addComponent(jLabel12)
                            .addComponent(jLabelOp)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompleterLayout.createSequentialGroup()
                        .addGap(0, 42, Short.MAX_VALUE)
                        .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompleterLayout.createSequentialGroup()
                                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3)
                                    .addComponent(textTitreOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(boutonPaneCompleter, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        panelCompleterLayout.setVerticalGroup(
            panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompleterLayout.createSequentialGroup()
                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCompleterLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14))
                    .addComponent(jLabel9))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelObs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelOp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(textTitreOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boutonPaneCompleter)
                .addGap(2, 2, 2)
                .addComponent(jLabel23))
        );

        interfacePH.addTab("COMPLETER", panelCompleter);

        panelDemandeDePrestation.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 153, 153));
        jLabel20.setText("Demande de prestation");

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("OK");

        textPrestation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 153, 153));
        jLabel15.setText("à :");

        medPres.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        //Prestation pres [] = Prestation.values();
        Specialite spe[]={Specialite.Sélectionner,Specialite.ANESTHESIE, Specialite.ANAPATHOLOGIE,Specialite.HEMATOLOGIE , Specialite.RADIOLOGIE,Specialite.LABORATOIRE_ANALYSE};

        DefaultComboBoxModel model = new DefaultComboBoxModel(spe);
        jComboBox1.setModel(model);
        jComboBox1.setToolTipText("");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jList2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des médecins", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jList2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(jList2);

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        javax.swing.GroupLayout panelDemandeDePrestationLayout = new javax.swing.GroupLayout(panelDemandeDePrestation);
        panelDemandeDePrestation.setLayout(panelDemandeDePrestationLayout);
        panelDemandeDePrestationLayout.setHorizontalGroup(
            panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18)
                    .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                        .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDemandeDePrestationLayout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(medPres, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDemandeDePrestationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addContainerGap())
        );
        panelDemandeDePrestationLayout.setVerticalGroup(
            panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(textPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(medPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4)
                        .addComponent(jLabel15))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addComponent(jLabel18))
        );

        interfacePH.addTab("DEMANDE PRESTATION", panelDemandeDePrestation);

        panelSortie.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 153, 153));
        jLabel17.setText("Lettre de sortie :");

        textLettreDeSortie.setColumns(20);
        textLettreDeSortie.setRows(5);
        jScrollPane8.setViewportView(textLettreDeSortie);

        jButton7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton7.setText("OK");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout panelSortieLayout = new javax.swing.GroupLayout(panelSortie);
        panelSortie.setLayout(panelSortieLayout);
        panelSortieLayout.setHorizontalGroup(
            panelSortieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSortieLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelSortieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7)
                    .addGroup(panelSortieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSortieLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel25))
        );
        panelSortieLayout.setVerticalGroup(
            panelSortieLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSortieLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jLabel25))
        );

        interfacePH.addTab("SORTIE", panelSortie);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfacePH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfacePH, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void interfacePHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_interfacePHMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_interfacePHMouseClicked

//supprimer
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       new ChangerMDP(this.perso).setVisible(true);
    }                                        

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        // TODO add your handling code here:
        if (!jList2.isSelectionEmpty()) {
            medPres.setText(jList2.getSelectedValue().toString());
        }
    }//GEN-LAST:event_jList2ValueChanged

    private void medPresActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

//   

    private void textPrestationActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        //Renvoie l'id du medecin selectionné = medecin receveur
        if (!jList2.isSelectionEmpty()) {
            String selection = jList2.getSelectedValue();
            String idp = ph.iPPMedecinListe(selection);
            System.out.println("IPP MEDECIN EST : " + idp);

            sejourCourant.ajouterPrestation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), idp, textPrestation.getText());
            textPrestation.setText("");
            medPres.setText("");
            DLM.clear();
            jList2.setModel(DLM);
        }

    }                                        

    private void voirLesInfosPatient(java.awt.event.MouseEvent evt) {                                     
        interfacePH.setSelectedIndex(2);
    }                                    

    private void completerSejour(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completerSejour
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();
        }

        //On ajoute une observation seule
        if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.ajouterObservation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText());
            System.out.println("1");
        } //On ajoute une operation seule
        //il faut que titre ET details soient remplis
        else if ((!textTitreOperation.getText().isEmpty() && textDetailsOperations.getText().isEmpty()) || (textTitreOperation.getText().isEmpty() && !textDetailsOperations.getText().isEmpty())) {
            System.out.println("il faudra mettre un pop-up car titre ou details non rempli");

        } else if (textDetailsOperations.getText().isEmpty() && textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.ajouterOperations(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textTitreOperation.getText(), textDetailsOperations.getText());
            System.out.println("2");
        } //On ajoute un resultat seul
        else if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.ajouterResultat(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textResultats.getText());
            System.out.println("3");
        } // On ajoute une prescription seule
        else if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty() && textResultats.getText().isEmpty()) {
            sejourCourant.ajouterPrescription(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textPrescriptions.getText());
            System.out.println("4");
        } //On ajoute une observation et une operation
        else if (textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), "", "");
            System.out.println("5");
        } //On ajoute une observation et un resultat
        else if (textResultats.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", textResultats.getText(), "");
            System.out.println("6");
        } //On ajoute une observation et un prescription
        else if (textResultats.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", "", textPrescriptions.getText());
            System.out.println("7");
        } //On ajoute une operation et un resultat
        else if ((textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), "");
            System.out.println("8");
        } //On ajoute une operation et une prescription
        else if (textResultats.getText().isEmpty() && textObservations.getText().isEmpty()) {
            System.out.println("9");
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), "", textPrescriptions.getText());
        } //On ajoute une prescription et un resultat
        else if ((textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", "", "", textResultats.getText(), textPrescriptions.getText());
            System.out.println("10");
        } // On ajoute une observation, une operation, un resultat
        else if (textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), "");
            System.out.println("11");
        } // On ajoute une observation, une operation, une prescription
        else if (textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), "", textPrescriptions.getText());
            System.out.println("12");
        } // On ajoute une observation, un resultat, une prescription
        else if (textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", textResultats.getText(), textPrescriptions.getText());
            System.out.println("13");
        } //On ajoute une operation, un resultat et une prescription
        else if (textPrescriptions.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), textPrescriptions.getText());
            System.out.println("14");
        } //On complete l'integralité du sejour
        else {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), textPrescriptions.getText());
            System.out.println("15");
        }

        textObservations.setText("");
        textTitreOperation.setText("");
        textDetailsOperations.setText("");
        textResultats.setText("");
        textPrescriptions.setText("");
    }//GEN-LAST:event_completerSejour

    private void panelInformationsPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelInformationsPatientMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelInformationsPatientMouseClicked

    private void panelAccueilMouseClicked(java.awt.event.MouseEvent evt) {                                          
        // Mise à jour de la liste des patients
        DLM.clear();
        listePatient = secrMed.afficherListePatientParService(perso.getSpecialite());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, ERROR, ABORT, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.URGENCE);
            String ipp = patient.ippPatientListe(element);
            String idDernierSejour = ph.idSejourPatientSelection(ipp);
            System.out.println("\n\n");
            System.out.println("              ICI                ");
            System.out.println(" a voiiiir" + idDernierSejour);
            System.out.println(sejourBluff.sejourEnCours(idDernierSejour));
            if (sejourBluff.sejourEnCours(idDernierSejour)) {
                DLM.addElement(element);
            }
            jList1.setModel(DLM);
            jList1.repaint();
        }
        
           
       
    }                                         

    private void jButton6ActionPerformed1(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        new ChangerMDP(this.perso).setVisible(true);
    }                                         

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        new Connexion().setVisible(true);
        this.dispose();
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String date = jFormattedTextField1.getText();
        DefaultListModel DLM = new DefaultListModel();

        if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && jFormattedTextField1.getText() != null) {
            Lp = inf.recherchePatientNomPrenomDate(jTextField1.getText(), jTextField2.getText(), date);

            for (int i = 0; i < Lp.size(); i++) {
                String element = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM.addElement(element);
            }
            //jList1.setModel(DLM);
        }
        if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty()) {
            Lp = inf.rechercheListPatientNomPrenom(jTextField1.getText(), jTextField2.getText());
            // DefaultListModel DLM = new DefaultListModel();
            for (int i = 0; i < Lp.size(); i++) {
                String element = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM.addElement(element);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        jLabel14.setText("" + jList1.getSelectedValue().toString());
        jLabel16.setText("" + jList1.getSelectedValue().toString());

        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();

            System.out.println(ph.estReferent(perso.getIdMed(), ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection))));

            //cliquer sur le bouton "Suivant" ouvre l'onglet où le médecin peut compléter les données du patient courant
            if (ph.estReferent(perso.getIdMed(), ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)))) {
                phref = true;
                System.out.println("VRAI");
                jButton7.setEnabled(true);
                jButton3.setEnabled(true);
            } else {
                System.out.println("FUAX");
            }

        }

        interfacePH.setSelectedIndex(1);
        jToggleButton1.setSelected(false);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {                                    
        // TODO add your handling code here:
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();
            initRenderer();
            buildTree();
         new nouveauPatientArchive().setVisible(true);
    }
    }

//   

//supprimer
/*

    private void voirLesInfosPatient(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voirLesInfosPatient
    
    }//GEN-LAST:event_voirLesInfosPatient
*/

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
         if (jList1.getSelectedValue() !=null){
            selection = jList1.getSelectedValue().toString();
            System.out.println(selection);
            System.out.println(jList1.getSelectedValue());
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
           if(phref==true){
            sejourCourant.editerLettreDeSortie(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection),textLettreDeSortie.getText());
            textLettreDeSortie.setText("");
        }
        else {
            JOptionPane.showMessageDialog(this,"Vous n'êtes pas autorisé(e) à éditer la lettre de sortie", "ATTENTION : Vous n'êtes pas le PH référent",JOptionPane.ERROR_MESSAGE);
   
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        DefaultListModel DLM = new DefaultListModel();
        String[] anesthesie = ph.afficherListePHAnes();
        String[] radiologue = ph.afficherListePHRadio();
        String[] hemato = ph.afficherListePHHemato();
        String[] anapatho = ph.afficherListePHAnapatho();
        String[] labo = ph.afficherListePHLabo();
        if (jComboBox1.getSelectedItem().equals(Specialite.ANESTHESIE)) {
            for (int i = 0; i < anesthesie.length; i++) {
                DLM.addElement(anesthesie[i]);
            }
            jList2.setModel(DLM);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.LABORATOIRE_ANALYSE)) {
            for (int i = 0; i < labo.length; i++) {
                DLM.addElement(labo[i]);
            }
            jList2.setModel(DLM);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.HEMATOLOGIE)) {
            for (int i = 0; i < hemato.length; i++) {
                DLM.addElement(hemato[i]);
            }
            jList2.setModel(DLM);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.ANAPATHOLOGIE)) {
            for (int i = 0; i < anapatho.length; i++) {
                DLM.addElement(anapatho[i]);
            }
            jList2.setModel(DLM);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.RADIOLOGIE)) {
            for (int i = 0; i < radiologue.length; i++) {
                DLM.addElement(radiologue[i]);
            }
            jList2.setModel(DLM);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        //   new PH();

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //    new PH().setVisible(true);

            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree DM;
    private javax.swing.JButton boutonPaneCompleter;
    private javax.swing.JTabbedPane interfacePH;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelObs;
    private javax.swing.JLabel jLabelOp;
    private javax.swing.JList jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTextField medPres;
    private javax.swing.JPanel panelAccueil;
    private javax.swing.JPanel panelCompleter;
    private javax.swing.JPanel panelDemandeDePrestation;
    private javax.swing.JPanel panelInformationsPatient;
    private javax.swing.JPanel panelSortie;
    private javax.swing.JTextArea textDetailsOperations;
    private javax.swing.JTextArea textLettreDeSortie;
    private javax.swing.JTextArea textObservations;
    private javax.swing.JTextArea textPrescriptions;
    private javax.swing.JTextField textPrestation;
    private javax.swing.JTextArea textResultats;
    private javax.swing.JTextField textTitreOperation;
    // End of variables declaration//GEN-END:variables

}
