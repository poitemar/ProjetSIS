/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

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

    public RechercherInfo() {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // Se connecter au LocalHost de la BD  
            st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }
        }
    }

    public Patient recherchePatientNomPrenom (String nom, String prenom) {
         Patient p=null; 
        try {

            String query = "select * from patients where nom='" + nom + "'and prenom='" + prenom + "'";

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
                System.out.println("ipp : " + ipp + " ;   nom :   " + x + ";   prenom :  " + prenom);
                //rs.updateRow();
                //Lp.add(p);
                //System.out.println(Lp.get(0).getNom());
                p=new Patient(nom,prenom);
            }
            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return p;

    }

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
}
