/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import nf.Sexe;

/**
 *
 * @author PC
 */
public class RechercherInfo {

    private Connection con;
    private Statement st;
    private String nom;
    private String prenom;
    private Calendar DateNaissance;
    private ResultSet rs;

    /**
     *
     */
    public ArrayList<Patient> Lp;

    Patient p = null;

    /**
     *
     */
    public RechercherInfo() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
             st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }
        }
    }
 
    /**
     *
     * @param nom
     * @param prenom
     * @return
     */
    public ArrayList<Patient> rechercheListPatientNomPrenom(String nom, String prenom) {
        /* On recherche un patient avec son nom et son prénom et on renvoie une liste des patients repondant aux critères **/
        Lp = new ArrayList<Patient>();
        Patient p = null;
        try {

            String query = "select * from patients where nom='" + nom + "'and prenom='" + prenom + "'";

            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                // Patient p = new Patient(nom, prenom);
                String ipp = rs.getString("ipp");

                String x = rs.getString("nom");// pour avoir accès a la colonne de ma table 
                prenom = rs.getString("prenom");

                String datenaissance = rs.getString("datenaissance");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("telephone");
                System.out.println("ipp : " + ipp + " ;   nom :   " + x + ";   prenom :  " + prenom);
                System.out.print(Lp.size());

                p = new Patient(x, prenom, datenaissance);
                Lp.add(p);

            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println(Lp.size());
        return Lp;

    }

    /**
     *
     * @param nom
     * @param prenom
     * @param date
     * @return
     */
    public ArrayList<Patient> recherchePatientListNomPrenomDate(String nom, String prenom, String date) {
        /* On recherche un patient avec son nom, son prénom et sa date de naissance et on renvoie une liste de patients répondatn à ces critères **/
//       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            String d = format.format(date);  
        Lp = new ArrayList<Patient>();
        Patient p = null;
        try {

            String query = "select * from patients where nom='" + nom + "'and prenom='" + prenom + "'and datenaissance='" + date + "';";
            System.out.println(query);
            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                // Patient p = new Patient(nom, prenom);
                String ipp = rs.getString("ipp");

                String x = rs.getString("nom");// pour avoir accès a la colonne de ma table 
                prenom = rs.getString("prenom");
                String sexe = rs.getString("sexe");
                String datenaissance = rs.getString("datenaissance");
                String adresse = rs.getString("adresse");
                String role = rs.getString("telephone");
                System.out.println("ipp : " + ipp + " ;   nom :   " + x + ";   prenom :  " + prenom + "   dateNaissance" + date);

                p = new Patient(nom, prenom, date);
                Lp.add(p);
                System.out.println(Lp.size());
            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println(Lp);
        return Lp;
    }

    //affichage de la Liste des patients 

    /**
     *
     * @return
     */
    public ArrayList<Patient> afficherListPatient() {
        /* On affiche une ArrayList de patients en passant par la base de données **/
        ArrayList<Patient> patients = null;
        Patient p = null;
        try {

            String query = "select * from patients";

            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                // Patient p = new Patient(nom, prenom);
                String ipp = rs.getString("ipp");

                String x = rs.getString("nom");// pour avoir accès a la colonne de ma table 
                String y = rs.getString("prenom");
                String sexe = rs.getString("sexe");
                String datenaissance = rs.getString("datenaissance");
                String adresse = rs.getString("adresse");
                String role = rs.getString("telephone");

                p = new Patient(x, y, datenaissance);
                System.out.println("Patient p" + p);
                System.out.println("1");
                patients.add(1, p);

            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return patients;

    }

    /**
     *
     * @param spe
     * @param nom1
     * @param prenom1
     * @return
     */
    public ArrayList<Patient> patientServiceNomPrenom(Specialite spe,String nom1,String prenom1) {
        /* On renvoie une liste de patients étant dans une spécialité ayant un nom de famille "nom1" et un prénom "prenom1" **/
        ArrayList<Patient> listePatient = new ArrayList<Patient>();

        try {
            String query = "select * from patients join ph_referent on (patients.IPP=ph_referent.IPP) join localisation using (ID_SEJOUR) where SERVICE ='" + spe + "' and nom='" + nom1 + "'and prenom='" + prenom1 + "' order by NOM"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                System.out.println(prenom);
                String date = rs.getString("DATENAISSANCE");
         

                Patient patient = new Patient(nom, prenom,date);
                            
                
                int i =0;
                Boolean rep = false;
                 while( i<listePatient.size() && rep==false){
                     if(listePatient.get(i).getIpp().equals(patient.getIpp())){
                  rep = true;
                  i++;
                }  
                     else{ i++;}
                 }
                 if(rep==false){
                     listePatient.add(patient);
                 }
              
                
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return listePatient;

    }

    /**
     *
     * @param spe
     * @param nom1
     * @param prenom1
     * @param date1
     * @return
     */
    public ArrayList<Patient> patientServiceNomPrenomDate(Specialite spe,String nom1,String prenom1,String date1) {
        /* On renvoie une liste de patients d'une spécialité avec une date de naissance, un nom et un prénom donnés **/
        ArrayList<Patient> listePatient = new ArrayList<Patient>();

        try {
            String query = "select * from patients join ph_referent on (patients.IPP=ph_referent.IPP) join localisation using (ID_SEJOUR) where SERVICE ='" + spe + "' and NOM='" + nom1 + "'and PRENOM='" + prenom1 +"'and DATENAISSANCE='" + date1 + "' order by NOM"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                System.out.println(prenom);
                String date = rs.getString("DATENAISSANCE");
         

                Patient patient = new Patient(nom, prenom,date);
                            
                
                int i =0;
                Boolean rep = false;
                 while( i<listePatient.size() && rep==false){
                     if(listePatient.get(i).getIpp().equals(patient.getIpp())){
                  rep = true;
                  i++;
                }  
                     else{ i++;}
                 }
                 if(rep==false){
                     listePatient.add(patient);
                 }
              
                
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return listePatient;

    }

    /**
     *
     * @param spe
     * @param nom1
     * @return
     */
    public ArrayList<Patient> patientServiceNom(Specialite spe,String nom1) {
        /* On renvoie une liste de patients d'une spécialité donnée ayant un nom de famille "nom1" **/
        ArrayList<Patient> listePatient = new ArrayList<Patient>();

        try {
            String query = "select * from patients join ph_referent on (patients.IPP=ph_referent.IPP) join localisation using (ID_SEJOUR) where SERVICE ='" + spe + "' and nom='" + nom1 + "' order by NOM"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                System.out.println(prenom);
                String date = rs.getString("DATENAISSANCE");
         

                Patient patient = new Patient(nom, prenom,date);
                            
                
                int i =0;
                Boolean rep = false;
                 while( i<listePatient.size() && rep==false){
                     if(listePatient.get(i).getIpp().equals(patient.getIpp())){
                  rep = true;
                  i++;
                }  
                     else{ i++;}
                 }
                 if(rep==false){
                     listePatient.add(patient);
                 }
              
                
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return listePatient;

    }

    /**
     *
     * @param nom
     * @param prenom
     * @return
     */
    public Patient recherchePatientNomPrenom(String nom, String prenom) {
        /* On renvoie un patient de la base de données qui a un nom "nom" et un prénom "prenom" **/
        p = new Patient(null, null);

        try {

            String query = "select * from patients where nom='" + nom + "'and prenom='" + prenom + "'";

            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                // Patient p = new Patient(nom, prenom);
                String ipp = rs.getString("ipp");

                String x = rs.getString("nom");// pour avoir accès a la colonne de ma table 
                prenom = rs.getString("prenom");

                String datenaissance = rs.getString("datenaissance");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("telephone");
                System.out.println("ipp : " + ipp + " ;   nom :   " + x + ";   prenom :  " + prenom);
                // System.out.print(Lp.size());

                p = new Patient(x, prenom, datenaissance);
                //  Lp.add(p);

            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println(p);
        return p;

    }

    /**
     *
     * @param nom
     * @param prenom
     * @param date
     * @return
     */
    public ArrayList<Patient> recherchePatientNomPrenomDate(String nom, String prenom, String date) {
        /* On renvoie une liste de patients ayant un nom "nom", un prénom "prenom" et une date de naissance "date" **/
//       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            String d = format.format(date);  
        Lp = new ArrayList<Patient>();
        Patient p = null;
        try {

            String query = "select * from patients where nom='" + nom + "'and prenom='" + prenom + "'and datenaissance='" + date + "';";
            System.out.println(query);
            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                // Patient p = new Patient(nom, prenom);
                String ipp = rs.getString("ipp");

                String x = rs.getString("nom");// pour avoir accès a la colonne de ma table 
                prenom = rs.getString("prenom");
                String sexe = rs.getString("sexe");
                String datenaissance = rs.getString("datenaissance");
                String adresse = rs.getString("adresse");
                String role = rs.getString("telephone");
                System.out.println("ipp : " + ipp + " ;   nom :   " + x + ";   prenom :  " + prenom + "   dateNaissance" + date);

                p = new Patient(nom, prenom, date);
                Lp.add(p);

            }
            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println(Lp.size());
        return Lp;

    }

    /**
     *
     * @param nom
     * @return
     */
    public ArrayList<Patient> rechercheListPatientNom(String nom) {
        /* On renvoie une liste de patients de la base de données ayant comme nom de famille "nom" **/
        Lp = new ArrayList<Patient>();
        Patient p = null;
        try {

            String query = "select * from patients where nom='" + nom + "'";

            st = con.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                // Patient p = new Patient(nom, prenom);
                String ipp = rs.getString("ipp");

                String x = rs.getString("nom");// pour avoir accès a la colonne de ma table 
                prenom = rs.getString("prenom");

                String datenaissance = rs.getString("datenaissance");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("telephone");
                System.out.println("ipp : " + ipp + " ;   nom :   " + x + ";   prenom :  " + prenom);
                System.out.print(Lp.size());

                p = new Patient(x, prenom, datenaissance);
                Lp.add(p);

            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println(Lp.size());
        return Lp;

 }   }

