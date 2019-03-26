/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import nf.DMA;
import nf.Lit;
import nf.Localisation;
import nf.Orientation;
import nf.Patient;
import nf.Sejour;
import nf.Service;
import nf.Specialite;

/**
 *
 * @author poite
 */
public class Anesthesiste extends javax.swing.JFrame {

    
    nf.PersonnelMedical perso;
    ArrayList<Patient> listePatient;
     ArrayList<String> listePrestation;
    DefaultListModel DLM_prestation = new DefaultListModel();
    DefaultListModel DLM_medecin = new DefaultListModel();
    nf.Patient patient = new nf.Patient("bluff", "bluff");
     nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
     nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
    String PatientSelection;
     String prestationSelection;
     nf.PH ph = new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
      private  DefaultTreeCellRenderer tCellRenderer = new  DefaultTreeCellRenderer();
   
    /**
     * Creates new form Anesthesiste
     * @param p
     */
    public Anesthesiste(nf.PersonnelMedical p) {
        initComponents();
        this.pack();
        Anesthesiste.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(Anesthesiste.MAXIMIZED_BOTH);
        setDefaultCloseOperation(Anesthesiste.DO_NOTHING_ON_CLOSE);

        this.perso = p;
         String s = "Mme/M. " + p.getNom() + " " + p.getPrenom();
        nomMed.setText(s);
         listePatient = perso.afficherListePatientPrestation(perso.getIdMed());

        System.out.println(listePatient.size());
        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
            String ipp = patient.ippPatientListe(element);

            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 12, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.CLINIQUE);
            String idDernierSejour = ph.idSejourPatientSelection(ipp);
            listePrestation = perso.afficherDatePrestation(perso.getIdMed(), idDernierSejour);
            for (int j = 0; j < listePrestation.size(); j++) {
               
                if (sejourBluff.sejourEnCours(idDernierSejour) && !sejourCourant.prestationRealisee(listePrestation.get(j),idDernierSejour)){
                   
                    DLM_prestation.addElement(element + "         " + perso.afficherPrestation(listePrestation.get(j),idDernierSejour));
                    listePrestation.remove(j);
                    j--;
                    System.out.println("MARGAUX §§§§");
                }
            }

        }
        jList1.setModel(DLM_prestation);
        jList1.repaint();
    }

     
      
    

    private void initRenderer() {
        //Instanciation
       
        this.tCellRenderer.setClosedIcon(null);
        this.tCellRenderer.setOpenIcon(null);
        this.tCellRenderer.setLeafIcon(null);
    }

    /**
     *
     */
    public void buildTree() {
        DM.setCellRenderer(this.tCellRenderer);
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();

        }
        List<String> listeIdSejours = sejourCourant.listeSejour(patient.ippPatientListe(PatientSelection));
        List<String> listedateSaisie;
        List<String> listeDateLoc;
        List<String> listeLoc;
        TreeMap<String, String> listeInfos;
        String dateS = "";
        java.util.Date date1 = new java.util.Date();
        java.util.Date date2 = new java.util.Date();
        String dateS2 = "";
        javax.swing.tree.DefaultMutableTreeNode racine = new javax.swing.tree.DefaultMutableTreeNode("Mme/M." + patient.patientListe(PatientSelection));

        for (int i = 0; i < listeIdSejours.size(); i++) {
            listedateSaisie = sejourCourant.listeSaisie(listeIdSejours.get(i));
            listeDateLoc = sejourCourant.listeLoc(listeIdSejours.get(i));
            System.out.println("REGARDE PAR IC ++++++++++++++++++++++++++++     ");
            System.out.println(listedateSaisie);
            System.out.println(listeDateLoc);
            javax.swing.tree.DefaultMutableTreeNode sejour = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSejourtoString(listeIdSejours.get(i)));
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            int isaisie = 0, iloc = 0;

            String dsaisie = null, loc = null;

            javax.swing.tree.DefaultMutableTreeNode localisation, saisie;

            while (isaisie < listedateSaisie.size() && iloc < listeDateLoc.size()) {

                dsaisie = listedateSaisie.get(isaisie);
                loc = listeDateLoc.get(iloc);
                

                localisation = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeLoctoString(loc, listeIdSejours.get(i)));
                saisie = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSaisietoString(dsaisie, listeIdSejours.get(i)));

                try {
                    date1 = format.parse(dsaisie);
                    date2 = format.parse(loc);
                } catch (ParseException ex) {

                    Logger.getLogger(Sejour.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (date1.compareTo(date2) > 0) {

                    sejour.add(localisation);

                    iloc++;
                } else {
                    sejour.add(saisie);

                    listeInfos = sejourCourant.listeInfosAnesth(dsaisie, listeIdSejours.get(i));

                        for (Map.Entry mapentry : listeInfos.entrySet()) {
                            String[] tab = mapentry.getKey().toString().split("X");
                            for (int k = 1; k < tab.length; k++) {
                                javax.swing.tree.DefaultMutableTreeNode info = new javax.swing.tree.DefaultMutableTreeNode(tab[k] + " : " + mapentry.getValue());
                                saisie.add(info);
                        }
                    }
                    
                    isaisie++;
                }

            }
            
            if(isaisie == listedateSaisie.size()){
                while(iloc < listeDateLoc.size()){
                    loc = listeDateLoc.get(iloc);
                    localisation = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeLoctoString(loc, listeIdSejours.get(i)));
                    sejour.add(localisation);
                    iloc++;
                }
            }
            else if(iloc == listeDateLoc.size()){
                while(isaisie < listedateSaisie.size()){
                    dsaisie = listedateSaisie.get(isaisie);
                    saisie = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSaisietoString(dsaisie, listeIdSejours.get(i)));
                    sejour.add(saisie);
                    
                    listeInfos = sejourCourant.listeInfos(dsaisie, listeIdSejours.get(i));

                        for (Map.Entry mapentry : listeInfos.entrySet()) {
                            String[] tab = mapentry.getKey().toString().split("X");
                            for (int k = 1; k < tab.length; k++) {
                                javax.swing.tree.DefaultMutableTreeNode info = new javax.swing.tree.DefaultMutableTreeNode(tab[k] + " : " + mapentry.getValue());
                                saisie.add(info);
                        }
                    }
                    
                    isaisie++;
                }
            }
            
            if(sejourCourant.sejourEnCours(listeIdSejours.get(i))){
                 localisation = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeLoctoString("", listeIdSejours.get(i)));
                    sejour.add(localisation);
            }
            
            
            
            racine.add(sejour);

        }

        DefaultTreeModel arbre = new DefaultTreeModel(racine);
        DM.setModel(arbre);

    }

    /**
     *
     */
    public void buildTree1() {
        this.tCellRenderer.setClosedIcon(null);
        this.tCellRenderer.setOpenIcon(null);
        this.tCellRenderer.setLeafIcon(null);

        Patient pat = new Patient("bluff", "bluff");
        DMA dma = new DMA(pat);
        Localisation locBluff = new Localisation(Specialite.ANESTHESIE, Orientation.NORD, 5, 3, Lit.FENETRE);
        Sejour sej = new Sejour("dSejour", "idPatient", "idphReferan", locBluff);
        nf.PH ph = new nf.PH("bono", "jean", "hello", "i am", "here", Specialite.ANESTHESIE, Service.MEDICO_TECHNIQUE);

        String IDSej = ph.idSejourPatientSelection(pat.ippPatientListe(jList1.getSelectedValue().toString()));
         javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Identité patient");
        String lecture1 = jList1.getSelectedValue().toString();
        String lecture2 = sej.AfficherPATIENT(pat.ippPatientListe(jList1.getSelectedValue().toString()));
        String lectureLocalisation = sej.afficherLOCALISATION(sej.AfficherIDSejour(pat.ippPatientListe(jList1.getSelectedValue().toString())));
        String affichageDma = sej.infoDMA(sej.AfficherIDSejour(pat.ippPatientListe(jList1.getSelectedValue().toString())));
        String personneConfiance = pat.afficherPersonneConfiance(pat.ippPatientListe(jList1.getSelectedValue().toString()));
        
        if(IDSej.equals("0000")){
            javax.swing.tree.DefaultMutableTreeNode treeNodeTEST = new javax.swing.tree.DefaultMutableTreeNode("Le patient n'a pas encore été admis");
         treeNode1.add(treeNodeTEST);
        }
        else{
       

//afficher infos patient
        System.out.println("selected value : " + jList1.getSelectedValue().toString());
          String[] result = lecture1.split("\\s\\s\\s\\s\\s\\s\\s\\s\\s");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Nom : " + result[0]);
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Prenom : " + result[1]);
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Date de naissance : " + result[2]);
        treeNode1.add(treeNode2);
        treeNode1.add(treeNode3);
        treeNode1.add(treeNode4);

        //complement des infos patient 
        String[] patient = lecture2.split("\\s\\s\\s\\s\\s\\s\\s\\s\\s");
        javax.swing.tree.DefaultMutableTreeNode treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Sexe : " + patient[0]);
        javax.swing.tree.DefaultMutableTreeNode treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Adresse : " + patient[1]);
        javax.swing.tree.DefaultMutableTreeNode treeNode7 = new javax.swing.tree.DefaultMutableTreeNode("Telephone : " + patient[2]);
        treeNode1.add(treeNode5);
        treeNode1.add(treeNode6);
        treeNode1.add(treeNode7);

        //afficher le sejour en cours 
        String[] IDSejour = IDSej.split("\\s");
        javax.swing.tree.DefaultMutableTreeNode treeNode13 = new javax.swing.tree.DefaultMutableTreeNode("Sejour En Cours :");
        javax.swing.tree.DefaultMutableTreeNode treeNode8 = new javax.swing.tree.DefaultMutableTreeNode("ID_Sejour : " + IDSejour[0]);
        treeNode13.add(treeNode8);
        javax.swing.tree.DefaultMutableTreeNode treeNode14 = new javax.swing.tree.DefaultMutableTreeNode("PH Referent : " + ph.afficherPHR(pat.ippPatientListe(jList1.getSelectedValue().toString())));
        treeNode13.add(treeNode14);
        treeNode1.add(treeNode13);

        //Afficher les prestations 
        javax.swing.tree.DefaultMutableTreeNode treeNode9 = new javax.swing.tree.DefaultMutableTreeNode("Prestations");
        ArrayList<String> pres = sej.affichePrestation(IDSej);
        if (sej.sejourEnCours(IDSej) == true) {
            for (int i = 0; i < pres.size(); i++) {
                String prest = pres.get(i);
                System.out.println(pres.get(i));

                javax.swing.tree.DefaultMutableTreeNode treeNode10 = new javax.swing.tree.DefaultMutableTreeNode(prest);
                treeNode9.add(treeNode10);
                treeNode1.add(treeNode9);
            }
        }
        //   Lettre de sortie
        ArrayList<String> lettre = sej.afficherLettreSortie(pat.ippPatientListe(jList1.getSelectedValue().toString()));
        javax.swing.tree.DefaultMutableTreeNode treeNode11 = new javax.swing.tree.DefaultMutableTreeNode("Lettre De Sortie");
        for (int i = 0; i < lettre.size(); i++) {
            String letter = lettre.get(i);
            System.out.println(lettre.get(i));
            javax.swing.tree.DefaultMutableTreeNode treeNode12 = new javax.swing.tree.DefaultMutableTreeNode(letter);
            treeNode11.add(treeNode12);
            treeNode1.add(treeNode11);
        }

        // Afficher personne de confiance: 
        String[] persConf = personneConfiance.split("\n");
        javax.swing.tree.DefaultMutableTreeNode treeNodePersonneConfiance = new javax.swing.tree.DefaultMutableTreeNode("Personne De Confiance:");
        javax.swing.tree.DefaultMutableTreeNode treeNode16 = new javax.swing.tree.DefaultMutableTreeNode(persConf[0]);
        javax.swing.tree.DefaultMutableTreeNode treeNode17 = new javax.swing.tree.DefaultMutableTreeNode("Adresse :" + persConf[1]);
        javax.swing.tree.DefaultMutableTreeNode treeNode18 = new javax.swing.tree.DefaultMutableTreeNode("Telephone :" + persConf[2]);
        treeNodePersonneConfiance.add(treeNode16);
        treeNodePersonneConfiance.add(treeNode17);
        treeNodePersonneConfiance.add(treeNode18);
        treeNode1.add(treeNodePersonneConfiance);

//Afficher medecin référent 
        String[] localisation = lectureLocalisation.split("\\s");
        javax.swing.tree.DefaultMutableTreeNode treeNodeLocalisation = new javax.swing.tree.DefaultMutableTreeNode("Localisation (En Cours)");
       if(localisation[1].contains("Consultation")){
             javax.swing.tree.DefaultMutableTreeNode treeNodeconsultation = new javax.swing.tree.DefaultMutableTreeNode("Type de sejour : Consultation");
             javax.swing.tree.DefaultMutableTreeNode treeNodeservice = new javax.swing.tree.DefaultMutableTreeNode("Service :" + localisation[2]);
        treeNodeLocalisation.add(treeNodeconsultation);
        treeNodeLocalisation.add(treeNodeservice);
        }
       else{
           
       
        javax.swing.tree.DefaultMutableTreeNode treeNode19 = new javax.swing.tree.DefaultMutableTreeNode("Type de sejour : Hospitalisation ");
        javax.swing.tree.DefaultMutableTreeNode treeNode20 = new javax.swing.tree.DefaultMutableTreeNode("Service : " + localisation[2]);
        javax.swing.tree.DefaultMutableTreeNode treeNode21 = new javax.swing.tree.DefaultMutableTreeNode("Orientation : " + localisation[3]);
        javax.swing.tree.DefaultMutableTreeNode treeNode22 = new javax.swing.tree.DefaultMutableTreeNode("Chambre: " + localisation[4]);
        javax.swing.tree.DefaultMutableTreeNode treeNode23 = new javax.swing.tree.DefaultMutableTreeNode("Etage : " + localisation[5]);
        javax.swing.tree.DefaultMutableTreeNode treeNode24 = new javax.swing.tree.DefaultMutableTreeNode("Lit : " + localisation[6]);
        treeNodeLocalisation.add(treeNode20);
        treeNodeLocalisation.add(treeNode21);
        treeNodeLocalisation.add(treeNode22);
        treeNodeLocalisation.add(treeNode23);
        treeNodeLocalisation.add(treeNode24);}
//        treeNode8.add(treeNode13);
//        treeNode8.add(treeNode14);
        treeNode1.add(treeNodeLocalisation);
//
//        String[] adm = affichageDma.split("\\s");
//        javax.swing.tree.DefaultMutableTreeNode treeNode15 = new javax.swing.tree.DefaultMutableTreeNode("Information complémentaire");
//        javax.swing.tree.DefaultMutableTreeNode treeNode16 = new javax.swing.tree.DefaultMutableTreeNode("IDPH : " + adm[0]);
//        javax.swing.tree.DefaultMutableTreeNode treeNode17 = new javax.swing.tree.DefaultMutableTreeNode("ID Sejour : " + adm[1]);
//        javax.swing.tree.DefaultMutableTreeNode treeNode18 = new javax.swing.tree.DefaultMutableTreeNode("Date de Saisie : " + adm[2]);
//        javax.swing.tree.DefaultMutableTreeNode treeNode21 = new javax.swing.tree.DefaultMutableTreeNode("heure:de saise " + adm[3]);
//        javax.swing.tree.DefaultMutableTreeNode treeNode19 = new javax.swing.tree.DefaultMutableTreeNode("Lettre de Sortie : " + adm[4]);
//        javax.swing.tree.DefaultMutableTreeNode treeNode20 = new javax.swing.tree.DefaultMutableTreeNode("Titre Operation : " + adm[5]);
//        treeNode15.add(treeNode16);
//        treeNode15.add(treeNode17);
//        treeNode15.add(treeNode18);
//        treeNode15.add(treeNode21);
//        treeNode15.add(treeNode19);
//        treeNode15.add(treeNode20);
//        treeNode1.add(treeNode15);
        }
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setCellRenderer(this.tCellRenderer);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ANESTHESISTE = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        labelPrestation = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        nomMed = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane5 = new javax.swing.JScrollPane();
        DM = new javax.swing.JTree();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        textPrestation = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        medPres = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Resultat = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        ANESTHESISTE.setBackground(new java.awt.Color(255, 255, 255));
        ANESTHESISTE.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ANESTHESISTE.setPreferredSize(new java.awt.Dimension(1300, 900));
        ANESTHESISTE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ANESTHESISTEMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1300, 900));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Liste des prestations :");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("Prestation à effectuer :");

        labelPrestation.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setText("Déconnexion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setText("Suivant");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        nomMed.setBackground(new java.awt.Color(0, 153, 153));
        nomMed.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        nomMed.setOpaque(true);

        jButton5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton5.setText("Changer mot de passe");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(nomMed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 771, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton5)
                                    .addComponent(jButton1)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(31, 31, 31))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton5});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nomMed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(4, 4, 4)
                        .addComponent(jButton1)))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(labelPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2))
                .addGap(105, 105, 105)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        ANESTHESISTE.addTab("ACCUEIL", jPanel1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }
        jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Médical Administratif", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jTree1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane4.setViewportView(jTree1);

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            DM.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }
        DM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Médical", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        DM.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane5.setViewportView(DM);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ANESTHESISTE.addTab("INFORMATIONS PATIENT", jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 153));
        jLabel6.setText("Demande de prestation :");

        textPrestation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPrestationActionPerformed(evt);
            }
        });

        Specialite spe[]={Specialite.Sélectionner, Specialite.ANAPATHOLOGIE,Specialite.HEMATOLOGIE , Specialite.LABORATOIRE_ANALYSE};

        DefaultComboBoxModel model = new DefaultComboBoxModel(spe);
        jComboBox1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jComboBox1.setModel(model);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jList2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Liste des médecins :");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("à :");

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("OK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        medPres.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textPrestation)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(medPres, javax.swing.GroupLayout.DEFAULT_SIZE, 1056, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4)
                                        .addContainerGap())))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPrestation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(medPres, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4)
                            .addComponent(jLabel9))
                        .addGap(61, 61, 61)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        ANESTHESISTE.addTab("DEMANDE PRESTATION", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 153));
        jLabel4.setText("Prestation :");

        Resultat.setColumns(20);
        Resultat.setRows(5);
        jScrollPane2.setViewportView(Resultat);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Résultats :");

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Envoyer");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1553, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(14, 14, 14)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 428, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ANESTHESISTE.addTab("COMPLETER", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ANESTHESISTE, javax.swing.GroupLayout.DEFAULT_SIZE, 1588, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ANESTHESISTE, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new Connexion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
       if(!jList1.isSelectionEmpty()){
           PatientSelection = jList1.getSelectedValue().toString();
            ANESTHESISTE.setSelectedIndex(1);
       }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(this, "Voulez-vous ajouter un résultat de prestation ?", "Confirmation d'envoi", JOptionPane.QUESTION_MESSAGE);
        
       if(!jList1.isSelectionEmpty()){
           PatientSelection = jList1.getSelectedValue().toString();
            System.out.println("HEEEEEE");
        int retour =  JOptionPane.showConfirmDialog(this, "Voulez-vous rajouter ces informations dans le dossier médical ?", "ATTENTION", JOptionPane.OK_CANCEL_OPTION);
        if(retour==0){
            String lecture = jList1.getSelectedValue();
        
        prestationSelection = perso.prestationPatientListe(lecture);
        System.out.println("help"+prestationSelection);
        sejourCourant.ajouterResultatPrestation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(),patient.ippPatientListe(PatientSelection),prestationSelection, Resultat.getText());
       
        JOptionPane.showMessageDialog(this, "Le résutat a bien été ajouté au dossier médical", "", JOptionPane.INFORMATION_MESSAGE);
        Resultat.setText("");
       }}
       
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
       if (!jList2.isSelectionEmpty()){
        String selection = jList2.getSelectedValue();
        String idp = ph.iPPMedecinListe(selection);
        System.out.println("IPP MEDECIN EST : " + idp);
       
        sejourCourant.ajouterPrestation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)),perso.getIdMed(), idp, textPrestation.getText());
        textPrestation.setText("");
        medPres.setText("");
        DLM_medecin.clear();
        jList2.setModel(DLM_medecin);
         JOptionPane.showMessageDialog(this, "La demande de préstation a bien été envoyée", "", JOptionPane.INFORMATION_MESSAGE);

       }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void textPrestationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPrestationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textPrestationActionPerformed

   
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        if( jList1.isSelectionEmpty()){
            
            labelPrestation.setText("");
            
        }
        else {
            String lecture = jList1.getSelectedValue();
            PatientSelection = jList1.getSelectedValue().toString();
             prestationSelection = perso.prestationPatientListe(lecture);
             initRenderer();
             buildTree();
             buildTree1();
             
             labelPrestation.setText(prestationSelection);
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void ANESTHESISTEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ANESTHESISTEMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ANESTHESISTEMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
           
       
        String[] hemato = ph.afficherListePHHemato();
        String[] anapatho = ph.afficherListePHAnapatho();
        String[] labo = ph.afficherListePHLabo();
        if (jComboBox1.getSelectedItem().equals(Specialite.HEMATOLOGIE)) {
            DLM_medecin.clear();
            for (int i = 0; i < hemato.length; i++) {
                DLM_medecin.addElement(hemato[i]);
            }
            jList2.setModel(DLM_medecin);

        }
         if (jComboBox1.getSelectedItem().equals(Specialite.ANAPATHOLOGIE)) {
            DLM_medecin.clear();
            for (int i = 0; i < anapatho.length; i++) {
                DLM_medecin.addElement(anapatho[i]);
            }
            jList2.setModel(DLM_medecin);

        }
         if (jComboBox1.getSelectedItem().equals(Specialite.LABORATOIRE_ANALYSE)) {
              DLM_medecin.clear();
            for (int i = 0; i < labo.length; i++) {
                DLM_medecin.addElement(labo[i]);
            }
            jList2.setModel(DLM_medecin);

        }
        
                 
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        // TODO add your handling code here:
       if(!jList2.isSelectionEmpty()){
        medPres.setText(jList2.getSelectedValue().toString());}
    }//GEN-LAST:event_jList2ValueChanged

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        DLM_prestation.clear();
         listePatient = perso.afficherListePatientPrestation(perso.getIdMed());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
            String ipp = patient.ippPatientListe(element);

            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 12, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.CLINIQUE);
            String idDernierSejour = ph.idSejourPatientSelection(ipp);
            listePrestation = perso.afficherDatePrestation(perso.getIdMed(), idDernierSejour);
            for (int j = 0; j < listePrestation.size(); j++) {
                
                if (sejourBluff.sejourEnCours(idDernierSejour) && !sejourCourant.prestationRealisee(listePrestation.get(j),idDernierSejour)){
                   
                    DLM_prestation.addElement(element + "         " + perso.afficherPrestation(listePrestation.get(j),idDernierSejour));
                    listePrestation.remove(j);
                    j--;
                    System.out.println("MARGAUX §§§§");
                }
            }

        }
        jList1.setModel(DLM_prestation);
        jList1.repaint();

    }//GEN-LAST:event_jPanel1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        new ChangerMDP(this.perso).setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(Anesthesiste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Anesthesiste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Anesthesiste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Anesthesiste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane ANESTHESISTE;
    private javax.swing.JTree DM;
    private javax.swing.JTextArea Resultat;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel labelPrestation;
    private javax.swing.JLabel medPres;
    private javax.swing.JLabel nomMed;
    private javax.swing.JTextField textPrestation;
    // End of variables declaration//GEN-END:variables
}
