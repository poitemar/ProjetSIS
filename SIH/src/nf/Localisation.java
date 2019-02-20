/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

/**
 *
 * @author poite
 */
public class Localisation {
    
    private Specialite service;
    private Orientation orientation;
    private int etage;
    private int chambre;
    private Lit lit;
    
    
    //Constructeur de localisation
    
    public Localisation(Specialite service, Orientation orientation,int etage, int chambre, Lit lit){
        this.service = service;
        this.orientation = orientation;
        this.etage = etage;
        this.chambre = chambre;
        this.lit =lit;
    }

   
        
        
       

    
    // getters et setters
    /**
     * @return the service
     */
    public Specialite getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(Specialite service) {
        this.service = service;
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
