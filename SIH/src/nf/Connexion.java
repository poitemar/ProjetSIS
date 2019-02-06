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
public class Connexion extends PersonnelMedical{
    
    BDconnect bd = new BDconnect();
    
    public Connexion(String nom, String prenom, String idMed, String specialite, String login, String password) {
        super(nom, prenom, idMed, specialite,login,password);
    }
    
    public void seConnecter(String login, String password){
        
    }
    
}
