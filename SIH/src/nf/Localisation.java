/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

import java.text.SimpleDateFormat;

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
    
    
    //Constructeur de localisation
    
    public Localisation(Specialite service, Orientation orientation,int etage, int chambre, Lit lit){
        this.specialite = service;
        this.orientation = orientation;
        this.etage = etage;
        this.chambre = chambre;
        this.lit =lit;
    }
    
    public Localisation(Patient patient, java.util.Date date, Specialite service, String codeLocalisation){
        patient = new Patient(patient.getIpp(),patient.getNom(), patient.getPrenom(), this);
        java.util.Date maDate = new java.util.Date();
        SimpleDateFormat maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date = maDate;
        service = this.specialite;
        codeLocalisation = this.codeLocalisation();
    }

    public String codeLocalisation(){
        /*on utilise un code de localisation qui regroupe toutes les informations de localisation sous le format :
        X-X-XXX-X = ORIENTATION-ETAGE-CHAMBRE-LIT
        exemple : N-2-220-F */
        String Code_Localisation = " " + this.orientation.toString().substring(0,1) +"-"+this.etage+"-"+this.chambre+"-"+this.lit.toString().substring(0,1);
        return Code_Localisation;
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
