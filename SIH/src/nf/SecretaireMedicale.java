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

/**
 *
 * @author PC
 */
public class SecretaireMedicale extends PersonnelMedical {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public SecretaireMedicale(String nom, String prenom, String idMed, String password) {
        super(nom, prenom, idMed, password);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    public void ajouterSejour(String idSejour, Patient patient, PH phReferant, Localisation localisation, String prescription, String observation, String compteRendu, String resultat, String titreOperation, String detailsOperation, String lettreDeSortie) {
        Sejour s = new Sejour(idSejour, patient, phReferant, localisation, prescription, observation, compteRendu, resultat, titreOperation, detailsOperation, lettreDeSortie);
//        s.setIdSejour(idSejour);
//        s.setPatient(patient);
//        s.setPhReferant(phReferant);
//        s.setLocalisation(localisation);

        String sql = "insert into ph(ID_PH,IPP_PATIENT,ID_SEJOUR,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,OPERATION,TITRE_OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, phReferant.idMed);
            pstm.setString(2, patient.getIpp());
            pstm.setString(3, idSejour);
            pstm.setString(4, observation);
            pstm.setString(5, resultat);
            pstm.setString(6, lettreDeSortie);
            pstm.setString(7, prescription);
            pstm.setString(8, detailsOperation);
            pstm.setString(9, titreOperation);
            pstm.setString(10, compteRendu);
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    public int nombrePatients() {
        int compteur = 0;
        try {
            String query = "select * from PATIENTS";
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

    public String[] afficherListePatients() {
        int compteur = nombrePatients();
        int i=0;
        String[] listePatients = new String[compteur];
        try {
            String query = "select * from PATIENTS "; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePatients[i]=nomEntier;
                System.out.println(listePatients[i]);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return listePatients;
    }
}
