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
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class PersonnelMedical {

    /**
     * @return the service
     */
    public Service getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * @return the specialite
     */
    public Specialite getSpecialite() {
        return specialite;
    }

    private String nom;
    private String prenom;
    private String idMed;
    private Specialite specialite;
    private Service service;
    private String login;

    private String password;

    private Connection con;
    private Statement st;
    private ResultSet rs;

    /**
     *
     * @param idMed
     * @param nom
     * @param prenom
     * @param login
     * @param password
     * @param spe
     * @param service
     */
    public PersonnelMedical(String idMed, String nom, String prenom, String login, String password, Specialite spe, Service service) {
        this.idMed = idMed;
        this.nom = nom;
        this.prenom = prenom;
        
        this.login = login;
        this.password = password;
        this.specialite = spe;
        this.service = service;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);

        }
    }

    /**
     *
     * @param loginTest
     * @param mdpTest
     * @param newmdp
     */
    public void changerMotDePasse(String loginTest, String mdpTest,String newmdp) {
        /** On prend en entrée le login actuel, le mot de passe actuel et le nouveau mot de passe
         et on met à jour la base de données afin que le mot de passe soit modifié */
//Exemple de modification de donnees dans la BD:
//"update reservation set busname='"+jTextField10.getText()+"',busno='"+jTextField9.getText()+"',cusname='"+jTextField8.getText()+"',noofpass='"+jTextField7.getText()+"',amount='"+jTextField6.getText()+"' where cusname='"+jTextField8.getText()+"' ")
           
//   Remplacement du nouveau mdp
        
         String sql = "update personnel_medical set MDP='"+newmdp+"' where LOGIN='"+loginTest+"' and MDP='"+mdpTest+"'";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

             pstm.executeUpdate(); 

        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        }
    
    
   //Afficher la liste des patients pour lequel le ph a reçu une prestation 

    /**
     *
     * @param idmed
     * @return
     */
    public ArrayList<Patient> afficherListePatientPrestation(String idmed){
        /** On prend en entrée l'id d'un médecin et on renvoie une liste des patients dont le médecin
         est réferent */
        ArrayList<Patient> listePatient = new ArrayList<Patient>();
        
         try {
            String query = "select * from patients join ph_referent using(IPP) join prestations using(ID_SEJOUR) where ID_DR_RECEVEUR ='"+idmed+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
            
                String nom = rs.getString("NOM");
              System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                System.out.println(prenom);
                  String date = rs.getString("DATENAISSANCE");
                     //System.out.println(date);
                String idp = rs.getString("IPP");
                   //System.out.println(idPatient);
                
                     
                   Sexe sexeLu = (Sexe) Enum.valueOf(Sexe.class, rs.getString("SEXE"));
                  String adresse = rs.getString("ADRESSE");
                   // System.out.println(Adresse);
                  String tel = rs.getString("TELEPHONE");
                  String nomC = rs.getString("NOMCONF");
                  String prenomC = rs.getString("PRENOMCONF");
                  String adresseC = rs.getString("ADRESSECONF");
                  String telC = rs.getString("TELEPHONECONF");
                     //System.out.println(tel);                 
                     
                 Patient patient = new Patient(idp,nom,prenom,sexeLu,date,adresse,tel,nomC,prenomC,adresseC,telC);
                   //   System.out.println(docteur.getNom());
                 //System.out.println(docteur.getSpecialite().toString());
                 if(!listePatient.contains(patient)){
                        listePatient.add(patient);
                 }
              
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        
        return listePatient;
     
}
 
        
    
     //Retourne la liste de prestation d'un medecin

    /**
     *
     * @param idmed
     * @param idSejour
     * @return
     */
    public ArrayList<String> afficherDatePrestation(String idmed,String idSejour){
        /** On prend en entrée l'id d'un médecin et l'id d'un séjour et on renvoie les dates auxquelles
         les prestations ont été réalisées*/
        ArrayList<String> listePrestations = new ArrayList<String>();
        
         try {
            String query = "select * from patients join ph_referent using(IPP) join prestations using(ID_SEJOUR) where ID_DR_RECEVEUR ='"+idmed+"' and ID_SEJOUR='"+idSejour+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
           
                String date = rs.getString("DATE_SAISIE");
          
           
                 
                   //   System.out.println(docteur.getNom());
                 //System.out.println(docteur.getSpecialite().toString());
                 
                 listePrestations.add(date); //on ajoute dans une liste les dates des prestations
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
         return listePrestations;
    } 
    
    /**
     *
     * @param date
     * @param idSejour
     * @return
     */
    public String afficherPrestation(String date, String idSejour){
         /** On prend en entrée une date et un id séjour et on renvoie la prestation associée dans un String */
      String prest ="";
        
         try {
            String query = "select * from prestations where DATE_SAISIE='"+date+"' and ID_SEJOUR='"+idSejour+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
           
                 prest = rs.getString("PRESTATION");
          
           
                 
                   //   System.out.println(docteur.getNom());
                 //System.out.println(docteur.getSpecialite().toString());
                 
                               
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
         return prest;
    } 
    
       //Fonction qui retourne la prestation associée à la selection dans une liste

    /**
     *
     * @param lecture
     * @return
     */
    public String prestationPatientListe(String lecture){
        /** On prend en entrée une sélection dans une liste et on renvoie
         le nom, le prénom, la date de prestation et la prestation associés à la sélection */
       
       
             String nomLu ="";
             String prenomLu="";
             String dateLue="";
             String prestationLue="";
             String[] result = lecture.split("\\s\\s\\s\\s\\s\\s\\s\\s\\s");
             
        for (int x=0; x<result.length; x++){
         nomLu =result[0];
            System.out.println(nomLu);
         prenomLu = result[1];
         System.out.println(prenomLu);
         dateLue =result[2];
         System.out.println(dateLue);
         prestationLue = result[3];
        
         
        }
        return prestationLue;
    }
    
        //to set the last name of the medical staff

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;

    }

    //to set the first name of the medical staff

    /**
     *
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    //to set the Id of  the medical staff

    /**
     *
     * @param idMed
     */
    public void setIdMed(String idMed) {
        this.idMed = idMed;
    }

    //to get the last name of the medical staff

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    //to get the first name of the medical staff

    /**
     *
     * @return
     */
    public String getPrenom() {
        return prenom;
    }

    //to get the ID of the medical staff

    /**
     *
     * @return
     */
    public String getIdMed() {
        return idMed;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    
    //retourne true si le medecin est le ph référent du séjour

    /**
     *
     * @param idMed
     * @param idSejour
     * @return
     */
    public boolean estReferent(String idMed,String idSejour){
        /** On prend en entrée l'id médecin et l'id d'un séjour et on renvoie true ou false selon
         si le médecin était référent ou non pour ce séjour */
        try {
            String query = "select ID_PHR from ph_referent where ID_SEJOUR ='"+idSejour+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
           
                String idMed2 = rs.getString("ID_PHR");
          
           if (idMed2.equals(idMed)){ //si l'id en entrée correspond à l'id d'un ph référent à un séjour donné
               System.out.println("C'est le med referent");
               return true; //si c'est le ph référent
               
           }
        
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
    
        
        return false; // si ce n'est pas le ph référent
    }
    
}
