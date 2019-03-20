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
import java.util.Collections;

/**
 *
 * @author PC
 */
public class SecretaireAdministrative extends PersonnelMedical{
     private Connection con;
      private Statement st;
      private Specialite specialite;
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
    public SecretaireAdministrative(String idMed,String nom, String prenom, String login, String password, Specialite spe,Service service) {
        super(idMed,nom, prenom,login,password,spe,service);
        this.specialite = Specialite.ACCUEIL;
         try{
            Class.forName("com.mysql.jdbc.Driver");
            
          con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
          st = con.createStatement();
            
        }catch(Exception ex) {
            System.out.println("error :" +  ex );
            
        }
    }
    
    /**
     *
     * @param ipp
     * @param nom
     * @param prenom
     * @param sexe
     * @param dateDeNaissance
     * @param adresse
     * @param telephone
     * @param nomCONF
     * @param prenomCONF
     * @param adresseCONF
     * @param telCONF
     */
    public void ajouterNouveauPatient(String ipp, String nom, String prenom, Sexe sexe, String dateDeNaissance, String adresse, String telephone,String nomCONF,String prenomCONF, String adresseCONF,String telCONF){
        /* On ajoute un nouveau patient avec un ipp, un nom, un prénom, un sexe, une date de naissance, une adresse, un numéro de téléphone, le nom de la personne de confiance, son prénom, son adresse et son numéro de téléphone dans la bd **/
        Patient p = new Patient(ipp,nom,prenom,sexe,dateDeNaissance,adresse,telephone,nomCONF,prenomCONF,adresseCONF,telCONF);
        DMA dma = new DMA(p);
        DM dm = new DM(p);
        
              String sql="insert into patients(IPP,NOM,PRENOM,SEXE,DATENAISSANCE,ADRESSE,TELEPHONE,NOMCONF,PRENOMCONF,ADRESSECONF,TELEPHONECONF) values (?,?,?,?,?,?,?,?,?,?,?)";
           
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
        
            pstm.setString(1,ipp);
            pstm.setString(2,nom);
            pstm.setString(3,prenom);
            pstm.setString(4,sexe.toString());            
            pstm.setString(5, dateDeNaissance);
            pstm.setString(6, adresse);
            pstm.setString(7, telephone);
            pstm.setString(8, nomCONF);
            pstm.setString(9, prenomCONF);
            pstm.setString(10, adresseCONF);
            pstm.setString(11, telCONF);
            pstm.executeUpdate(); 
            
            
           }
           catch (Exception ex) {
            System.out.println(ex);
        }
    } 

    /**
     *
     * @return
     */
    public int nombrePatients() {
        /* Renvoie le nombre de patients dans la bd **/
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

    /**
     *
     * @return
     */
    public String[] afficherListePatients() {
        /* On renvoie un tableau de String contenant les noms et prénoms des patients de la base de données **/
        int compteur = nombrePatients();
        int i=0;
        String[] listePatients = new String[compteur];
        try {
            String query = "select * from patients order by NOM"; // la query à entrer pour accéder aux données de nos tables 
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
    
    /**
     *
     * @param ipp
     * @param nom
     * @param prenom
     * @param sexe
     * @param dateNaissance
     * @param adresse
     * @param telephone
     * @param nomCONF
     * @param prenomCONF
     * @param adresseCONF
     * @param telCONF
     */
    public void modifierPatient(String ipp, String nom, String prenom, Sexe sexe, String dateNaissance, String adresse, String telephone,String nomCONF,String prenomCONF, String adresseCONF,String telCONF){
        /* On modifie les champs de la table "patient" avec ceux en entrée de cette fonction **/
        Patient patient = new Patient(ipp, nom, prenom, sexe, dateNaissance, adresse, telephone,nomCONF,prenomCONF,adresseCONF,telCONF);
        String sql = "update patient set NOM='"+patient.getNom()
                +"', PRENOM='"+patient.getPrenom()
                +"', SEXE='"+patient.getSexe()
                +"', DATENAISSANCE='"+patient.getDateDeNaissance()
                +"', ADRESSE='"+patient.getAdresse()
                +"', TELEPHONE='"+patient.getTelephone()
                +"', NOMCONF='"+patient.getNomC()
                +"', PRENOMCONF='"+patient.getPrenomC()
                +"', ADRESSECONF='"+patient.getAdresseC()
                +"', TELEPHONECONF='"+patient.getTelC()
                +"' where IPP='"+ipp+"'";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            //On insere les donnees dans le classe localisation ce qui correspond a la requete 2
            pstm.setString(1, ipp);
            pstm.setString(2, nom);
            pstm.setString(3, prenom);
            pstm.setString(4, sexe.toString());
            pstm.setString(5, dateNaissance);
            pstm.setString(6, adresse);
            pstm.setString(7, telephone);
            pstm.setString(8, nomCONF);
            pstm.setString(9, prenomCONF);
            pstm.setString(10, adresseCONF);
            pstm.setString(11, telCONF);
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
}

