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
import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author PC
 */
public class SecretaireAdministrative extends PersonnelMedical{
     private Connection con;
      private Statement st;
      private Specialite specialite;
      private ResultSet rs;
     
    public SecretaireAdministrative(String idMed,String nom, String prenom, String login, String password, Specialite spe,Service service) {
        super(idMed,nom, prenom,login,password,spe,service);
        this.specialite = Specialite.ACCUEIL;
         try{
            Class.forName("com.mysql.jdbc.Driver");
            
            con= DriverManager.getConnection("jdbc:mysql://mysql-dossmed.alwaysdata.net:3306/dossmed_bd", "dossmed", "projetsis"); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();
            
        }catch(Exception ex) {
            System.out.println("error :" +  ex );
            
        }
    }
    

    public void ajouterNouveauPatient(String ipp, String nom, String prenom, Sexe sexe, String dateDeNaissance, String adresse, String telephone){
        
        Patient p = new Patient(ipp,nom,prenom,sexe,dateDeNaissance,adresse,telephone);
        DMA dma = new DMA(p);
        DM dm = new DM(p);
        
              String sql="insert into patients(IPP,NOM,PRENOM,SEXE,DATENAISSANCE,ADRESSE,TELEPHONE) values (?,?,?,?,?,?,?)";
           
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
        
            pstm.setString(1,ipp);
            pstm.setString(2,nom);
            pstm.setString(3,prenom);
            pstm.setString(4,sexe.toString());            
            pstm.setString(5, dateDeNaissance);
            pstm.setString(6, adresse);
            pstm.setString(7, telephone);
            pstm.executeUpdate(); 
            
            
           }
           catch (Exception ex) {
            System.out.println(ex);
        }
    } 
public int nombrePatients() {
        int compteur = 0;
        try {
            String query = "select * from patients"; // la query à entrer pour accéder aux données de nos tables 
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
            String query = "select * from Patients"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String date = rs.getString("DATENAISSANCE");
                String nomEntier = nom + "         " + prenom+ "         " +date;
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

