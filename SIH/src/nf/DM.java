/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author poite
 */
public class DM {
    private Patient patient;
    private List<Sejour> listeDeSejour;
    
    
    // Constructeur DM
    //Creation d'un DM implique un patient en parametre

    /**
     *
     * @param patient
     */
    public DM(Patient patient){
        this.patient = patient;
        this.listeDeSejour = new ArrayList<Sejour>();
    }

    
    
    // getters et setters
    /**
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * @param patient the patient to set
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * @return the listeDeSejour
     */
    public List<Sejour> getListeDeSejour() {
        return listeDeSejour;
    }

    /**
     * @param listeDeSejour the listeDeSejour to set
     */
    public void setListeDeSejour(List<Sejour> listeDeSejour) {
        this.listeDeSejour = listeDeSejour;
    }
   
    
    // Fonctions à coder en dessous
   
}
