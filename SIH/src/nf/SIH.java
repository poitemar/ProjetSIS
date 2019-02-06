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
public class SIH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     // pour la BD 
        BDconnect bd = new BDconnect(); 
      //bd.getData2();
      //bd.ajouterDonnees();
        SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567");
        sm.ajouterNouveauPatient("37849205","poitevin","margaux",Sexe.FEMME,new Date(11,8,1997),"2 impasse","060504");
       
    
    }
}
