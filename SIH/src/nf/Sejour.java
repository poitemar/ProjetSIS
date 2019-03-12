/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author poite
 */
public class Sejour {

    private String idSejour;
    private String idPatient;
    private String idPHReferant;
    private List<String> listeObservations;
    private ArrayList<String> listePrescriptions;
    private List<String> listeTitreOperations;
    private List<String> listeDetailsOperations;
    private List<String> listeDeResultats;
    private List<String> listeDeCompteRenduRadio;
    private List<String> listeDateSaisie;
    private List<String> listePrestations;
    private Date dateSortie;
    private String lettreDeSortie;
    private Localisation localisation;
    private String observation;
    Connection con;
    ResultSet rs;
    Statement st;
    String IPP;
    String phRef;

    public Sejour(String idSejour, String idPatient, String idphReferant, Localisation localisation) {
        this.idSejour = idSejour;
        this.idPatient = idPatient;
        this.idPHReferant = idphReferant;
        this.localisation = localisation;
        listeObservations = new ArrayList<String>();
        listePrescriptions = new ArrayList<String>();
        listeTitreOperations = new ArrayList<String>();
        listeDetailsOperations = new ArrayList<String>();
        listeDeResultats = new ArrayList<String>();
        listeDeCompteRenduRadio = new ArrayList<String>();
        listeDateSaisie = new ArrayList<String>();
        listePrestations = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://mysql-dossmed.alwaysdata.net:3306/dossmed_bd", "dossmed", "projetsis"); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    //Completer la base de données avec les informations, cette fonction est pour un PH clinique
    public void completerSejour(String idSejour, String idMed, String Ipp, String observations, String titreOperations, String detailsOperations, String resultats, String prescriptions) {
        if (sejourEnCours(idSejour)) {
            String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";

            System.out.println(sql);
            try {
                PreparedStatement pstm = con.prepareStatement(sql);
                Date maDate;
                SimpleDateFormat maDateLongue;
                maDate = new Date();
                maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                //on insere les donnees dans la classe ph_referent ce qui correspond a la requete 1
                pstm.setString(1, idMed);
                pstm.setString(2, Ipp);
                pstm.setString(3, idSejour);
                pstm.setString(4, maDateLongue.format(maDate));
                System.out.println(maDateLongue.format(maDate));
                pstm.setString(5, observations);
                System.out.println(observations);
                listeObservations.add(observations);
                pstm.setString(6, resultats);
                listeDeResultats.add(resultats);
                pstm.setString(7, "");

                pstm.setString(8, prescriptions);
                listePrescriptions.add(prescriptions);
                pstm.setString(9, titreOperations);
                listeTitreOperations.add(titreOperations);
                pstm.setString(10, detailsOperations);
                listeDetailsOperations.add(detailsOperations);
                pstm.setString(11, "");
                pstm.executeUpdate();

            } catch (Exception ex) {
                System.out.println(ex);

            }
        } else {
            System.out.println("Le séjour est clos et non modifiable");
        }
    }

    // getters et setters
    /**
     * @return the idSejour
     */
    public String getIdSejour() {
        return idSejour;
    }

    /**
     * @return the listeObservations
     */
    public List<String> getListeObservations() {
        return listeObservations;
    }

    /**
     * @param listeObservations the listeObservations to set
     */
    public void setListeObservations(List<String> listeObservations) {
        this.listeObservations = listeObservations;
    }

    /**
     * @return the listePrescriptions
     */
    public ArrayList<String> getListePrescriptions() {
        return listePrescriptions;
    }

    /**
     * @param listePrescriptions the listePrescriptions to set
     */
    public void setListePrescriptions(ArrayList<String> listePrescriptions) {
        this.listePrescriptions = listePrescriptions;
    }

    /**
     * @return the listeTitreOperations
     */
    public List<String> getListeTitreOperations() {
        return listeTitreOperations;
    }

    /**
     * @param listeTitreOperations the listeTitreOperations to set
     */
    public void setListeTitreOperations(List<String> listeTitreOperations) {
        this.listeTitreOperations = listeTitreOperations;
    }

    /**
     * @return the listeDetailsOperations
     */
    public List<String> getListeDetailsOperations() {
        return listeDetailsOperations;
    }

    /**
     * @param listeDetailsOperations the listeDetailsOperations to set
     */
    public void setListeDetailsOperations(List<String> listeDetailsOperations) {
        this.listeDetailsOperations = listeDetailsOperations;
    }

    /**
     * @return the listeDeResultats
     */
    public List<String> getListeDeResultats() {
        return listeDeResultats;
    }

    /**
     * @param listeDeResultats the listeDeResultats to set
     */
    public void setListeDeResultats(List<String> listeDeResultats) {
        this.listeDeResultats = listeDeResultats;
    }

    /**
     * @return the listeDeCompteRenduRadio
     */
    public List<String> getListeDeCompteRenduRadio() {
        return listeDeCompteRenduRadio;
    }

    /**
     * @param listeDeCompteRenduRadio the listeDeCompteRenduRadio to set
     */
    public void setListeDeCompteRenduRadio(List<String> listeDeCompteRenduRadio) {
        this.listeDeCompteRenduRadio = listeDeCompteRenduRadio;
    }

    /**
     * @return the dateEntree
     */
    /**
     * @return the dateSortie
     */
    public Date getDateSortie() {
        return dateSortie;
    }

    /**
     * @param dateSortie the dateSortie to set
     */
    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    /**
     * @return the lettreDeSortie
     */
    public String getLettreDeSortie() {
        return lettreDeSortie;
    }

    /**
     * @param lettreDeSortie the lettreDeSortie to set
     */
    public void setLettreDeSortie(String lettreDeSortie) {
        this.lettreDeSortie = lettreDeSortie;
    }

    /**
     * @return the localisation
     */
    public Localisation getLocalisation() {
        return localisation;
    }

    /**
     * @param localisation the localisation to set
     */
    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    // Fonctions a coder en dessous
    public void ajouterPrestation(String idSejour, String idMedD, String idMedR, String prestation) {
        this.listePrestations.add(prestation);
        {
            if (sejourEnCours(idSejour)) {
                String sql = "insert into prestations (ID_SEJOUR,DATE_SAISIE,ID_DR_DEMANDEUR,ID_DR_RECEVEUR,PRESTATION,DATE_PRESTATION) values (?,?,?,?,?,?)";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    Date maDate;
                    SimpleDateFormat maDateLongue;
                    maDate = new Date();
                    maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idSejour);
                    pstm.setString(2, maDateLongue.format(maDate));
                    System.out.println(maDateLongue.format(maDate));
                    pstm.setString(3, idMedD);
                    pstm.setString(4, idMedR);
                    pstm.setString(5, prestation);
                    pstm.setString(6, "");

                    pstm.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public void ajouterPrescription(String idSejour, String idMed, String Ipp, String prescription) {
        this.listePrescriptions.add(prescription);
        {
            if (sejourEnCours(idSejour)) {
                String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    Date maDate;
                    SimpleDateFormat maDateLongue;
                    maDate = new Date();
                    maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idMed);
                    pstm.setString(2, Ipp);
                    pstm.setString(3, idSejour);
                    pstm.setString(4, maDateLongue.format(maDate));
                    System.out.println(maDateLongue.format(maDate));
                    pstm.setString(5, "");

                    pstm.setString(6, "");

                    pstm.setString(7, "");

                    pstm.setString(8, prescription);
                    pstm.setString(9, "");
                    pstm.setString(10, "");
                    pstm.setString(11, "");
                    pstm.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public void ajouterObservation(String idSejour, String idMed, String Ipp, String observation) {
        this.listeObservations.add(observation);
        {
            if (sejourEnCours(idSejour)) {
                String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    Date maDate;
                    SimpleDateFormat maDateLongue;
                    maDate = new Date();
                    maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idMed);
                    pstm.setString(2, Ipp);
                    pstm.setString(3, idSejour);
                    pstm.setString(4, maDateLongue.format(maDate));
                    System.out.println(maDateLongue.format(maDate));
                    pstm.setString(5, observation);

                    pstm.setString(6, "");

                    pstm.setString(7, "");

                    pstm.setString(8, "");
                    pstm.setString(9, "");
                    pstm.setString(10, "");
                    pstm.setString(11, "");
                    pstm.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public void ajouterCompteRendu(String idSejour, String idMed, String Ipp, String prestation, String compterendu) {
        listeDeCompteRenduRadio.add(compterendu);
        {
            if (sejourEnCours(idSejour)) {
                Date maDate;
                SimpleDateFormat maDateLongue;
                maDate = new Date();
                maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";
                String sql2 = "update prestations set DATE_PRESTATION='" + maDateLongue.format(maDate) + "' where PRESTATION='" + prestation + "'";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    PreparedStatement pstm2 = con.prepareStatement(sql2);
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idMed);
                    pstm.setString(2, Ipp);
                    pstm.setString(3, idSejour);
                    pstm.setString(4, maDateLongue.format(maDate));

                    pstm.setString(5, "");

                    pstm.setString(6, "");

                    pstm.setString(7, "");

                    pstm.setString(8, "");
                    pstm.setString(9, "");
                    pstm.setString(10, "");
                    pstm.setString(11, compterendu);
                    pstm.executeUpdate();

                    pstm2.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public void ajouterResultat(String idSejour, String idMed, String Ipp, String resultat) {
        this.listeDeResultats.add(resultat);
        {
            if (sejourEnCours(idSejour)) {
                String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    Date maDate;
                    SimpleDateFormat maDateLongue;
                    maDate = new Date();
                    maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idMed);
                    pstm.setString(2, Ipp);
                    pstm.setString(3, idSejour);
                    pstm.setString(4, maDateLongue.format(maDate));
                    System.out.println(maDateLongue.format(maDate));
                    pstm.setString(5, "");

                    pstm.setString(6, resultat);

                    pstm.setString(7, "");

                    pstm.setString(8, "");
                    pstm.setString(9, "");
                    pstm.setString(10, "");
                    pstm.setString(11, "");
                    pstm.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public void ajouterResultatPrestation(String idSejour, String idMed, String Ipp, String prestation, String resultat) {
        this.listeDeResultats.add(resultat);
        {
            if (sejourEnCours(idSejour)) {
                Date maDate;
                SimpleDateFormat maDateLongue;
                maDate = new Date();
                maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";
                String sql2 = "update prestations set DATE_PRESTATION='" + maDateLongue.format(maDate) + "' where PRESTATION='" + prestation + "'";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    PreparedStatement pstm2 = con.prepareStatement(sql2);
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idMed);
                    pstm.setString(2, Ipp);
                    pstm.setString(3, idSejour);
                    pstm.setString(4, maDateLongue.format(maDate));
                    System.out.println(maDateLongue.format(maDate));
                    pstm.setString(5, "");

                    pstm.setString(6, resultat);

                    pstm.setString(7, "");

                    pstm.setString(8, "");
                    pstm.setString(9, "");
                    pstm.setString(10, "");
                    pstm.setString(11, "");
                    pstm.executeUpdate();
                    pstm2.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public void editerLettreDeSortie(String idSejour, String idMed, String Ipp, String lettre) {
        this.setLettreDeSortie(lettre);
        {
            if (sejourEnCours(idSejour)) {
                String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    Date maDate;
                    SimpleDateFormat maDateLongue;
                    maDate = new Date();
                    maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idMed);
                    pstm.setString(2, Ipp);
                    pstm.setString(3, idSejour);
                    pstm.setString(4, maDateLongue.format(maDate));
                    System.out.println(maDateLongue.format(maDate));
                    pstm.setString(5, "");

                    pstm.setString(6, "");

                    pstm.setString(7, lettre);

                    pstm.setString(8, "");
                    pstm.setString(9, "");
                    pstm.setString(10, "");
                    pstm.setString(11, "");
                    pstm.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est déja clos");
            }
        }
    }

    public void ajouterOperations(String idSejour, String idMed, String Ipp, String titreOperation, String detailOperation) {
        this.listeTitreOperations.add(titreOperation);
        this.listeDetailsOperations.add(detailOperation);
        {
            if (sejourEnCours(idSejour)) {
                String sql = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";

                System.out.println(sql);
                try {
                    PreparedStatement pstm = con.prepareStatement(sql);
                    Date maDate;
                    SimpleDateFormat maDateLongue;
                    maDate = new Date();
                    maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    pstm.setString(1, idMed);
                    pstm.setString(2, Ipp);
                    pstm.setString(3, idSejour);
                    pstm.setString(4, maDateLongue.format(maDate));
                    System.out.println(maDateLongue.format(maDate));
                    pstm.setString(5, "");

                    pstm.setString(6, "");

                    pstm.setString(7, "");

                    pstm.setString(8, "");
                    pstm.setString(9, titreOperation);
                    pstm.setString(10, detailOperation);
                    pstm.setString(11, "");
                    pstm.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    // Retourne true si le sejour correspondant à l'id rentré est en cours = si il n'y a pas de lettre de sorties
    public boolean sejourEnCours(String idSejour) {
        Boolean rep = true;
        try {

           String query = "select LETTRE_SORTIE from ph where ID_SEJOUR='" + idSejour + "'"; // la query à entrer pour accéder aux données de nos tables 
     //  String query = "select * from PATIENTS,PH,PH_REFERENT,localisation where (PATIENTS.IPP=PH.IPP_PATIENT) and (PATIENTS.IPP=PH_REFERENT.IPP) and PH.ID_SEJOUR='"+idSejour+'" and Localisation.service='"+spe+"' and PH.Lettre_Sortie='"+""+"'"
         System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next() && rep == true) {
                String lettreS = rs.getString("LETTRE_SORTIE");
                if (lettreS.isEmpty()) {

                } else {
                    rep = false;
                }
                System.out.println(lettreS);
                System.out.println(rep);
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return rep;
    }

    public boolean prestationRealisee(String prestation) {
        boolean rep = false;

        try {

            String query = "select DATE_PRESTATION from prestations where PRESTATION='" + prestation + "'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String date = rs.getString("DATE_PRESTATION");
                if (date.isEmpty()) {

                } else {
                    rep = true;
                }
                System.out.println(date);
                System.out.println(rep);
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return rep;
    }

    public String AfficherIDSejour(String IPP) {
        String idsej = "";
        try {
            String query = "select ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU from ph where IPP_PATIENT='" + IPP + "';";
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String IdPH = rs.getString("ID_PH");
                String Ipp = rs.getString("IPP_PATIENT");// pour avoir accès a la colonne de ma table 
                String IDsejour = rs.getString("ID_SEJOUR");
                String DateSaisie = rs.getString("DATE_SAISIE");
                //String etage = rs.getString("OBSERVATION");
                //String lit = rs.getString("RESULTAT");
                String lettreSortie = rs.getString("LETTRE_SORTIE");
                // String chambre = rs.getString("PRESCRIPTION");
                String titreOperation = rs.getString("TITRE_OPERATION");
                // String compteRendu = rs.getString("COMPTE_RENDU");
                idsej = IDsejour + "";
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(idsej);
        return idsej;
    }
    //afficher les infos administratives du patient

    public String AfficherPATIENT(String ipp) {
        String patient = "";

        try {
            String query = "select IPP,NOM,PRENOM,SEXE,DATENAISSANCE,ADRESSE,TELEPHONE from patients where IPP='" + ipp + "';";
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getString("IPP");
                String nom = rs.getString("nom");// pour avoir accès a la colonne de ma table 
                String prenom = rs.getString("prenom");
                String sexe = rs.getString("sexe");
                String datenaissance = rs.getString("datenaissance");
                String adresse = rs.getString("adresse");
                String telephone = rs.getString("telephone");

                patient = sexe + "         " + adresse + "         " + telephone;
            }

        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(patient);
        return patient;
    }
    //afficher la localisation du patient en cours 

    public String afficherLOCALISATION(String IDsej) {
        String local = "";
        try {
            String query = "select ID_SEJOUR,SERVICE,ORIENTATION,CHAMBRE,ETAGE,LIT from localisation where ID_SEJOUR='" + IDsej + "';";
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                IDsej = rs.getString("ID_SEJOUR");
                String service = rs.getString("SERVICE");// pour avoir accès a la colonne de ma table 
                String orientation = rs.getString("ORIENTATION");
                String chambre = rs.getString("CHAMBRE");
                String etage = rs.getString("ETAGE");
                String lit = rs.getString("LIT");

                local = IDsej + " " + service + " " + orientation + " " + chambre + " " + etage + " " + lit;
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(local);
        return local;
    }

    //recupere les infos Du DM qui doivent s'afficher dans un DMA (lettre de sortie et titre operation )
    public String infoDMA(String Idsej) {
        String sej = "";
        try {
            String query = "select ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU from ph where ID_SEJOUR='" + Idsej + "';";
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String IdPH = rs.getString("ID_PH");
                String Ipp = rs.getString("IPP_PATIENT");// pour avoir accès a la colonne de ma table 
                String IDsejour = rs.getString("ID_SEJOUR");
                String DateSaisie = rs.getString("DATE_SAISIE");

                String lettreSortie = rs.getString("LETTRE_SORTIE");
                String titreOperation = rs.getString("TITRE_OPERATION");

                sej = IdPH + " " + IDsejour + " " + DateSaisie + " " + lettreSortie + " " + titreOperation;
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(sej);
        return sej;
    }

    public ArrayList<String> affichePrestation(String ID_Sejour) {
        ArrayList<String> pres = new ArrayList<String>();
        try {
            String query = "select prestation from prestations where ID_SEJOUR='" + ID_Sejour + "'";
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                //System.out.println("hello");
                String prestation = rs.getString("prestation");

                pres.add(prestation);

//                    pres.add(i,prestation);
//                    System.out.println("hiii");
            }

        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(pres);
        return pres;
    }

    public ArrayList <String> afficherLettreSortie(String IPP) {
      ArrayList <String> lettreSortie = new ArrayList <String>(); 
        try {
            String query = " select LETTRE_SORTIE from ph where IPP_PATIENT='" + IPP + "'";
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
             
                
                String lettre = rs.getString("LETTRE_SORTIE");
                lettreSortie.add(lettre);
            }

        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(lettreSortie);
        return lettreSortie;
    }}
    
//    public  boolean sejourClos(String sejour){
//        boolean doss = true;
//      try {
//            String query = " select LETTRE_SORTIE from ph where LETTRE_SORTIE ='"+""+"' ";
//            
//            System.out.println(query);
//            rs = st.executeQuery(query);
//            while (rs.next()) {
//             String lettre = rs.getString("LETTRE_SORTIE");
//            if(lettre != ""){    
//                    doss = true ; 
//                    System.out.println("sejour clos");
//                
//            }}
//
//        } catch (Exception ex) {
//            System.out.println(ex);
//
//        }
//      return doss; 
//               }
//        
//    }

