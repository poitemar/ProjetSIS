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
import java.util.Date;

/**
 *
 * @author PC
 */
public class SecretaireAdministrative extends PersonnelMedical{
     private Connection con;
      private Statement st; 
     
    public SecretaireAdministrative(String nom, String prenom, String idMed) {
        super(nom, prenom, idMed);
         try{
            Class.forName("com.mysql.jdbc.Driver");
            
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/bd","root",""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();
            
        }catch(Exception ex) {
            System.out.println("error :" +  ex );
            
        }
    }
    

    public void ajouterNouveauPatient(String ipp, String nom, String prenom, Sexe sexe, Date dateDeNaissance, String adresse, String telephone){
        
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
            pstm.setDate(5, (java.sql.Date) dateDeNaissance);
            pstm.setString(6, adresse);
            pstm.setString(7, telephone);
            pstm.executeUpdate(); 
            
            
           }
           catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
