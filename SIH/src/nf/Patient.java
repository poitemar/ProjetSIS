/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author poite
 */
public class Patient {

    private String ipp;
    private String nom;
    private String prenom;
    private Sexe sexe;
    private Date dateDeNaissance;
    private String adresse;
    private String telephone;

    private Connection con;
    private Statement st;
public Patient(){
    
}
    //Constructeur nom prenom pour rechercher le patient par nom et prenom 
     public Patient (String nom, String prenom){
         this.nom=nom; 
         this.prenom=prenom; 
         
         
     }

    // Constructeur de Patient
    public Patient(String ipp, String nom, String prenom, Sexe sexe, Date dateDeNaissance, String adresse, String telephone) throws ClassNotFoundException, SQLException {
        this.nom = nom;
        this.ipp = ipp;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.telephone = telephone;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }

        }}

 
        // getters et setters
        /**
         * @return the ipp
         */
     public String getIpp() {

        return ipp;
     }

    /**
     * @param ipp the ipp to set
     */
    public void setIpp(String ipp) {
       this.ipp=ipp;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
     this.nom=nom;
        
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
                    
      this.prenom=prenom;

    }
    

    /**
     * @return the sexe
     */
    public Sexe getSexe() {
        return sexe;
    }

    /**
     * @param sexe the sexe to set
     */
    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    /**
     * @return the dateDeNaissance
     */
    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    /**
     * @param dateDeNaissance the dateDeNaissance to set
     */
    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    /**
     * @return the address
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the address to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    //Fonctions à coder en dessous
}
