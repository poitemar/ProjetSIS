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
public class Infirmiere extends PersonnelMedical {
    
    public Infirmiere(String idMed, String nom, String prenom, String login, String password, Specialite spe, Service service) {
        super(idMed, nom, prenom, login, password, spe, service);
    }
    
    
}
