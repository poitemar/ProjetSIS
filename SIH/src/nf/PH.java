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
import java.util.ArrayList;
import nf.Sejour;

/**
 *
 * @author poite
 */
public class PH extends PersonnelMedical {

    /**
     * @return the specialite
     */
    public Specialite getSpecialite() {
        return specialite;
    }

    /**
     * @param specialite the specialite to set
     */
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    private Connection con;
    private Statement st;
    private Specialite specialite;

    public PH(String nom, String prenom, String idMed, Specialite specialite, String login, String password) {
        super(nom, prenom, idMed, password,login);
        this.specialite = specialite;
    }

    public void ajouterSejour(String idSejour, Patient patient, PH phReferant, Localisation localisation, String prescription, String observation, String compteRendu, String resultat, String titreOperation, String detailsOperation, String lettreDeSortie) {
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
            pstm.setString(1, phReferant.getIdMed());
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

//    public ArrayList<Sejour> afficherSejour() {
//
//        sej = new ArrayList<Sejour>();
//
//        try {
//
//            String query = "select * from ph";
//            System.out.println(query);
//            st = con.createStatement();
//            rs = st.executeQuery(query);
//
//            while (rs.next()) {
//
//                String idSej = rs.getString("ID_SEJOUR");
//                String patient = rs.getString("ipp");// pour avoir accès a la colonne de ma table 
//                String medecin = rs.getString("ID_PH");
//                String obs = rs.getString("OBSERVATION");
//                String resultat = rs.getString("RESULTAT");
//                String lettre = rs.getString("LETTRE_SORTIE");
//                String presc = rs.getString("PRESCRIPTION");
//                String op = rs.getString("OPERATION");
//                String titreOp = rs.getString("TITRE_OPERATION");
//                String comptreRendu = rs.getString("COMPTE_RENDU");
//                System.out.println("ipp : " + patient + " obs   :  " + obs + "resultat   :  " + resultat);
//
//                sejour = new Sejour(obs);
//                sej.add(sejour);
//
//            }
//            rs.close();
//            st.close();
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//        System.out.println(sej.size());
//        return sej;
//    }

}
