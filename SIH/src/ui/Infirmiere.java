/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import static java.awt.image.ImageObserver.ABORT;
import static java.awt.image.ImageObserver.ERROR;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import nf.DMA;
import nf.Lit;
import nf.Localisation;
import nf.Orientation;
import nf.RechercherInfo;
import nf.Sejour;
import nf.Service;
import nf.Specialite;

/**
 *
 * @author poite
 */
public class Infirmiere extends javax.swing.JFrame {
nf.PersonnelMedical perso;
 String PatientSelection = "";
    Boolean persoUrg = false;
     DefaultListModel DLM = new DefaultListModel();
    DefaultListModel DLM_recherche;
    nf.PH ph = new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
    nf.Patient patient = new nf.Patient("bluff", "bluff");
    nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
    ArrayList<nf.Patient> listePatient;
    private DefaultTreeCellRenderer tCellRenderer = new DefaultTreeCellRenderer();
    private DefaultMutableTreeNode racine;
    /**
     * Creates new form Infirmiere
     * @param p
     */
    public Infirmiere(nf.PersonnelMedical p) {
        initComponents();
        this.pack();
        Infirmiere.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(Infirmiere.MAXIMIZED_BOTH);
        setDefaultCloseOperation(Infirmiere.DO_NOTHING_ON_CLOSE);
        
        this.perso = p;
        if (perso.getSpecialite().equals(Specialite.URGENCE)) {
            persoUrg = true;
        }
        listePatient = secrMed.afficherListePatientParService(perso.getSpecialite());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, ERROR, ABORT, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.CLINIQUE);
            String ipp = patient.ippPatientListe(element);

            String idDernierSejour = ph.idSejourPatientSelection(ipp);
           
            if (sejourBluff.sejourEnCours(idDernierSejour)) {
                DLM.addElement(element);
            }
            jList1.setModel(DLM);
            jList1.repaint();
        }
    }

     /**
     *
     * @param ae
     */
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
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
             
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

        nf.Patient pat = new nf.Patient("bluff", "bluff");
        DMA dma = new DMA(pat);
        Localisation locBluff = new Localisation(Specialite.ANESTHESIE, Orientation.NORD, 5, 3, Lit.FENETRE);
        Sejour sej = new Sejour("dSejour", "idPatient", "idphReferan", locBluff);
        nf.PH ph = new nf.PH("bono", "jean", "hello", "i am", "here", Specialite.ANESTHESIE, Service.MEDICO_TECHNIQUE);

        if (!jList1.isSelectionEmpty()) {
            String IDSej = ph.idSejourPatientSelection(pat.ippPatientListe(jList1.getSelectedValue().toString()));
            javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Identité patient");
            String lecture1 = jList1.getSelectedValue().toString();
            String lecture2 = sej.AfficherPATIENT(pat.ippPatientListe(jList1.getSelectedValue().toString()));
            String lectureLocalisation = sej.afficherLOCALISATION(sej.AfficherIDSejour(pat.ippPatientListe(jList1.getSelectedValue().toString())));
            String affichageDma = sej.infoDMA(sej.AfficherIDSejour(pat.ippPatientListe(jList1.getSelectedValue().toString())));
            String personneConfiance = pat.afficherPersonneConfiance(pat.ippPatientListe(jList1.getSelectedValue().toString()));

            if (IDSej.equals("0000")) {
                javax.swing.tree.DefaultMutableTreeNode treeNodeTEST = new javax.swing.tree.DefaultMutableTreeNode("Le patient n'a pas encore été admis");
                treeNode1.add(treeNodeTEST);
            } else {

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
                if (localisation[1].contains("Consultation")) {
                    javax.swing.tree.DefaultMutableTreeNode treeNodeconsultation = new javax.swing.tree.DefaultMutableTreeNode("Type de sejour : Consultation");
                    javax.swing.tree.DefaultMutableTreeNode treeNodeservice = new javax.swing.tree.DefaultMutableTreeNode("Service :" + localisation[2]);
                    treeNodeLocalisation.add(treeNodeconsultation);
                    treeNodeLocalisation.add(treeNodeservice);
                } else {

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
                    treeNodeLocalisation.add(treeNode24);
                }

                treeNode1.add(treeNodeLocalisation);

            }
            jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
            jTree1.setCellRenderer(this.tCellRenderer);
        }
    }

    //Affichage du DMA pour le patient des urgences

    /**
     *
     */
    public void buildTree2() {
        this.tCellRenderer.setClosedIcon(null);
        this.tCellRenderer.setOpenIcon(null);
        this.tCellRenderer.setLeafIcon(null);

        nf.Patient pat = new nf.Patient("bluff", "bluff");
        DMA dma = new DMA(pat);
        Localisation locBluff = new Localisation(Specialite.ANESTHESIE, Orientation.NORD, 5, 3, Lit.FENETRE);
        Sejour sej = new Sejour("dSejour", "idPatient", "idphReferan", locBluff);
        nf.PH ph = new nf.PH("bono", "jean", "hello", "i am", "here", Specialite.ANESTHESIE, Service.MEDICO_TECHNIQUE);

        if (!jList1.isSelectionEmpty()) {
            String IDSej = ph.idSejourPatientSelection(pat.ippPatientListe(jList1.getSelectedValue().toString()));
            javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Identité patient");
            String lecture1 = jList1.getSelectedValue().toString();
            String lecture2 = sej.AfficherPATIENT(pat.ippPatientListe(jList1.getSelectedValue().toString()));
            String lectureLocalisation = sej.afficherLOCALISATION(sej.AfficherIDSejour(pat.ippPatientListe(jList1.getSelectedValue().toString())));
            String affichageDma = sej.infoDMA(sej.AfficherIDSejour(pat.ippPatientListe(jList1.getSelectedValue().toString())));
            String personneConfiance = pat.afficherPersonneConfiance(pat.ippPatientListe(jList1.getSelectedValue().toString()));

            if (IDSej.equals("0000")) {
                javax.swing.tree.DefaultMutableTreeNode treeNodeTEST = new javax.swing.tree.DefaultMutableTreeNode("Le patient n'a pas encore été admis");
                treeNode1.add(treeNodeTEST);
            } else {

//afficher infos patient
                System.out.println("selected value : " + jList1.getSelectedValue().toString());
                String[] result = lecture1.split("\\s\\s\\s\\s\\s\\s\\s\\s\\s");
                javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Nom : " + result[0]);
                javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Prenom : " + result[1]);
                javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Date de naissance : " + result[2]);
                treeNode1.add(treeNode2);
                treeNode1.add(treeNode3);
                treeNode1.add(treeNode4);

                //afficher le sejour en cours 
                String[] IDSejour = IDSej.split("\\s");
                javax.swing.tree.DefaultMutableTreeNode treeNode13 = new javax.swing.tree.DefaultMutableTreeNode("Sejour En Cours :");
                javax.swing.tree.DefaultMutableTreeNode treeNode8 = new javax.swing.tree.DefaultMutableTreeNode("ID_Sejour : " + IDSejour[0]);
                treeNode13.add(treeNode8);
                javax.swing.tree.DefaultMutableTreeNode treeNode14 = new javax.swing.tree.DefaultMutableTreeNode("PH Referent : " + ph.afficherPHR(pat.ippPatientListe(jList1.getSelectedValue().toString())));
                treeNode13.add(treeNode14);
                treeNode1.add(treeNode13);

//Afficher localisation
                String[] localisation = lectureLocalisation.split("\\s");
                javax.swing.tree.DefaultMutableTreeNode treeNodeLocalisation = new javax.swing.tree.DefaultMutableTreeNode("Localisation (En Cours)");
                if (localisation[1].contains("Consultation")) {
                    javax.swing.tree.DefaultMutableTreeNode treeNodeconsultation = new javax.swing.tree.DefaultMutableTreeNode("Type de sejour : Consultation");
                    javax.swing.tree.DefaultMutableTreeNode treeNodeservice = new javax.swing.tree.DefaultMutableTreeNode("Service :" + localisation[2]);
                    treeNodeLocalisation.add(treeNodeconsultation);
                    treeNodeLocalisation.add(treeNodeservice);
                } else {

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
                    treeNodeLocalisation.add(treeNode24);
                }

                treeNode1.add(treeNodeLocalisation);

            }
            jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
            jTree1.setCellRenderer(this.tCellRenderer);
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

        jPanel1 = new javax.swing.JPanel();
        interfaceInfirmiere = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        dateR = new javax.swing.JFormattedTextField();
        prenomR = new javax.swing.JTextField();
        nomR = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        DM = new javax.swing.JTree();
        jScrollPane4 = new javax.swing.JScrollPane();
        textObservations = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        textTitreOperation = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        textDetailsOperations = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setInheritsPopupMenu(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1722, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        interfaceInfirmiere.setBackground(new java.awt.Color(255, 255, 255));
        interfaceInfirmiere.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        interfaceInfirmiere.setPreferredSize(new java.awt.Dimension(900, 800));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 800));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Liste de patients", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        if(jList1.isSelectionEmpty()){ javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient"); jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3)); }
        jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Administratif", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jScrollPane2.setViewportView(jTree1);

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Déconnexion");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton3.setText("Changer mot de passe");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        dateR.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        jButton5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton5.setText("OK");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("Rechercher");

        jToggleButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jToggleButton1.setText("Suivant");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        jLabel7.setBackground(new java.awt.Color(0, 153, 153));
        jLabel7.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Nom :");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Prénom :");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setText("Date de naissance :");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(nomR, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(prenomR, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dateR, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButton5)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(124, 124, 124)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1237, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3)
                                    .addComponent(jButton2))
                                .addGap(23, 23, 23))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2)))
                        .addGap(23, 23, 23))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel11))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dateR, nomR, prenomR});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jButton3))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prenomR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jLabel11))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton2, jButton3});

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dateR, nomR, prenomR});

        interfaceInfirmiere.addTab("ACCUEIL", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            DM.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }
        DM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Médical", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
        jScrollPane3.setViewportView(DM);

        textObservations.setColumns(20);
        textObservations.setRows(5);
        jScrollPane4.setViewportView(textObservations);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Observations :");

        textTitreOperation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 153));
        jLabel3.setText("Opérations");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Titre :");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Détails :");

        textDetailsOperations.setColumns(20);
        textDetailsOperations.setRows(5);
        jScrollPane5.setViewportView(textDetailsOperations);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(148, 148, 148)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(textTitreOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(162, 162, 162)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addContainerGap(597, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textTitreOperation, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                .addComponent(jLabel12))
        );

        interfaceInfirmiere.addTab("DOSSIER MEDICAL", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(247, Short.MAX_VALUE))
            .addComponent(interfaceInfirmiere, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(interfaceInfirmiere, javax.swing.GroupLayout.PREFERRED_SIZE, 1051, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new ChangerMDP(this.perso).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         if (!jList1.isSelectionEmpty()) {
            try {
                PatientSelection = jList1.getSelectedValue().toString();

                //On ajoute une observation seule
                if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {
                    if (sejourCourant.ajouterObservation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("1");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }
                } //On ajoute une operation seule
                //il faut que titre ET details soient remplis
                else if ((!textTitreOperation.getText().isEmpty() && textDetailsOperations.getText().isEmpty()) || (textTitreOperation.getText().isEmpty() && !textDetailsOperations.getText().isEmpty())) {
                    JOptionPane.showMessageDialog(this, "Renseignez les champs titre ET details", "ERREUR", JOptionPane.ERROR_MESSAGE);

                } else if (textDetailsOperations.getText().isEmpty()) {
                    if (sejourCourant.ajouterOperations(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textTitreOperation.getText(), textDetailsOperations.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("2");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }}
                    
                     //On ajoute une observation et une operation
                     else{
                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), "", "").equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("5");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                }
            
               textObservations.setText("");
                textTitreOperation.setText("");
                textDetailsOperations.setText("");
                initRenderer();
                buildTree();
                  } catch (Exception ex) {

                System.out.println(ex);
            }}
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();
            initRenderer();
            buildTree();
            if (persoUrg == false) {
                buildTree1();
            } else {
                buildTree2();
            }}
    }//GEN-LAST:event_jList1ValueChanged

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
         DLM.clear();
        listePatient = secrMed.afficherListePatientParService(perso.getSpecialite());

        for (int i = 0; i < listePatient.size(); i++) {
            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, ERROR, ABORT, Lit.PORTE);
            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.CLINIQUE);
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
        if (persoUrg == false) {
            buildTree1();
        } else {
            buildTree2();
        }
        initRenderer();
        buildTree();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
          String element1 = "";
        String element2 = "";
        String element3 = "";
        ArrayList<nf.Patient> Lp = null;
        RechercherInfo inf = new RechercherInfo();
        String date = dateR.getText();
        DLM_recherche = new DefaultListModel();
        if (!nomR.getText().isEmpty() && !prenomR.getText().isEmpty() && dateR.getText() != null) {
            Lp = inf.patientServiceNomPrenomDate(perso.getSpecialite(), nomR.getText(), prenomR.getText(), date);

            for (int i = 0; i < Lp.size(); i++) {
                element1 = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM_recherche.addElement(element1);

            }

        }
        if (!nomR.getText().isEmpty() && !prenomR.getText().isEmpty() && dateR.getText().isEmpty()) {
            Lp = inf.patientServiceNomPrenom(perso.getSpecialite(), nomR.getText(), prenomR.getText());
            // DefaultListModel DLM = new DefaultListModel();
            for (int i = 0; i < Lp.size(); i++) {
                element2 = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM_recherche.addElement(element2);

            }
        }
        if (!nomR.getText().isEmpty() && prenomR.getText().isEmpty() && dateR.getText().isEmpty()) {
            Lp = inf.patientServiceNom(perso.getSpecialite(), nomR.getText());
            for (int i = 0; i < Lp.size(); i++) {
                element3 = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM_recherche.addElement(element3);
            }
        }
        jList1.setModel(DLM_recherche);


    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         new Connexion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
          if (!jList1.isSelectionEmpty()) {
            
            PatientSelection = jList1.getSelectedValue().toString();
            initRenderer();
            buildTree();
            if (persoUrg == false) {
                buildTree1();
            } else {
                buildTree2();
            }  }

        interfaceInfirmiere.setSelectedIndex(1);
        jToggleButton1.setSelected(false);
        
    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Infirmiere.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Infirmiere.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Infirmiere.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Infirmiere.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //   new Infirmiere().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree DM;
    private javax.swing.JFormattedTextField dateR;
    private javax.swing.JTabbedPane interfaceInfirmiere;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTextField nomR;
    private javax.swing.JTextField prenomR;
    private javax.swing.JTextArea textDetailsOperations;
    private javax.swing.JTextArea textObservations;
    private javax.swing.JTextField textTitreOperation;
    // End of variables declaration//GEN-END:variables
}
