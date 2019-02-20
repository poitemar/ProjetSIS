/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.Date;
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
    

    /**
     * @param specialite the specialite to set
     */
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Specialite specialite;

    public PH(String idMed, String nom, String prenom, String login, String password,Specialite spe,Service service) {
        super(idMed, nom, prenom, login,password,spe,service);
        this.specialite = spe;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    //Retroune l'id du dernier sejour crée pour le patient
    public String idSejourPatientSelection(String iPP){
        
        String idSejour ="";
        String dateLaPlusRecente="01/01/0001";
        String date="";
        //On recherche le sejour le plus recent
        try {
            String query = "select DATE_CREATION_SEJOUR from ph_referent where IPP='"+iPP+"'";
            System.out.println(query);
            rs = st.executeQuery(query);
            
            while (rs.next()) {
               date = rs.getString("DATE_CREATION_SEJOUR");
               if(date.compareTo(dateLaPlusRecente)>0){
                   dateLaPlusRecente = date;
             
               }
                System.out.println(date);
                   System.out.println(dateLaPlusRecente);   
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        //On récupère l'id associé au sejour le plus ancien
        try {
            String query = "select ID_SEJOUR from ph_referent where IPP='"+iPP+"' and DATE_CREATION_SEJOUR='"+dateLaPlusRecente+"'";
            System.out.println(query);
            rs = st.executeQuery(query);
            
            while (rs.next()) {
               idSejour = rs.getString("ID_SEJOUR");
              
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        
        return idSejour;
    }
    
    
    
    
    public Specialite getSpecialite() {
        return specialite;
    }
//    public void ajouterSejour(String idSejour, Patient patient, PH phReferant, Localisation localisation, String prescription, String observation, String compteRendu, String resultat, String titreOperation, String detailsOperation, String lettreDeSortie) {
//        Sejour s = new Sejour(idSejour, patient, phReferant, localisation, prescription, observation, compteRendu, resultat, titreOperation, detailsOperation, lettreDeSortie);
//        s.setPhReferant(this);
//        s.getListePrescriptions().add(prescription);
//        s.getListeObservations().add(observation);
//        s.getListeTitreOperations().add(titreOperation);
//        s.getListeDetailsOperations().add(detailsOperation);
//        s.getListeDeCompteRenduRadio().add(compteRendu);
//        s.getListeDeResultats().add(resultat);
//        s.setLettreDeSortie(lettreDeSortie);
//
//        String sql = "insert into ph(ID_PH,IPP_PATIENT,ID_SEJOUR,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,OPERATION,TITRE_OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?)";
//
//        try {
//            PreparedStatement pstm = con.prepareStatement(sql);
//            pstm.setString(1, phReferant.getIdMed());
//            pstm.setString(2, patient.getIpp());
//            pstm.setString(3, idSejour);
//            pstm.setString(4, observation);
//            pstm.setString(5, resultat);
//            pstm.setString(6, lettreDeSortie);
//            pstm.setString(7, prescription);
//            pstm.setString(8, detailsOperation);
//            pstm.setString(9, titreOperation);
//            pstm.setString(10, compteRendu);
//            pstm.executeUpdate();
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//    }
    
    public int nombrePatients() {
        int compteur = 0;
        System.out.println(getSpecialite());
        try {
            String query = "select * from PATIENTS join PH on (IPP=IPP_PATIENT) join PERSONNEL_MEDICAL on (ID_PH = ID_P) join LOCALISATION using (ID_SEJOUR) where UPPER(LOCALISATION.service) = UPPER('"+getSpecialite()+"')";
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
    
    public String[] afficherListePatients(){
        int compteur = nombrePatients();
        int i=0;
        String[] listePatients = new String[compteur];
        try {
            String query = "select * from PATIENTS join PH on (IPP=IPP_PATIENT) join PERSONNEL_MEDICAL on (ID_PH = ID_P) join LOCALISATION using (ID_SEJOUR) where UPPER(LOCALISATION.service)= UPPER('"+getSpecialite()+"')";// la query à entrer pour accéder aux données de nos tables 
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
