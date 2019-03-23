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
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import nf.DMA;
//import nf.HL7;
import nf.Lit;
import nf.Localisation;
import nf.Orientation;
import nf.Patient;
import nf.RechercherInfo;
import nf.Sejour;
import nf.Service;
import nf.Specialite;

/**
 *
 * @author poite
 */
public class Radiologue extends javax.swing.JFrame {

    nf.PersonnelMedical perso;
    nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
    DefaultListModel DLM = new DefaultListModel();
    ArrayList<Patient> listePatient;
    nf.Patient patient = new nf.Patient("bluff", "bluff");
    String prestationSelection;
    private DefaultTreeCellRenderer tCellRenderer = new DefaultTreeCellRenderer();
    String PatientSelection;

    ArrayList<String> listePrestation;

    /**
     * Creates new form Radiologue
     * @param p
     */
    public Radiologue(nf.PersonnelMedical p) {
        initComponents();
        setSize(900, 800);
//        HL7 hl7 = new HL7();
        this.perso = p;
        String s = "Dr. " + p.getNom() + " " + p.getPrenom();
        System.out.println(s);
        nomRadiologue.setText(s);
        spe.setText(perso.getSpecialite().toString());
        listePatient = perso.afficherListePatientPrestation(perso.getIdMed());
        System.out.println(listePatient);
         System.out.println("6------------6");
         //hl7.ecouterMsg();
        System.out.println("6------------6");
        System.out.println(listePatient.size());
        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
            String ipp = patient.ippPatientListe(element);
        System.out.println("ICI STP ===================2222"+element);
            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 12, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.CLINIQUE);
            String idDernierSejour = ph.idSejourPatientSelection(ipp);
            listePrestation = perso.afficherDatePrestation(perso.getIdMed(), idDernierSejour);
            
            for (int j = 0; j < listePrestation.size(); j++) {
                
                if (sejourBluff.sejourEnCours(idDernierSejour) && !sejourCourant.prestationRealisee(listePrestation.get(j),idDernierSejour)){
                   
                    DLM.addElement(element + "         " + perso.afficherPrestation(listePrestation.get(j),idDernierSejour));
                    listePrestation.remove(j);
                    j--;
                 
                }
            }

        }
        jList1.setModel(DLM);
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

            if (isaisie == listedateSaisie.size()) {
                while (iloc < listeDateLoc.size()) {
                    loc = listeDateLoc.get(iloc);
                    localisation = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeLoctoString(loc, listeIdSejours.get(i)));
                    sejour.add(localisation);
                    iloc++;
                }
            } else if (iloc == listeDateLoc.size()) {
                while (isaisie < listedateSaisie.size()) {
                    dsaisie = listedateSaisie.get(isaisie);
                    saisie = new javax.swing.tree.DefaultMutableTreeNode(sejourCourant.listeSaisietoString(dsaisie, listeIdSejours.get(i)));
                    sejour.add(saisie);

                    listeInfos = sejourCourant.listeInfosRadio(dsaisie, listeIdSejours.get(i));

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

            if (sejourCourant.sejourEnCours(listeIdSejours.get(i))) {
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

        tab = new javax.swing.JTabbedPane();
        accueil = new javax.swing.JPanel();
        nomRadiologue = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        spe = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pres = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<String>();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        DM = new javax.swing.JTree();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textCR = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        tab.setBackground(new java.awt.Color(255, 255, 255));
        tab.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tab.setPreferredSize(new java.awt.Dimension(700, 600));
        tab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });

        accueil.setBackground(new java.awt.Color(255, 255, 255));
        accueil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accueilMouseClicked(evt);
            }
        });

        nomRadiologue.setBackground(new java.awt.Color(0, 153, 153));
        nomRadiologue.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        nomRadiologue.setOpaque(true);

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("Déconnexion");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        spe.setBackground(new java.awt.Color(0, 153, 153));
        spe.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        spe.setText(" ");
        spe.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Prestation à effectuer :");

        pres.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        pres.setText(" ");
        pres.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Suivant");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste des prestations", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        javax.swing.GroupLayout accueilLayout = new javax.swing.GroupLayout(accueil);
        accueil.setLayout(accueilLayout);
        accueilLayout.setHorizontalGroup(
            accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accueilLayout.createSequentialGroup()
                .addGroup(accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(accueilLayout.createSequentialGroup()
                        .addGroup(accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomRadiologue, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spe, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(accueilLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(accueilLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(270, 270, 270)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pres, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(102, 102, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, accueilLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(91, 91, 91))
        );
        accueilLayout.setVerticalGroup(
            accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accueilLayout.createSequentialGroup()
                .addGroup(accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(accueilLayout.createSequentialGroup()
                        .addComponent(nomRadiologue, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(spe, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pres, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addGap(39, 39, 39)
                .addComponent(jLabel14)
                .addContainerGap())
        );

        tab.addTab("ACCUEIL", accueil);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText(" ");

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }
        jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Médical Administratif", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jTree1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane3.setViewportView(jTree1);

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            DM.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }
        DM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Médical", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        DM.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jScrollPane4.setViewportView(DM);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel13))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel13))
        );

        tab.addTab("INFORMATIONS PATIENT", jPanel3);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        textCR.setColumns(20);
        textCR.setRows(5);
        jScrollPane1.setViewportView(textCR);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Compte rendu radiologique :");

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(44, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        tab.addTab("COMPTE RENDU ", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new Connexion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();
            tab.setSelectedIndex(1);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        if (!jList1.isSelectionEmpty()) {

            PatientSelection = jList1.getSelectedValue().toString();
            int retour = JOptionPane.showConfirmDialog(this, "Voulez-vous rajouter ces informations dans le dossier médical ?", "ATTENTION", JOptionPane.OK_CANCEL_OPTION);
            if (retour == 0) {
                System.out.println("HEEEEEE");
                String lecture = jList1.getSelectedValue();
                prestationSelection = perso.prestationPatientListe(lecture);

                nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.ACCUEIL);

                sejourCourant.ajouterCompteRendu(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), prestationSelection, textCR.getText());
                textCR.setText("");
                JOptionPane.showMessageDialog(this, "Compte-rendu ajouté au dossier médical", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        if (jList1.isSelectionEmpty()) {

            pres.setText("");
        } else {
            String lecture = jList1.getSelectedValue();
            prestationSelection = perso.prestationPatientListe(lecture);
            initRenderer();
            buildTree();
            buildTree1();
            pres.setText(prestationSelection);
        }


    }//GEN-LAST:event_jList1ValueChanged

    private void tabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_tabMouseClicked

    private void accueilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accueilMouseClicked
        // TODO add your handling code here:
        DLM.clear();
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
                  
                    DLM.addElement(element + "         " + perso.afficherPrestation(listePrestation.get(j),idDernierSejour));
                    listePrestation.remove(j);
                    j--;
                    
                }
            }

        }
        jList1.setModel(DLM);
        jList1.repaint();

    }//GEN-LAST:event_accueilMouseClicked

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
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Radiologue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //   new Radiologue().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree DM;
    private javax.swing.JPanel accueil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTree jTree1;
    private javax.swing.JLabel nomRadiologue;
    private javax.swing.JLabel pres;
    private javax.swing.JLabel spe;
    private javax.swing.JTabbedPane tab;
    private javax.swing.JTextArea textCR;
    // End of variables declaration//GEN-END:variables
}
