/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

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
    
    // Constructeur de Patient
    public Patient(String ipp, String nom, String prenom, Sexe sexe, Date dateDeNaissance, String adresse, String telephone){
        this.nom = nom;
        this.ipp=ipp;
        this.prenom=prenom;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
       
    }
    
    
    
    
}
