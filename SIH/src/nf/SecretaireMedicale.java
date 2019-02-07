/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author PC
 */
public class SecretaireMedicale extends PersonnelMedical {

    private Connection con;
    private Statement st;

    public SecretaireMedicale(String nom, String prenom, String idMed, String password) {
        super(nom, prenom, idMed, password);
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);

        }
    }

    public void ajouterSejour(String idSejour, Patient patient, PH phReferant,
            Localisation localisation) {
        Sejour s = new Sejour(idSejour, patient, phReferant, localisation);
        s.ajouterIdSejour(idSejour);
        s.ajouterPatient(patient);
        s.ajouterPhReferant(phReferant);
        s.ajouterLocalisation(localisation);

////        String sql = "insert into ph(ID_PH,IPP_PATIENT,ID_SEJOUR,OBSERVATION,RESULTAT,"
////                + "LETTRE_SORTIE,PRESCRIPTION,OPERATION,TITRE_OPERATION,COMPTE_RENDU,) "
////                + "values (?,?,?,?,?,?,?,?,?,?)";
////
////        try {
////            PreparedStatement pstm = con.prepareStatement(sql);
////
////            pstm.setString(1, phReferant.idMed);
////            pstm.setString(2, patient.getIpp());
////            pstm.setString(3, idSejour);
////            pstm.executeUpdate();
////        } catch (Exception ex) {
////            System.out.println(ex);
////        }
    }
}
