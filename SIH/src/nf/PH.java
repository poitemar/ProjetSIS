/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import static java.awt.image.ImageObserver.ABORT;
import static java.awt.image.ImageObserver.ERROR;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import nf.Sejour;

/**
 *
 * @author poite
 */
public class PH extends PersonnelMedical {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Specialite specialite;

    /**
     *
     * @param idMed
     * @param nom
     * @param prenom
     * @param login
     * @param password
     * @param spe
     * @param service
     */
    public PH(String idMed, String nom, String prenom, String login, String password, Specialite spe, Service service) {
        super(idMed, nom, prenom, login, password, spe, service);
        this.specialite = spe;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    //Retroune l'id du dernier sejour crée pour le patient
    /**
     *
     * @param iPP
     * @return
     */
    public String idSejourPatientSelection(String iPP) {
        /**
         * on prend en entrée l'ipp d'un patient et on renvoie l'id séjour le
         * plus récent d'un patient
         */

        String idSejour = "";
        String dateLaPlusRecente = "01/01/0001 00:00";
        String date = "";
        java.util.Date date1;
        Boolean rep = false;
        java.util.Date date2;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //On recherche le sejour le plus recent
        try {
            String query = "select DATE_CREATION_SEJOUR from ph_referent where IPP='" + iPP + "'";
            System.out.println(query);
            rs = st.executeQuery(query);

            //  trouver comment detecter une personne qui n'a jamais eu de sejour               
            while (rs.next()) {
                rep = true;
                date = rs.getString("DATE_CREATION_SEJOUR");
                date1 = format.parse(date);
                date2 = format.parse(dateLaPlusRecente);
                if (date1.compareTo(date2) > 0) { //positif si la date 1 est la plus récente
                    dateLaPlusRecente = date;
                }
           }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        //On récupère l'id associé au sejour le plus récent
        try {
            String query2 = "select ID_SEJOUR from ph_referent where IPP='" + iPP + "' and DATE_CREATION_SEJOUR='" + dateLaPlusRecente + "'";
            rs = st.executeQuery(query2);

            while (rs.next()) {
                idSejour = rs.getString("ID_SEJOUR");

            }
            if (rep == false) {
                idSejour = "0000";
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return idSejour;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    /**
     *
     * @return
     */
    public int nombrePatients() {
        /**
         * cette fonction renvoie le nombre de patient de la spécialité du PH
         * associée
         */
        int compteur = 0;
        System.out.println(getSpecialite());
        try {
            String query = "select * from patients join ph on (IPP=IPP_PATIENT) join personnel_medical on (ID_PH = ID_P) join localisation using (ID_SEJOUR) where UPPER(localisation.service) = UPPER('" + getSpecialite() + "')";
            rs = st.executeQuery(query);
            while (rs.next()) {
                compteur++; //compteur qui incrémente chaque fois qu'il y a un patient dans la table
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println("compteur = " + compteur);
        return compteur;
    }

    /**
     *
     * @return
     */
    public String[] afficherListePatients() {
        /**
         * Cette fonction va renvoyer dans un tableau de String les différents
         * patients, sous la forme "nom prénom". Ce format de tableau est plus
         * simple à exploiter pour l'ui
         */
        int compteur = nombrePatients();
        int i = 0;
        String[] listePatients = new String[compteur]; //on fait un tableau de la taille du nombre de patients
        try {
            String query = "select * from patients join ph on (IPP=IPP_PATIENT) join personnel_medical on (ID_PH = ID_P) join localisation using (ID_SEJOUR) where UPPER(localisation.service)= UPPER('" + getSpecialite() + "')";// la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePatients[i] = nomEntier; //on ajoute dans la liste le nom et le prénom des patients présents
                System.out.println(listePatients[i]);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return listePatients;
    }

    //compter le nombre des Anesthesistes
    /**
     *
     * @return
     */
    public int nombrePHAnes() {
        /**
         * Cette fonction compte les nombre de docteurs anesthésistes
         */
        int compteur = 0;
        try {
            String query = "select * from personnel_medical where TYPE_P ='DOCTEUR' and SPE ='" + Specialite.ANESTHESIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query); //on récupère les docteurs qui ont comme spécialité "anesthésiste"
            while (rs.next()) {
                compteur++; //on incrémente à chaque fois qu'il y en a un
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println("compteur = " + compteur);
        return compteur;
    }

    //affichage de la liste des Anesthesistes
    /**
     *
     * @return
     */
    public String[] afficherListePHAnes() {
        /**
         * Cette fonction va renvoyer dans un tableau de Strings la liste des
         * docteurs anesthésistes. Ce format est plus simplé à exploiter pour
         * l'ui
         */
        int compteur = nombrePHAnes() + 1;
        int i = 0;
        String[] listePH = new String[compteur]; //on fait un tableau de taille le nombre de docteurs anesthésistes

        try {
            String query = "select * from personnel_medical where TYPE_P = 'DOCTEUR' and SPE ='" + Specialite.ANESTHESIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i] = nomEntier; // on ajoute dans la liste de ph anesthésistes chaque ph anesthésistes
                System.out.println(listePH[i]);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println();
        return listePH;
    }

    //compter le nombre des Radiologues
    /**
     *
     * @return
     */
    public int nombrePHRadio() { //même principe que nombrePhAnes
        /**
         * Cette fonction renvoie le nombre de docteurs radiologues
         */
        int compteur = 0;
        try {
            String query = "select * from personnel_medical where TYPE_P ='DOCTEUR' and SPE ='" + Specialite.RADIOLOGIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                compteur++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println("compteur = " + compteur);
        return compteur;
    }

    //affichage de la liste des Radiologues
    /**
     *
     * @return
     */
    public String[] afficherListePHRadio() { //même principe que afficherlListePhAnes
        /**
         * Cette fonction va renvoyer dans un tableau de Strings la liste des
         * docteurs radiologues. Ce format est plus simple à exploiter pour l'ui
         */
        int compteur = nombrePHRadio();

        int i = 0;
        String[] listePH = new String[compteur];

        try {
            String query = "select * from personnel_medical where TYPE_P = 'DOCTEUR' and SPE ='" + Specialite.RADIOLOGIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i] = nomEntier;
                System.out.println(listePH[i]);
                i++;
                System.out.println(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println();
        return listePH;
    }

    /**
     *
     * @return
     */
    public int nombrePHLabo() { //même principe que nombrePhAnes
        /**
         * Cette fonction va renvoyer le nombre de docteurs en laboratoire
         * d'analyse
         */
        int compteur = 0;
        try {
            String query = "select * from personnel_medical where TYPE_P ='DOCTEUR' and SPE ='" + Specialite.LABORATOIRE_ANALYSE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                compteur++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println("compteur = " + compteur);
        return compteur;
    }

    //affichage de la liste des Radiologues
    /**
     *
     * @return
     */
    public String[] afficherListePHLabo() { //même principe que afficherListePHAnes
        /**
         * Cette fonction va renvoyer dans un tableau de Strings la liste des
         * docteurs en laboratoire d'analyse. Ce format est plus simple à
         * exploiter pour l'ui
         */
        int compteur = nombrePHRadio();

        int i = 0;
        String[] listePH = new String[compteur];

        try {
            String query = "select * from personnel_medical where TYPE_P = 'DOCTEUR' and SPE ='" + Specialite.LABORATOIRE_ANALYSE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i] = nomEntier;
                System.out.println(listePH[i]);
                i++;
                System.out.println(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println();
        return listePH;
    }
    //Fonction qui retourne l'idMed du medecin lu dans la liste

    /**
     *
     * @param lecture
     * @return
     */
    public String iPPMedecinListe(String lecture) {
        /**
         * On prend en entrée le nom et le prénom concaténé dans un String,
         * séparés par un espace et on renvoie l'id du médecin associé
         */
        String ipp = "";
        try {
            String nomLu = "";
            String prenomLu = "";
            String[] result = lecture.split(" ");

            for (int x = 0; x < result.length; x++) {
                nomLu = result[0];
                prenomLu = result[1];

            }
            String query = "select * from personnel_medical where NOM='" + nomLu + "' and PRENOM='" + prenomLu + "'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getString("ID_P");
                System.out.println(ipp);

            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        return ipp;
    }
//Fonction qui retourne l'idMed du medecin lu dans la liste

    /**
     *
     * @param idmed
     * @return
     */
    public Specialite speIDPH(String idmed) {
        /**
         * On prend en entrée l'id d'un médecin et on renvoie la spécialité du
         * médecin associée
         */
        String spe = "";
        try {
            String query = "select * from personnel_medical where ID_P='" + idmed + "'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String idph = rs.getString("ID_P");
                System.out.println(idph);
                spe = rs.getString("SPE");

            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        Specialite speS = Specialite.valueOf(spe);
        return speS;
    }

    //compter le nombre de HEMATOLOGUE
    /**
     *
     * @return
     */
    public int nombrePHHemato() { //même principe que nombrePhAnes
        /**
         * Cete fonction va renvoyer le nombre de docteurs hématologues
         */
        int compteur = 0;
        try {
            String query = "select * from personnel_medical where TYPE_P ='DOCTEUR' and SPE ='" + Specialite.HEMATOLOGIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                compteur++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println("compteur = " + compteur);
        return compteur;
    }

    //affichage de la liste des Hematologues
    /**
     *
     * @return
     */
    public String[] afficherListePHHemato() { //même principe que afficherListePHAnes
        /**
         * Cette fonction va renvoyer dans un tableau de Strings le nom et le
         * prénom des docteurs hématologues sous la forme "nom prénom". Ce
         * format de tableau est plus simple à exploiter pour l'ui
         */
        int compteur = nombrePHHemato();

        int i = 0;
        String[] listePH = new String[compteur];

        try {
            String query = "select * from personnel_medical where TYPE_P = 'DOCTEUR' and SPE ='" + Specialite.HEMATOLOGIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i] = nomEntier;
                System.out.println(listePH[i]);
                i++;
                System.out.println(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println();
        return listePH;
    }

    //compter le nombre de Anapathologues
    /**
     *
     * @return
     */
    public int nombrePHAnapatho() { //même principe que nombrePhAnes
        /**
         * Cette fonction va renvoyer le nombre de docteurs anapathologues
         */
        int compteur = 0;
        try {
            String query = "select * from personnel_medical where TYPE_P ='DOCTEUR' and SPE ='" + Specialite.ANAPATHOLOGIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                compteur++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println("compteur = " + compteur);
        return compteur;
    }

    //affichage de la liste des anapathologues
    /**
     *
     * @return
     */
    public String[] afficherListePHAnapatho() { //même principe que afficherListePHAnes
        /**
         * Cette fonction va renvoyer un tableau de Strings des docteurs
         * anapathologues sous la forme "nom prénom". Ce format de tableau est
         * plus simple à exploiter pour l'ui
         */
        int compteur = nombrePHRadio();

        int i = 0;
        String[] listePH = new String[compteur];

        try {
            String query = "select * from personnel_medical where TYPE_P = 'DOCTEUR' and SPE ='" + Specialite.ANAPATHOLOGIE + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i] = nomEntier;
                System.out.println(listePH[i]);
                i++;
                System.out.println(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println();
        return listePH;
    }

    //affichage du PH referent du patient dans le DMA
    /**
     *
     * @param IPP
     * @return
     */
    public String afficherPHR(String IPP) { 
        /**
         * Cette fonction prend en entrée l'IPP d'un patient, et renvoie sous la
         * forme d'un String le nom et le prénom du ph référent, concaténés et
         * séparés par un espace
         */
        String PHref = " ";
        try {
            String query = "select * from personnel_medical join ph_referent on (personnel_medical.ID_P=ph_referent.ID_PHR) where IPP='" + IPP + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                PHref = nom + " " + prenom;

            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println(PHref);
        return PHref;
    }

}
