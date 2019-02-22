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
import static java.util.Optional.empty;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import nf.Lit;
import nf.Orientation;
import nf.Patient;
import nf.PersonnelMedical;

import nf.RechercherInfo;
import nf.Sejour;
import nf.Specialite;

import nf.SecretaireMedicale;
import nf.Service;

/**
 * !!
 *
 * @author poite
 */
public class PH extends JFrame implements ActionListener {

    DefaultListModel m = new DefaultListModel();
    RechercherInfo inf = new RechercherInfo();
    ArrayList<Patient> Lp;
    ArrayList<Patient> listePatient;
    nf.PersonnelMedical perso;
    String PatientSelection = "";
    DefaultListModel DLM = new DefaultListModel();
    nf.PH ph = new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
    nf.Patient patient = new nf.Patient("bluff", "bluff");
    nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);

    /**
     * Creates new form PH
     */
    public PH(nf.PersonnelMedical p) {

        initComponents();
        setSize(700, 600);

        jButton1.addActionListener(this);
        this.perso = p;
        String s = "Mme/M. " + p.getNom() + " " + p.getPrenom();
        jLabel1.setText(s);
        listePatient = secrMed.afficherListePatientParService(perso.getSpecialite());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
              //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, ERROR, ABORT, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.URGENCE);
            String ipp = patient.ippPatientListe(element);
            String idDernierSejour = ph.idSejourPatientSelection(ipp);

            if (sejourBluff.sejourEnCours(idDernierSejour)) {
                DLM.addElement(element);
            }
        jList1.setModel(DLM);
        jList1.repaint();}
      
    }

    /**
     *
     * @param ae
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
    }

//    public  void afficherListePatient() {
//        DefaultListModel DTM = new DefaultListModel();
//        DTM = (DefaultListModel) jList1.getModel();
//
//      //  if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty()) {
//
//            Lp = inf.afficherListPatient();
//            for (int i = 0; i < Lp.size(); i++) {
//                Lp.get(i);
//                DTM.addElement(new Object[]{Lp.get(i).getNom(), Lp.get(i).getPrenom(),});
//                jList1.setModel(DTM);
//            //}
//
//        }
//    }
    /**
     *
     */
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
        jLabel3 = new javax.swing.JLabel();
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
        panelCompleter = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textObservations = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        textTitreOperation = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
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
        panelInformationsPatient = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panelSortie = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        textLettreDeSortie = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        panelDemandeDePrestation = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        textPrestation = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        interfacePH.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        panelAccueil.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        panelAccueil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAccueilMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Dr DUPONT Laurent");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("ONCOLOGIE");

        jScrollPane1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jList1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(jList1);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Liste de patients");

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
        jButton5.setText("Deconnexion");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton6.setText("Mdp");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed1(evt);
            }
        });

        javax.swing.GroupLayout panelAccueilLayout = new javax.swing.GroupLayout(panelAccueil);
        panelAccueil.setLayout(panelAccueilLayout);
        panelAccueilLayout.setHorizontalGroup(
            panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccueilLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAccueilLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelAccueilLayout.createSequentialGroup()
                                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addComponent(jButton1))))
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(30, 30, 30)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton1))
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelAccueilLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelAccueilLayout.setVerticalGroup(
            panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccueilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(1, 1, 1)
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(panelAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton3)
                    .addComponent(jToggleButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        interfacePH.addTab("ACCUEIL", panelAccueil);

        panelCompleter.setPreferredSize(new java.awt.Dimension(666, 476));

        textObservations.setColumns(20);
        textObservations.setRows(5);
        jScrollPane2.setViewportView(textObservations);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Observations :");

        textTitreOperation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel9.setText("Opérations :");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Titre :");

        textDetailsOperations.setColumns(20);
        textDetailsOperations.setRows(5);
        jScrollPane3.setViewportView(textDetailsOperations);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Détails :");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel12.setText("Résultats :");

        textResultats.setColumns(20);
        textResultats.setRows(5);
        jScrollPane4.setViewportView(textResultats);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        jLabel14.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel14.setText("ABI CHACRA");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                voirLesInfosPatient(evt);
            }
        });

        javax.swing.GroupLayout panelCompleterLayout = new javax.swing.GroupLayout(panelCompleter);
        panelCompleter.setLayout(panelCompleterLayout);
        panelCompleterLayout.setHorizontalGroup(
            panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompleterLayout.createSequentialGroup()
                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCompleterLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompleterLayout.createSequentialGroup()
                                .addGap(0, 40, Short.MAX_VALUE)
                                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                                    .addComponent(textTitreOperation)))
                            .addGroup(panelCompleterLayout.createSequentialGroup()
                                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel13))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane5)
                            .addGroup(panelCompleterLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addGap(70, 70, 70))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCompleterLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boutonPaneCompleter)))
                .addContainerGap())
        );
        panelCompleterLayout.setVerticalGroup(
            panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCompleterLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(panelCompleterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        interfacePH.addTab("COMPLETER", panelCompleter);

        jTree1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane6.setViewportView(jTree1);

        jTree2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jScrollPane7.setViewportView(jTree2);

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("ABI CHACRA");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel18.setText("Dossier médical :");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel19.setText("Dossier médical administratif :");

        javax.swing.GroupLayout panelInformationsPatientLayout = new javax.swing.GroupLayout(panelInformationsPatient);
        panelInformationsPatient.setLayout(panelInformationsPatientLayout);
        panelInformationsPatientLayout.setHorizontalGroup(
            panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformationsPatientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformationsPatientLayout.createSequentialGroup()
                        .addGap(0, 322, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelInformationsPatientLayout.createSequentialGroup()
                        .addGroup(panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInformationsPatientLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(0, 315, Short.MAX_VALUE))
                            .addComponent(jScrollPane7))
                        .addContainerGap())))
        );
        panelInformationsPatientLayout.setVerticalGroup(
            panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInformationsPatientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformationsPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                    .addComponent(jScrollPane7))
                .addGap(35, 35, 35))
        );

        interfacePH.addTab("INFORMATIONS PATIENT", panelInformationsPatient);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel17.setText("Lettre de sortie :");

        textLettreDeSortie.setColumns(20);
        textLettreDeSortie.setRows(5);
        jScrollPane8.setViewportView(textLettreDeSortie);

        jButton7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton7.setText("OK");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

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
                .addContainerGap(45, Short.MAX_VALUE))
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
                .addContainerGap(61, Short.MAX_VALUE))
        );

        interfacePH.addTab("SORTIE", panelSortie);

        jLabel20.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel20.setText("Demande de prestation");

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("OK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        textPrestation.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        textPrestation.setText("Scanner");
        textPrestation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPrestationActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel15.setText("à :");

        jTextField5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        //Prestation pres [] = Prestation.values();
        Specialite spe[]={Specialite.Sélectionner , Specialite.ANESTHESIE , Specialite.RADIOLOGIE};

        DefaultComboBoxModel model = new DefaultComboBoxModel(spe);
        jComboBox1.setModel(model);
        jComboBox1.setToolTipText("");
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jList2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(jList2);

        jLabel21.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel21.setText("Liste des Medecins : ");

        javax.swing.GroupLayout panelDemandeDePrestationLayout = new javax.swing.GroupLayout(panelDemandeDePrestation);
        panelDemandeDePrestation.setLayout(panelDemandeDePrestationLayout);
        panelDemandeDePrestationLayout.setHorizontalGroup(
            panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(textPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addContainerGap(561, Short.MAX_VALUE))
                            .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jButton4)
                                .addContainerGap(50, Short.MAX_VALUE))))
                    .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                        .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(475, Short.MAX_VALUE))))
        );
        panelDemandeDePrestationLayout.setVerticalGroup(
            panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDemandeDePrestationLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(textPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDemandeDePrestationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        interfacePH.addTab("DEMANDE PRESTATION", panelDemandeDePrestation);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfacePH)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(interfacePH, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void completerSejour(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completerSejour
        PatientSelection = jList1.getSelectedValue().toString();
        
        
        //On ajoute une observation seule
        if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.ajouterObservation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText());
            System.out.println("1");
        } 

        //On ajoute une operation seule
        //il faut que titre ET details soient remplis
        else if ((!textTitreOperation.getText().isEmpty() && textDetailsOperations.getText().isEmpty()) || (textTitreOperation.getText().isEmpty() && !textDetailsOperations.getText().isEmpty())) {
            System.out.println("il faudra mettre un pop-up car titre ou details non rempli");

        } else if (textDetailsOperations.getText().isEmpty() && textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.ajouterOperations(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textTitreOperation.getText(), textDetailsOperations.getText());
            System.out.println("2");
        } 

        //On ajoute un resultat seul
        else if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.ajouterResultat(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textResultats.getText());
            System.out.println("3");
        } 


        // On ajoute une prescription seule
        else if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty() && textResultats.getText().isEmpty()) {
            sejourCourant.ajouterPrescription(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textPrescriptions.getText());
            System.out.println("4");
        } 

        //On ajoute une observation et une operation
        else if (textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), "", "");
            System.out.println("5");
        }
        
        //On ajoute une observation et un resultat
        else if (textResultats.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", textResultats.getText(), "");
            System.out.println("6");
        }
        
        //On ajoute une observation et un prescription
        else if (textResultats.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", "", textPrescriptions.getText());
            System.out.println("7");
        } 

        //On ajoute une operation et un resultat
        else if ((textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), "");
            System.out.println("8");
        } 

        //On ajoute une operation et une prescription
        else if (textResultats.getText().isEmpty() && textObservations.getText().isEmpty()) {
            System.out.println("9");
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), "", textPrescriptions.getText());
        } 

        //On ajoute une prescription et un resultat
        else if ((textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", "", "", textResultats.getText(), textPrescriptions.getText());
            System.out.println("10");
        } 

        // On ajoute une observation, une operation, un resultat
        else if (textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), "");
            System.out.println("11");
        }
        
        // On ajoute une observation, une operation, une prescription
        else if (textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), "", textPrescriptions.getText());
            System.out.println("12");
        } 

        // On ajoute une observation, un resultat, une prescription
        else if (textPrescriptions.getText().isEmpty()) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", textResultats.getText(), textPrescriptions.getText());
            System.out.println("13");
        }
        
        //On ajoute une operation, un resultat et une prescription
        else if (textPrescriptions.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {
            sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), textPrescriptions.getText());
            System.out.println("14");
        } 

        //On complete l'integralité du sejour
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

        PatientSelection = jList1.getSelectedValue().toString();
        //cliquer sur le bouton "Suivant" ouvre l'onglet où le médecin peut compléter les données du patient courant
        interfacePH.setSelectedIndex(1);
        jToggleButton1.setSelected(false);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //Renvoie l'id du medecin selectionné = medecin receveur
        String selection = jList2.getSelectedValue();
        String idp = ph.iPPMedecinListe(selection);
        System.out.println("IPP MEDECIN EST : " + idp);
       
        sejourCourant.ajouterPrestation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)),perso.getIdMed(), idp, textPrestation.getText());
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new Connexion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

//   

    private void textPrestationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPrestationActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_textPrestationActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

//supprimer
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // edition de la lettre de sortie  = cloture du sejour
        
        sejourCourant.editerLettreDeSortie(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection),textLettreDeSortie.getText());
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton6ActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed1
        // TODO add your handling code here:
        new ChangerMDP(this.perso).setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed1

    private void voirLesInfosPatient(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voirLesInfosPatient
        interfacePH.setSelectedIndex(2);
    }//GEN-LAST:event_voirLesInfosPatient

    private void panelAccueilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAccueilMouseClicked
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

            if (sejourBluff.sejourEnCours(idDernierSejour)) {
                DLM.addElement(element);
            }
        jList1.setModel(DLM);
        jList1.repaint();}
        
    }//GEN-LAST:event_panelAccueilMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        DefaultListModel DLM = new DefaultListModel();
        String[] anesthesie = ph.afficherListePHAnes();
        String[] radiologue = ph.afficherListePHRadio();
        if (jComboBox1.getSelectedItem().equals(Specialite.ANESTHESIE)) {
            for (int i = 0; i < anesthesie.length; i++) {
                DLM.addElement(anesthesie[i]);
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

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        // TODO add your handling code here:
        jTextField5.setText(jList2.getSelectedValue().toString());
    }//GEN-LAST:event_jList2ValueChanged

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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTextField jTextField5;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTree jTree2;
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
