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

/**
 *
 * @author poite
 */
public class Localisation {
    
    private Specialite specialite;
    private Orientation orientation;
    private int etage;
    private int chambre;
    private Lit lit;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public Localisation(){
      try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://mysql-dossmed.alwaysdata.net:3306/dossmed_bd", "dossmed", "projetsis"); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }

    }
    
    //Constructeur de localisation
    
    public Localisation(Specialite service, Orientation orientation,int etage, int chambre, Lit lit){
        this.specialite = service;
        this.orientation = orientation;
        this.etage = etage;
        this.chambre = chambre;
        this.lit =lit;
    }

   
        
        
       

    
    // getters et setters
    /**
     * @return the service
     */
    public Specialite getSpecialite() {
        return specialite;
    }

    /**
     * @param service the service to set
     */
    public void setSpecialite(Specialite spe) {
        this.specialite = spe;
    }

    /**
     * @return the orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * @return the etage
     */
    public int getEtage() {
        return etage;
    }

    /**
     * @param etage the etage to set
     */
    public void setEtage(int etage) {
        this.etage = etage;
    }

    /**
     * @return the chambre
     */
    public int getChambre() {
        return chambre;
    }

    /**
     * @param chambre the chambre to set
     */
    public void setChambre(int chambre) {
        this.chambre = chambre;
    }

    /**
     * @return the lit
     */
    public Lit getLit() {
        return lit;
    }

    /**
     * @param lit the lit to set
     */
    public void setLit(Lit lit) {
        this.lit = lit;
    }
    
    
    
    
    // Fonctions a coder en dessous
}
