/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

/**
 *
 * @author PC
 */
public class PersonnelMedical {

    public String nom;
    public String prenom;
    public String  idMed;
    public String specialite;

    public PersonnelMedical(String nom, String prenom, String idMed, String specialite) {
        this.nom = nom;
        this.prenom = prenom;
        this.idMed = idMed;
        this.specialite = specialite;

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

    //to set the speciality of the medical staff 
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
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
    public String getIdMed(){
        return idMed; 
    }
    //to get the speciality of the medical staff
    public String getSpecialite() {
        return specialite;
    }
}
