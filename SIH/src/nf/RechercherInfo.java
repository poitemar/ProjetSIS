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
    public ArrayList<Patient> Lp;

    Patient p = null;

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
 
    public ArrayList<Patient> rechercheListPatientNomPrenom(String nom, String prenom) {
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

    public ArrayList<Patient> recherchePatientListNomPrenomDate(String nom, String prenom, String date) {
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
    public ArrayList<Patient> afficherListPatient() {
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

    public Patient recherchePatientNomPrenom(String nom, String prenom) {
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

    public ArrayList<Patient> recherchePatientNomPrenomDate(String nom, String prenom, String date) {
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

 public ArrayList<Patient> rechercheListPatientNom(String nom) {
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

//    public ResultSet recherchePatientNomPrenomDate(String nom, String prenom, Calendar date) throws SQLException {
//        String datedenaiss = date.get(date.YEAR) + "-" + (date.get(date.MONTH) + 1) + "-" + date.get(date.DAY_OF_MONTH);
//        Connection c = new Connection();
//        c.connecter();
//        Statement previous = c.c.createStatement();
//        ResultSet r = previous.executeQuery("select * from patients"
//                + " where nom='" + nom + "' and date_naissance ='" + datedenaiss + "' and prenom='" + prenom + "'");
//        //r.first();
//        return r;
//    }
//    //affichage de La Liste des Patients par nom et prenom 
//    public ArrayList <Patient> afficherListPatient() {
//     DefaultTableModel DTM = new DefaultTableModel();
//     DTM = (DefaultTableModel) rechercheTable.getModel();
//        DTM.setRowCount(0);
//      if (!jTextField1.getText().isEmpty() && !jTextField2.getText().isEmpty()){
//                    
//            ArrayList<Patient> lp;
//            lp= inf.recherchePatientsNomPrenom(jTextField1.getText(), jTextField2.getText());
//            for(int i=0 ; i<Lp.size(); i++){  
//            Lp.get(i);
//            DTM.addRow(new Object[]{Lp.get(i).getNom(), Lp.get(i).getPrenom(),});
//        }
//                jList1.setModel(DTM);
//    } 
//    }

