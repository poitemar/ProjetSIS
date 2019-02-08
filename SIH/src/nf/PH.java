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
 * @author poite
 */
public class PH extends PersonnelMedical {

    private Connection con;
    private Statement st;

    public PH(String nom, String prenom, String idMed, String specialite, String login, String password) {
        super(nom, prenom, idMed, password);
    }

    public void ajouterSejour(String idSejour, Patient patient, PH phReferant, Localisation localisation, String prescription, String observation, String compteRendu, String resultat, String titreOperation,String detailsOperation, String lettreDeSortie){
       // Sejour s = new Sejour(idSejour, patient, phReferant, localisation, prescription, observation, compteRendu, resultat, titreOperation, detailsOperation, lettreDeSortie);
//        s.setPhReferant(this);
//        s.getListePrescriptions().add(prescription);
//        s.getListeObservations().add(observation);
//        s.getListeTitreOperations().add(titreOperation);
//        s.getListeDetailsOperations().add(detailsOperation);
//        s.getListeDeCompteRenduRadio().add(compteRendu);
//        s.getListeDeResultats().add(resultat);
//        s.setLettreDeSortie(lettreDeSortie);

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
        }
    }

}
