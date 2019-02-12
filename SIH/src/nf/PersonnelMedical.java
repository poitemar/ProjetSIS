/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.DriverManager;
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

   
    public PersonnelMedical(String idMed, String nom, String prenom, String login, String password, Specialite spe, Service service){
        this.nom = nom;
        this.prenom = prenom;
        this.idMed =idMed;
        this.login = login;
        this.password = password;
        this.specialite=spe;
        this.service=service;
         try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);

        }
    }
    public String nouvelId(){
      return "1234";
      
    }
    
  
   
    //to set the last name of the medical staff
    public void setNom(String nom) {
        this.nom = nom;

    }

    //to set the first name of the medical staff
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    //to set the Id of  the medical staff
    public void setIdMed(String idMed) {
        this.idMed = idMed;
    }

    //to get the last name of the medical staff
    public String getNom() {
        return nom;
    }

    //to get the first name of the medical staff
    public String getPrenom() {
        return prenom;
    }

    //to get the ID of the medical staff
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
}
