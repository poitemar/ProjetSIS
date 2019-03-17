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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    ResultSet rs2;
    Statement st2;
    Statement st;
    ResultSet rs3;
    Statement st3;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
            st = con.createStatement();
            st2 = con.createStatement();
            st3 = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    // Ici on implémente la fonction qui retourne la liste des Id_Sejours d'un patient
    public List<String> listeSejour(String iPP) {
        List<String> liste = new ArrayList<String>();
        List<String> listeDate = new ArrayList<String>();
        try {
            String query = "select * from ph_referent where IPP='" + iPP + "'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {

                String date = rs.getString("DATE_CREATION_SEJOUR");

                listeDate.add(date);

            }

            Collections.sort(listeDate, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    // Write your logic here.
                    String dateString1 = s1;
                    String dateString2 = s2;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date1;
                    try {
                        date1 = format.parse(dateString1);
                    } catch (ParseException ex) {
                        date1 = new Date();
                        Logger.getLogger(Sejour.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Date date2;
                    try {
                        date2 = format.parse(dateString2);
                    } catch (ParseException ex) {
                        date2 = new Date();
                        Logger.getLogger(Sejour.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return date2.compareTo(date1);
                }
            }
            );
        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(listeDate);

        for (int i = 0; i < listeDate.size(); i++) {
            try {
                String query = "select * from ph_referent where DATE_CREATION_SEJOUR='" + listeDate.get(i) + "'"; // la query à entrer pour accéder aux données de nos tables 
                System.out.println(query);
                rs = st.executeQuery(query);
                while (rs.next()) {

                    String id = rs.getString("ID_SEJOUR");

                    liste.add(id);

                }
            } catch (Exception ex) {
                System.out.println(ex);

            }
        }
//                

        return liste;
    }

    //Méthode qui formate l'affichage des séjours dans le jTree
    public String listeSejourtoString(String idSejour) {
        String format = "";
        Boolean rep = sejourEnCours(idSejour);

        try {
            String query = "select * from ph_referent join ph using(ID_SEJOUR) where ID_SEJOUR='" + idSejour + "' AND LETTRE_SORTIE !='" + "" + "'"; // la query à entrer pour accéder aux données de nos tables 
            String query2 = "select * from ph_referent join ph using(ID_SEJOUR) where ID_SEJOUR='" + idSejour + "' AND LETTRE_SORTIE='" + "" + "' LIMIT 1"; // la query à entrer pour accéder aux données de nos tables 

            if (rep == false) {

                rs = st.executeQuery(query);
                while (rs.next()) {

                    String date = rs.getString("DATE_CREATION_SEJOUR");
                    String dateFin = rs.getString("DATE_SAISIE");
                    format = "Séjour du : " + date + " au: " + dateFin;

                }
            }
            if (rep == true) {

                rs = st.executeQuery(query2);
                while (rs.next()) {

                    String date = rs.getString("DATE_CREATION_SEJOUR");

                    format = "Séjour du : " + date + " (En cours)";

                }
            }

        } catch (Exception ex) {

            System.out.println(ex);

        }
        return format;

    }
    // Ici on implémente la fonction qui retourne la liste des localisations dans un séjour
    public List<String> listeLoc(String idSejour) {
        List<String> liste = new ArrayList<String>();
        List<String> listeDate = new ArrayList<String>();
        try {
            String query = "select * from historique_localisation where ID_SEJOUR='" + idSejour + "'"; // la query à entrer pour accéder aux données de nos tables 
            
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String date = rs.getString("DATE");
                String observation = rs.getString("SERVICE");
                String resultat = rs.getString("CODE_LOCALISATION");
                
                    listeDate.add(date);
                

            }

            Collections.sort(listeDate, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    // Write your logic here.
                    String dateString1 = s1;
                    String dateString2 = s2;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date1;
                    try {
                        date1 = format.parse(dateString1);
                    } catch (ParseException ex) {
                        date1 = new Date();
                        Logger.getLogger(Sejour.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Date date2;
                    try {
                        date2 = format.parse(dateString2);
                    } catch (ParseException ex) {
                        date2 = new Date();
                        Logger.getLogger(Sejour.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return date1.compareTo(date2);
                }
            }
            );
        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(listeDate);

        for (int i = 0; i < listeDate.size(); i++) {
            try {
                String query = "select * from historique_localisation where DATE='" + listeDate.get(i) + "'"; // la query à entrer pour accéder aux données de nos tables 
                System.out.println(query);
                rs = st.executeQuery(query);
                while (rs.next()) {

                    String date = rs.getString("DATE");

                    liste.add(date);

                }
            } catch (Exception ex) {
                System.out.println(ex);

            }
        }
//                

        return liste;
    }

    
    
    // Ici on implémente la fonction qui retourne la liste des saisies dans un séjour
    public List<String> listeSaisie(String idSejour) {
        List<String> liste = new ArrayList<String>();
        List<String> listeDate = new ArrayList<String>();
        try {
            String query = "select * from ph where ID_SEJOUR='" + idSejour + "'"; // la query à entrer pour accéder aux données de nos tables 

            rs = st.executeQuery(query);
            while (rs.next()) {
                String date = rs.getString("DATE_SAISIE");
                String observation = rs.getString("OBSERVATION");
                String resultat = rs.getString("RESULTAT");
                String lettre = rs.getString("LETTRE_SORTIE");
                String prescription = rs.getString("PRESCRIPTION");
                String titreOperation = rs.getString("TITRE_OPERATION");
                String operation = rs.getString("OPERATION");
                String CR = rs.getString("COMPTE_RENDU");
                if (observation.equals("") && resultat.equals("") && lettre.equals("") && prescription.equals("") && titreOperation.equals("") && operation.equals("") && CR.equals("")) {

                } else {
                    listeDate.add(date);
                }

            }

            Collections.sort(listeDate, new Comparator<String>() {
                public int compare(String s1, String s2) {
                    // Write your logic here.
                    String dateString1 = s1;
                    String dateString2 = s2;
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date1;
                    try {
                        date1 = format.parse(dateString1);
                    } catch (ParseException ex) {
                        date1 = new Date();
                        Logger.getLogger(Sejour.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Date date2;
                    try {
                        date2 = format.parse(dateString2);
                    } catch (ParseException ex) {
                        date2 = new Date();
                        Logger.getLogger(Sejour.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return date1.compareTo(date2);
                }
            }
            );
        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(listeDate);

        for (int i = 0; i < listeDate.size(); i++) {
            try {
                String query = "select * from ph where DATE_SAISIE='" + listeDate.get(i) + "'"; // la query à entrer pour accéder aux données de nos tables 
                System.out.println(query);
                rs = st.executeQuery(query);
                while (rs.next()) {

                    String date = rs.getString("DATE_SAISIE");

                    liste.add(date);

                }
            } catch (Exception ex) {
                System.out.println(ex);

            }
        }
//                

        return liste;
    }

//Méthode qui formate l'affichage des saisies dans le séjour dans le jtree
    public String listeSaisietoString(String dateSaisie, String idSejour) {
        String format = "";
        Boolean rep = sejourEnCours(idSejour);
        System.out.println("rep=" + rep);
        System.out.println("TOSTRING");
        String doc = "";
        try {
            String query = "select * from ph_referent join ph using(ID_SEJOUR) where DATE_SAISIE='" + dateSaisie + "'"; // la query à entrer pour accéder aux données de nos tables 

            if (rep == false) {

                rs = st.executeQuery(query);
                while (rs.next()) {
                    System.out.println("query 1 test");
                    String date = rs.getString("DATE_SAISIE");
                    String idPH = rs.getString("ID_PH");
                    String sql = "select * from personnel_medical where ID_P='" + idPH + "'"; // la query à entrer pour accéder aux données de nos tables 
                    System.out.println(sql);
                    rs2 = st2.executeQuery(sql);
                    while (rs2.next()) {
                        System.out.println("boucle while");

                        doc = "Dr." + rs2.getString("NOM") + " " + rs2.getString("PRENOM");
                        System.out.println("TEST" + doc);
                    }

                    format = "Saisie le " + date + " par le " + doc;
                    System.out.println("formatsaisie " + format);

                }
            }
            if (rep == true) {
                rs = st.executeQuery(query);
                System.out.println("okkkkkk");
                while (rs.next()) {
                    String sql2 = "select * from ph_referent join personnel_medical on ID_PHR =ID_P where ID_SEJOUR='" + idSejour + "'"; // la query à entrer pour accéder aux données de nos tables 
//          System.out.println(sql2);     
//           rs3 = st3.executeQuery(sql2);
//               while(rs3.next()){
//                   doc   = "Dr. REFERENT : " + rs3.getString("NOM")+ "\n "+rs3.getString("PRENOM");
//                   format = doc +"\n";
//               }
                    String date = rs.getString("DATE_SAISIE");
                    String idPH = rs.getString("ID_PH");
                    String sql = "select * from personnel_medical where ID_P='" + idPH + "'"; // la query à entrer pour accéder aux données de nos tables 
                    System.out.println(sql);
                    rs2 = st2.executeQuery(sql);
                    while (rs2.next()) {
                        System.out.println("boucle while");

                        doc = "Dr." + rs2.getString("NOM") + " " + rs2.getString("PRENOM");
                        System.out.println("TEST" + doc);
                    }

                    format = format + "\n" + "Saisie le " + date + " par le " + doc;
                    System.out.println("formatsaisie " + format);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);

        }
        return format;

    }
    
    //Méthode qui formate l'affichage des saisies dans le séjour dans le jtree
    public String listeLoctoString(String dateSaisie, String idSejour) {
        String format = "";
        Boolean rep =false;
        
        System.out.println("rep=" + rep);
        System.out.println("TOSTRING");
        String doc = "";
        try {
            String query = "select * from historique_localisation where DATE='" + dateSaisie + "' and ID_SEJOUR='"+idSejour+"'"; // la query à entrer pour accéder aux données de nos tables 
            String query2 = "select ID_SEJOUR,SERVICE,ORIENTATION,CHAMBRE,ETAGE,LIT from localisation where ID_SEJOUR='" + idSejour + "';";
            System.out.println("LA         +++++++++++++++++++++++++++"+query);
            if(dateSaisie == ""){
                rs2 = st2.executeQuery(query2); 
                rep=true;
            }
            else{
                 rs = st.executeQuery(query);
            }
          
            if (rep == false) {
               
               
                while (rs.next()) {
                    System.out.println("query 1 test");
                    String date = rs.getString("DATE");
                    String service1 = rs.getString("SERVICE");
                    String codeL = rs.getString("CODE_LOCALISATION");

                    format = "Nouvelle localisation le: " + date + " dans le service de: " + service1+". ";
                    
                    if(!codeL.equals(" N-0-0-N")){
                        format = format + "Le patient a été hospitalisé en: "+codeL+".";
                    }

                }
            }
          else if (rep == true) {
               
                System.out.println("okkkkkk");
                
            while (rs2.next()) {
               
                String service = rs2.getString("SERVICE");// pour avoir accès a la colonne de ma table 
                String orientation = rs2.getString("ORIENTATION");
                int chambre = rs2.getInt("CHAMBRE");
                int etage = rs2.getInt("ETAGE");
                String lit = rs2.getString("LIT");
                
                System.out.println("PETIT TEST LA          ");
                System.out.println(service);
                System.out.println(etage);
                if(etage==0 && chambre==0){
                    format = "Le patient se trouve dans le service de: " + service+". ";
                }
                
                else{
                    format = "Le patient est hospitalité dans le service de: " + service+", "+orientation+", Chambre: "+chambre+", Lit: "+lit+", étage: "+etage;
                }
//                 
                   

                }}
            

        } catch (Exception ex) {
            System.out.println(ex);

        }
        return format;

    }
      

    // Ici on implémente la fonction qui retourne la liste des informations par saisie
    public TreeMap<String, String> listeInfos(String dateSaisie, String idSejour) {

        TreeMap<String, String> liste = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String s1, String s2) {

                return s1.compareTo(s2);
            }
        });

        try {
            String query = "select * from ph where DATE_SAISIE='" + dateSaisie + "'"; // la query à entrer pour accéder aux données de nos tables 

            rs = st.executeQuery(query);
            while (rs.next()) {
                String observation = rs.getString("OBSERVATION");
                if (!observation.equals("")) {
                    liste.put("A" + "X" + "Observation", observation);
                }

                String titreOperation = rs.getString("TITRE_OPERATION");
                if (!titreOperation.equals("")) {
                    liste.put("B" + "X" + "Operation", titreOperation);
                }
                String operation = rs.getString("OPERATION");
                if (!operation.equals("")) {
                    liste.put("C" + "X" + "Détails", operation);
                }
                String resultat = rs.getString("RESULTAT");
                if (!resultat.equals("")) {
                    liste.put("D" + "X" + "Resultat", resultat);
                }
                String prescription = rs.getString("PRESCRIPTION");
                if (!prescription.equals("")) {
                    liste.put("E" + "X" + "Prescription", prescription);
                }
                String CR = rs.getString("COMPTE_RENDU");
                if (!CR.equals("")) {
                    liste.put("F" + "X" + "Compte-rendu radiologique", CR);
                }
                String lettre = rs.getString("LETTRE_SORTIE");

                if (!lettre.equals("")) {
                    liste.put("G" + "X" + "Lettre de sortie", lettre);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);

        }

        return liste;
    }

    // Ici on implémente la fonction qui retourne la liste des informations par saisie pour un Médico-technique
    public TreeMap<String, String> listeInfosPHMT(String dateSaisie, String idSejour) {

        TreeMap<String, String> liste = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String s1, String s2) {

                return s1.compareTo(s2);
            }
        });

        try {
            String query = "select * from ph where DATE_SAISIE='" + dateSaisie + "'"; // la query à entrer pour accéder aux données de nos tables 

            rs = st.executeQuery(query);
            while (rs.next()) {

                String observation = rs.getString("OBSERVATION");
                if (!observation.equals("")) {
                    liste.put("A" + "X" + "Observation", observation);
                }

                String resultat = rs.getString("RESULTAT");
                if (!resultat.equals("")) {
                    liste.put("B" + "X" + "Resultat", resultat);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);

        }

        return liste;
    }

    // Ici on implémente la fonction qui retourne la liste des informations par saisie pour un Médico-technique
    public TreeMap<String, String> listeInfosRadio(String dateSaisie, String idSejour) {

        TreeMap<String, String> liste = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String s1, String s2) {

                return s1.compareTo(s2);
            }
        });

        try {
            String query = "select * from ph where DATE_SAISIE='" + dateSaisie + "'"; // la query à entrer pour accéder aux données de nos tables 

            rs = st.executeQuery(query);
            while (rs.next()) {

                String observation = rs.getString("OBSERVATION");
                if (!observation.equals("")) {
                    liste.put("A" + "X" + "Observation", observation);
                }

                String resultat = rs.getString("RESULTAT");
                if (!resultat.equals("")) {
                    liste.put("B" + "X" + "Resultat", resultat);
                }
                String CR = rs.getString("COMPTE_RENDU");
                if (!CR.equals("")) {
                    liste.put("C" + "X" + "Compte-rendu radiologique", CR);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);

        }

        return liste;
    }
    // Ici on implémente la fonction qui retourne la liste des informations par saisie pour un Médico-technique

    public TreeMap<String, String> listeInfosAnesth(String dateSaisie, String idSejour) {

        TreeMap<String, String> liste = new TreeMap<String, String>(new Comparator<String>() {
            public int compare(String s1, String s2) {

                return s1.compareTo(s2);
            }
        });

        try {
            String query = "select * from ph where DATE_SAISIE='" + dateSaisie + "'"; // la query à entrer pour accéder aux données de nos tables 

            rs = st.executeQuery(query);
            while (rs.next()) {

                String observation = rs.getString("OBSERVATION");
                if (!observation.equals("")) {
                    liste.put("A" + "X" + "Observation", observation);
                }

                String resultat = rs.getString("RESULTAT");
                if (!resultat.equals("")) {
                    liste.put("B" + "X" + "Resultat", resultat);
                }
                String CR = rs.getString("PRESCRIPTION");
                if (!CR.equals("")) {
                    liste.put("C" + "X" + "Prescription", CR);
                }

            }

        } catch (Exception ex) {
            System.out.println(ex);

        }

        return liste;
    }

    //Completer la base de données avec les informations, cette fonction est pour un PH clinique
    public String completerSejour(String idSejour, String idMed, String Ipp, String observations, String titreOperations, String detailsOperations, String resultats, String prescriptions) {
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
                return "OK";

            } catch (Exception ex) {
                System.out.println(ex);
                return "ERREUR";
            }
        } else {
            System.out.println("Le séjour est clos et non modifiable");
        }
        return "";
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

    public String ajouterPrescription(String idSejour, String idMed, String Ipp, String prescription) {
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
                    return "OK";
                } catch (Exception ex) {
                    System.out.println(ex);
                    return "ERREUR";

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
        return "";
    }

    public String ajouterObservation(String idSejour, String idMed, String Ipp, String observation) {
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
                    return "OK";

                } catch (Exception ex) {
                    System.out.println(ex);
                    return "ERREUR";

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }

        }
        return "";
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

                    //on insere les donnees dans la classe ph ce qui correspond a la requete 1
                    PreparedStatement pstm = con.prepareStatement(sql);

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
                    PreparedStatement pstm2 = con.prepareStatement(sql2);

                    pstm2.executeUpdate();
                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public String ajouterResultat(String idSejour, String idMed, String Ipp, String resultat) {
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

                    return "OK";
                } catch (Exception ex) {
                    System.out.println(ex);
                    return "ERREUR";

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
        return "";
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

                    PreparedStatement pstm2 = con.prepareStatement(sql2);
                    pstm2.executeUpdate();

                } catch (Exception ex) {
                    System.out.println(ex);

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
    }

    public String editerLettreDeSortie(String idSejour, String idMed, String Ipp, String lettre) {
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
                    return "OK";
                } catch (Exception ex) {
                    System.out.println(ex);
                    return "ERREUR";
                }
            } else {
                System.out.println("Le séjour est déja clos");
            }
        }
        return "";
    }

    public String ajouterOperations(String idSejour, String idMed, String Ipp, String titreOperation, String detailOperation) {
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
                    return "OK";

                } catch (Exception ex) {
                    System.out.println(ex);
                    return "ERREUR";

                }
            } else {
                System.out.println("Le séjour est clos et non modifiable");
            }
        }
        return "";
    }

    // Retourne true si le sejour correspondant à l'id rentré est en cours = si il n'y a pas de lettre de sorties
    public boolean sejourEnCours(String idSejour) {
        Boolean rep = true;
        try {

            String query = "select LETTRE_SORTIE from ph where ID_SEJOUR='" + idSejour + "'"; // la query à entrer pour accéder aux données de nos tables 
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
            if (idSejour.equals("0000")) {
                rep = false;
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return rep;
    }

    public boolean prestationRealisee(String dateSaisie,String idSejour) {
        boolean rep = false;

        try {

            String query = "select * from prestations where DATE_SAISIE='"+dateSaisie+"' and ID_SEJOUR='"+idSejour+"'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String date = rs.getString("DATE_PRESTATION");
                if (date.equals("")) {

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

    public ArrayList<String> afficherLettreSortie(String IPP) {
        ArrayList<String> lettreSortie = new ArrayList<String>();
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
    }

    public ArrayList afficherTousLoc(String idsej) {
        ArrayList<String> tousLoc = new ArrayList<String>();
        try {
            String query = " select * from historique_localisation where ID_SEJOUR='" + idsej + "'";
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String idsejour = rs.getString("ID_SEJOUR");
                String date = rs.getString("DATE");
                String service = rs.getString("SERVICE");
                String codeLoc = rs.getString("CODE_LOCALISATION");
                String loc= ""+idsejour+" "+date+" "+service+" "+codeLoc;
                tousLoc.add(loc);
            }

        } catch (Exception ex) {
            System.out.println(ex);

        }
        System.out.println(tousLoc);
        return tousLoc;
    }

}


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

