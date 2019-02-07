/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

import java.sql.Date;




/**
 *
 * @author poite
 */
public class SIH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     // pour la BD 
      //BDconnect bd = new BDconnect(); 
      //bd.getData2();
      Connexion cx = new Connexion();
      cx.seConnecter("HYVES","YHEGEY");
     
        //SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567","pomme");
        //sm.ajouterNouveauPatient("37849205","poitevin","margaux",Sexe.FEMME,new Date(13,02,1997),"2 impasse","060504");
       
    
    }
}
