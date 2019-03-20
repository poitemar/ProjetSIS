/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.event.ActionListener;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static java.util.Optional.empty;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import nf.DMA;
import nf.Lit;
import nf.Localisation;
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

    /**
     *
     */
    public static String selection = "";
    DefaultListModel DLM = new DefaultListModel();
    DefaultListModel DLM_recherche;
    nf.PH ph = new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
    nf.Patient patient = new nf.Patient("bluff", "bluff");
    nf.SecretaireMedicale secrMed = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    nf.Localisation locCourant = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1, 133, Lit.PORTE);
    nf.Sejour sejourCourant = new nf.Sejour("bluff", "bluff", "bluff", locCourant);
    private boolean phref = false;
    private DefaultTreeCellRenderer tCellRenderer = new DefaultTreeCellRenderer();
    private DefaultMutableTreeNode racine;
    DefaultListModel DLM_medecin;

    private int compteur = 0;

    /**
     * Creates new form PH
     * @param p
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

    /**
     *
     */
    public PH() {

    }

    /**
     *
     * @return
     */
    public String getPrenom() {
        compteur = getNom().length() + 9;
        System.out.println("le compteur est de : " + compteur);
        System.out.println("et la lettre à cet endroit est : " + selection.charAt(compteur));
        String prenom = "";
        while (!(String.valueOf(selection.charAt(compteur))).equals(" ")) {
            prenom = prenom + selection.charAt(compteur);
            compteur++;
        }
        compteur++;
        System.out.println("prenom = " + prenom);
        return prenom;

    }

    /**
     *
     * @return
     */
    public String getNom() {
        String nom = "";
        System.out.println("ceci est la séléction : " + selection);
        compteur = 0;
        while (!(String.valueOf(selection.charAt(compteur))).equals(" ")) {
            System.out.println(String.valueOf(selection.charAt(compteur)));
            System.out.println((String.valueOf(selection.charAt(compteur))).equals(" "));
            nom = nom + selection.charAt(compteur);
            compteur++;
        }
        compteur++;
        System.out.println("nom = " + nom);
        return nom;
    }

    /**
     *
     * @param nom
     * @param prenom
     * @return
     */
    public String getDateNaissance(String nom, String prenom) {
        compteur = getNom().length() + getPrenom().length() + 19;
        System.out.println("le compteur est : " + compteur);
        System.out.println("et la lettre à cet endroit est : " + selection.charAt(compteur));
        System.out.println("longueur de : " + selection.length());
        String dateNaissance = "";
        while (compteur < selection.length()) {
            System.out.println("compteur de : " + compteur);
            dateNaissance = dateNaissance + selection.charAt(compteur);
            compteur++;
        }
        System.out.println(" date naissance = " + dateNaissance);
        return dateNaissance;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
        jButton2 = new javax.swing.JButton();
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
        panelAccueil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelAccueilMouseClicked(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 153, 153));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(0, 153, 153));
        jLabel2.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton6.setText("Changer le mot de passe");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PPTHlogo.png"))); // NOI18N

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("Déplacer patient");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
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
                    .addComponent(jButton3)
                    .addComponent(jButton2))
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

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }
        else{
            jTree1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Administratif", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
            jTree1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        }
        jScrollPane6.setViewportView(jTree1);

        if(jList1.isSelectionEmpty()){
            javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Selectionnez un patient");
            DM.setModel(new javax.swing.tree.DefaultTreeModel(treeNode3));
        }
        else{
            DM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dossier Médical", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP, new java.awt.Font("Arial", 1, 14), new java.awt.Color(0, 153, 153))); // NOI18N
            DM.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        }
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
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
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
        jLabel10.setText("Titre :");

        textDetailsOperations.setColumns(20);
        textDetailsOperations.setRows(5);
        jScrollPane3.setViewportView(textDetailsOperations);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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

        jLabel20.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 153, 153));
        jLabel20.setText("Demande de prestation");

        jButton4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton4.setText("OK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
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

        jLabel17.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
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
            .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(interfacePH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interfacePH, javax.swing.GroupLayout.PREFERRED_SIZE, 601, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void interfacePHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_interfacePHMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_interfacePHMouseClicked


    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged
        // TODO add your handling code here:
        if (!jList2.isSelectionEmpty()) {
            medPres.setText(jList2.getSelectedValue().toString());
        }
    }//GEN-LAST:event_jList2ValueChanged


    private void completerSejour(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completerSejour

        if (!jList1.isSelectionEmpty()) {
            try {
                PatientSelection = jList1.getSelectedValue().toString();

                //On ajoute une observation seule
                if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
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

                } else if (textDetailsOperations.getText().isEmpty() && textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
                    if (sejourCourant.ajouterOperations(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textTitreOperation.getText(), textDetailsOperations.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("2");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute un resultat seul
                else if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
                    if (sejourCourant.ajouterResultat(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textResultats.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("3");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } // On ajoute une prescription seule
                else if (textTitreOperation.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty() && textResultats.getText().isEmpty()) {
                    if (sejourCourant.ajouterPrescription(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("4");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute une observation et une operation
                else if (textResultats.getText().isEmpty() && textPrescriptions.getText().isEmpty()) {
                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), "", "").equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("5");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute une observation et un resultat
                else if (textResultats.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", textResultats.getText(), "").equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("6");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute une observation et un prescription
                else if (textResultats.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", "", textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("7");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute une operation et un resultat
                else if ((textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty()) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), "").equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("8");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute une operation et une prescription
                else if (textResultats.getText().isEmpty() && textObservations.getText().isEmpty()) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), "", textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("9");

                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute une prescription et un resultat
                else if ((textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty()) && textObservations.getText().isEmpty()) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", "", "", textResultats.getText(), textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("10");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } // On ajoute une observation, une operation, un resultat
                else if (textPrescriptions.getText().isEmpty()) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), "").equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("11");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } // On ajoute une observation, une operation, une prescription
                else if (textPrescriptions.getText().isEmpty()) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), "", textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("12");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } // On ajoute une observation, un resultat, une prescription
                else if (textPrescriptions.getText().isEmpty()) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), "", "", textResultats.getText(), textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("13");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On ajoute une operation, un resultat et une prescription
                else if (textPrescriptions.getText().isEmpty() && (textDetailsOperations.getText().isEmpty() || textTitreOperation.getText().isEmpty())) {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), "", textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("14");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                } //On complete l'integralité du sejour
                else {

                    if (sejourCourant.completerSejour(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textObservations.getText(), textTitreOperation.getText(), textDetailsOperations.getText(), textResultats.getText(), textPrescriptions.getText()).equals("ERREUR")) {
                        JOptionPane.showMessageDialog(this, "Une erreur est survenue, Réeesayez", "ERREUR", JOptionPane.ERROR_MESSAGE);

                    } else {
                        System.out.println("15");
                        JOptionPane.showMessageDialog(this, "Informations ajoutées au dossier", "OK", JOptionPane.INFORMATION_MESSAGE);

                    }

                }

                textObservations.setText("");
                textTitreOperation.setText("");
                textDetailsOperations.setText("");
                textResultats.setText("");
                textPrescriptions.setText("");
                initRenderer();
                buildTree();
            } catch (Exception ex) {

                System.out.println(ex);
            }
        }

    }//GEN-LAST:event_completerSejour

    private void panelInformationsPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelInformationsPatientMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_panelInformationsPatientMouseClicked



    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();
            initRenderer();
            buildTree();
            new nouveauPatientArchive().setVisible(true);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            initRenderer();
            buildTree();
            buildTree1();
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


    private void voirLesInfosPatient(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_voirLesInfosPatient
        interfacePH.setSelectedIndex(2);
    }//GEN-LAST:event_voirLesInfosPatient


    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if (jList1.getSelectedValue() != null) {
            selection = jList1.getSelectedValue().toString();
            System.out.println(selection);
            System.out.println(jList1.getSelectedValue());
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if (phref == true) {
            int retour = JOptionPane.showConfirmDialog(this, "Voulez-vous cloturer le séjour du patient ?", "ATTENTION", JOptionPane.OK_CANCEL_OPTION);
            if (retour == 0) {
                sejourCourant.editerLettreDeSortie(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), patient.ippPatientListe(PatientSelection), textLettreDeSortie.getText());
            
            textLettreDeSortie.setText("");
            JOptionPane.showMessageDialog(this, "Le séjour du patient est cloturé", "OK", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vous n'êtes pas autorisé(e) à éditer la lettre de sortie", "ATTENTION : Vous n'êtes pas le PH référent", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        DLM_medecin = new DefaultListModel();
        String[] anesthesie = ph.afficherListePHAnes();
        String[] radiologue = ph.afficherListePHRadio();
        String[] hemato = ph.afficherListePHHemato();
        String[] anapatho = ph.afficherListePHAnapatho();
        String[] labo = ph.afficherListePHLabo();
        if (jComboBox1.getSelectedItem().equals(Specialite.ANESTHESIE)) {
            for (int i = 0; i < anesthesie.length; i++) {
                DLM_medecin.addElement(anesthesie[i]);
            }
            jList2.setModel(DLM_medecin);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.LABORATOIRE_ANALYSE)) {
            for (int i = 0; i < labo.length; i++) {
                DLM_medecin.addElement(labo[i]);
            }
            jList2.setModel(DLM_medecin);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.HEMATOLOGIE)) {
            for (int i = 0; i < hemato.length; i++) {
                DLM_medecin.addElement(hemato[i]);
            }
            jList2.setModel(DLM_medecin);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.ANAPATHOLOGIE)) {
            for (int i = 0; i < anapatho.length; i++) {
                DLM_medecin.addElement(anapatho[i]);
            }
            jList2.setModel(DLM_medecin);

        }
        if (jComboBox1.getSelectedItem().equals(Specialite.RADIOLOGIE)) {
            for (int i = 0; i < radiologue.length; i++) {
                DLM_medecin.addElement(radiologue[i]);

            }
            jList2.setModel(DLM_medecin);

        }

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        new Connexion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new ChangerMDP(this.perso).setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //Renvoie l'id du medecin selectionné = medecin receveur
        if (!jList2.isSelectionEmpty()) {
            String selection = jList2.getSelectedValue();
            String idp = ph.iPPMedecinListe(selection);
            System.out.println("IPP MEDECIN EST : " + idp);

            sejourCourant.ajouterPrestation(ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)), perso.getIdMed(), idp, textPrestation.getText());
            nf.HL7 hl7 = new nf.HL7();
            if(ph.speIDPH(idp)==Specialite.RADIOLOGIE){
                Patient p = patient.getPatient(patient.ippPatientListe(PatientSelection));
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA+"+p.getipp());
                //hl7.sendMessage(patient.getPatient(patient.ippPatientListe(PatientSelection)),2);
                   //JOptionPane.showMessageDialog(this, "Echec de connexion avec le serveur HL7", "ERREUR", JOptionPane.ERROR_MESSAGE);

               
               
            }
            textPrestation.setText("");
            medPres.setText("");
            DLM_medecin.clear();
            jList2.setModel(DLM_medecin);
            JOptionPane.showMessageDialog(this, "La demande de préstation a bien été envoyée", "", JOptionPane.INFORMATION_MESSAGE);

        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        // TODO add your handling code here:
        if (!jList1.isSelectionEmpty()) {
            PatientSelection = jList1.getSelectedValue().toString();
            initRenderer();
            buildTree();
            buildTree1();

            System.out.println("ID_SEJOUR ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;" + ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)));
            System.out.println(ph.estReferent(perso.getIdMed(), ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection))));

            //cliquer sur le bouton "Suivant" ouvre l'onglet où le médecin peut compléter les données du patient courant
            if (ph.estReferent(perso.getIdMed(), ph.idSejourPatientSelection(patient.ippPatientListe(PatientSelection)))) {
                phref = true;
                System.out.println("VRAI");
                jButton7.setEnabled(true);
                jButton3.setEnabled(true);
            } else {
                phref = false;
                jButton7.setEnabled(false);
                jButton3.setEnabled(false);
                System.out.println("FUAX");
            }

        }


    }//GEN-LAST:event_jList1ValueChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (!jList1.isSelectionEmpty()) {
        String ipp = patient.ippPatientListe(PatientSelection);
        new ModifierLocalisation(ipp).setVisible(true);}
        else{
            JOptionPane.showMessageDialog(this, "Veuillez selectionner un patient", "ATTENTION", JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String element1 = "";
        String element2 = "";
        String element3 = "";
        ArrayList<Patient> Lp = null;
        RechercherInfo inf = new RechercherInfo();
        String date = jFormattedTextField1.getText();
        DLM_recherche = new DefaultListModel();
        if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && jFormattedTextField1.getText() != null) {
            Lp = inf.patientServiceNomPrenomDate(perso.getSpecialite(), jTextField1.getText(), jTextField2.getText(), date);

            for (int i = 0; i < Lp.size(); i++) {
                element1 = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM_recherche.addElement(element1);

            }

        }
        if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty() && jFormattedTextField1.getText().isEmpty()) {
            Lp = inf.patientServiceNomPrenom(perso.getSpecialite(), jTextField1.getText(), jTextField2.getText());
            // DefaultListModel DLM = new DefaultListModel();
            for (int i = 0; i < Lp.size(); i++) {
                element2 = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM_recherche.addElement(element2);

            }
        }
        if (!jTextField1.getText().isEmpty() && jTextField2.getText().isEmpty() && jFormattedTextField1.getText().isEmpty()) {
            Lp = inf.patientServiceNom(perso.getSpecialite(), jTextField1.getText());
            for (int i = 0; i < Lp.size(); i++) {
                element3 = "" + Lp.get(i).getNom() + "         " + Lp.get(i).getPrenom() + "         " + Lp.get(i).getDateDeNaissance();
                DLM_recherche.addElement(element3);
            }
        }
        jList1.setModel(DLM_recherche);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void panelAccueilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelAccueilMouseClicked
        // TODO add your handling code here:
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
    }//GEN-LAST:event_panelAccueilMouseClicked

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
    private javax.swing.JButton jButton2;
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
