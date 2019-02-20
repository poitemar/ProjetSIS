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
import nf.Prestation;
/**
 *
 * @author poite
 */
public class PH extends PersonnelMedical {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Specialite specialite;
    private Prestation prestation; 
    Sejour sej; 
    
    public PH(String nom, String prenom, String idMed, String login, String password,Specialite spe,Service service) {
        super(nom, prenom, idMed, password,login,spe,service);
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

   
    
  
    
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
    public Specialite getSpecialite() {
        return specialite;
    }
    public void ajouterSejour(String idSejour, Patient patient, PH phReferant, Localisation localisation, String prescription, String observation, String compteRendu, String resultat, String titreOperation, String detailsOperation, String lettreDeSortie,String prestation) {
        // Sejour s = new Sejour(idSejour, patient, phReferant, localisation, prescription, observation, compteRendu, resultat, titreOperation, detailsOperation, lettreDeSortie);
//        s.setPhReferant(this);
//        s.getListePrescriptions().add(prescription);
//        s.getListeObservations().add(observation);
//        s.getListeTitreOperations().add(titreOperation);
//        s.getListeDetailsOperations().add(detailsOperation);
//        s.getListeDeCompteRenduRadio().add(compteRendu);
//        s.getListeDeResultats().add(resultat);
//        s.setLettreDeSortie(lettreDeSortie);

        String sql = "insert into ph(ID_PH,IPP_PATIENT,ID_SEJOUR,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,OPERATION,TITRE_OPERATION,COMPTE_RENDU,PRESTATION,) values (?,?,?,?,?,?,?,?,?,?,?)";

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
            pstm.setString(11, prestation);
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
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
    
    //compter le nombre des Anesthesistes
     public int nombrePHAnes() {
        int compteur = 0;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P ='DOCTEUR' and SPE ='"+ Specialite.ANESTHESIE+"'"; // la query à entrer pour accéder aux données de nos tables 
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
    
     //affichage de la liste des Anesthesistes
    public String[] afficherListePHAnes() {
        int compteur = nombrePHAnes()+1;
        int i=1;
        String[] listePH = new String[compteur];
        
        listePH[0]=null;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P = 'DOCTEUR' and SPE ='"+ Specialite.ANESTHESIE +"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i]=nomEntier;
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
     public int nombrePHRadio() {
        int compteur = 0;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P ='DOCTEUR' and SPE ='"+ Specialite.RADIOLOGIE+"'"; // la query à entrer pour accéder aux données de nos tables 
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
    public String[] afficherListePHRadio() {
        int compteur = nombrePHAnes()+1;
        int i=1;
        String[] listePH = new String[compteur];
        
        listePH[0]=null;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P = 'DOCTEUR' and SPE ='"+ Specialite.RADIOLOGIE +"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i]=nomEntier;
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
    
   //Fonction qui retourne l'idMed du medecin lu dans la liste
    public String iPPMedecinListe(String lecture){
        String ipp="";
         try {
             String nomLu ="";
             String prenomLu="";
             String[] result = lecture.split(" ");
             
        for (int x=0; x<result.length; x++){
         nomLu =result[0];
         prenomLu = result[1];
         
        }
            String query = "select * from PERSONNEL_MEDICAL where NOM='"+nomLu+"' and PRENOM='"+prenomLu+"'"; // la query à entrer pour accéder aux données de nos tables 
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
    public String iDPH(String idmed){
        String idph="";
       try{
       String query = "select * from PH where ID_PH='"+idmed+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                idph = rs.getString("ID_PH");
                System.out.println(idph);
             
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        return idph;
    } 
   public void ajouterPrestations(String idSejour, String idMedDemandeur,String idMedReceveur,String Prestations){
       if(sej.sejourEnCours(idSejour)) {
           String sql ="insert into prestation (ID_SEJOUR,ID_MED_DEMANDEUR,ID_MED_RECEVEUR,PRESTATION) values (?,?,?,?)";
       
        System.out.println(sql);
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
//           Date maDate;
//            SimpleDateFormat maDateLongue;
//            maDate= new Date();
//             maDateLongue= new SimpleDateFormat("dd/MM/yyyy HH:mm");
            //on insere les donnees dans la classe ph_referent ce qui correspond a la requete 1
            pstm.setString(1, idSejour);
            pstm.setString(2, idMedDemandeur);
             pstm.setString(3, idMedReceveur);
              pstm.setString(4,Prestations);
           
 
           pstm.executeUpdate();
            
            
        } catch (Exception ex) {
            System.out.println(ex);
           
        }}
       else {System.out.println("Le séjour est clos et non modifiable");}
    }
    
    }
