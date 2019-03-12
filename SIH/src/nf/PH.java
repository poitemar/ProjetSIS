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

    public PH(String idMed, String nom, String prenom, String login, String password,Specialite spe,Service service) {
        super(idMed, nom, prenom, login,password,spe,service);
        this.specialite = spe;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://mysql-dossmed.alwaysdata.net:3306/dossmed_bd", "dossmed", "projetsis"); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    //Retroune l'id du dernier sejour crée pour le patient
    public String idSejourPatientSelection(String iPP){
        
        String idSejour ="";
        String dateLaPlusRecente="01/01/0001 00:00";
        String date="";
        java.util.Date date1;
        java.util.Date date2;
         SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //On recherche le sejour le plus recent
        try {
            String query = "select DATE_CREATION_SEJOUR from ph_referent where IPP='"+iPP+"'";
            System.out.println(query);
            rs = st.executeQuery(query);
            
            while (rs.next()) {
               date = rs.getString("DATE_CREATION_SEJOUR");
                date1 = format.parse(date);
                date2 = format.parse(dateLaPlusRecente);
               if(date1.compareTo(date2)>0){
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
        int i=0;
        String[] listePH = new String[compteur];
     
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
        int compteur = nombrePHRadio();
       
        int i=0;
        String[] listePH = new String[compteur];
        
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
                System.out.println(i);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println();
        return listePH;
    }
      public int nombrePHLabo() {
        int compteur = 0;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P ='DOCTEUR' and SPE ='"+ Specialite.LABORATOIRE_ANALYSE+"'"; // la query à entrer pour accéder aux données de nos tables 
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
    public String[] afficherListePHLabo() {
        int compteur = nombrePHRadio();
       
        int i=0;
        String[] listePH = new String[compteur];
        
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P = 'DOCTEUR' and SPE ='"+ Specialite.LABORATOIRE_ANALYSE +"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i]=nomEntier;
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

 //compter le nombre de HEMATOLOGUE
     public int nombrePHHemato() {
        int compteur = 0;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P ='DOCTEUR' and SPE ='"+ Specialite.HEMATOLOGIE+"'"; // la query à entrer pour accéder aux données de nos tables 
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
    public String[] afficherListePHHemato() {
        int compteur = nombrePHRadio();
       
        int i=0;
        String[] listePH = new String[compteur];
        
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P = 'DOCTEUR' and SPE ='"+ Specialite.HEMATOLOGIE +"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i]=nomEntier;
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
     public int nombrePHAnapatho() {
        int compteur = 0;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P ='DOCTEUR' and SPE ='"+ Specialite.ANAPATHOLOGIE+"'"; // la query à entrer pour accéder aux données de nos tables 
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
    public String[] afficherListePHAnapatho() {
        int compteur = nombrePHRadio();
       
        int i=0;
        String[] listePH = new String[compteur];
        
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P = 'DOCTEUR' and SPE ='"+ Specialite.ANAPATHOLOGIE +"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i]=nomEntier;
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
}
    
