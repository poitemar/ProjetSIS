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

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);

        }
    }

    public String nouvelId() {
        return "1234";

    }

    public void changerMotDePasse(String loginTest, String mdpTest,String newmdp) {
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
