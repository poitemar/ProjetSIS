/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

//import java.util.Date;
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
      //pour la BD 
//      BDconnect bd = new BDconnect(); 
//      bd.ajouterSejour();
//      bd.getData2();
//      Connexion cx = new Connexion();
//      cx.seConnecter("HYVES","YHEGEY");
     
        SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567","pomme");
        Date d1 = new Date((1997-1900),(8-1),11);
       // sm.ajouterNouveauPatient("37849875","poitevin","margaux",Sexe.FEMME,d1,"2 impasse","060504");
        
       
        Localisation loc = new Localisation(Service.CLINIQUE, Orientation.EST, 7, 56, Lit.FENETRE);
//        Patient p = new Patient("378021405","poitevin","margaux",Sexe.FEMME,d1,"2 impasse","060504");
        
        SecretaireMedicale smed = new SecretaireMedicale("ABI CHACRA", "Lauren", "131174L", "poire");
        System.out.println(smed.idMed);
        PH ph = new PH("RACAMIER", "Axel", "16458A", "cardio", "login", "kiwi");
        System.out.println(ph.nom);
//        smed.ajouterSejour("S111", p, ph, loc, "//", "//", "//", "//","//", "//", "//");
//        ph.ajouterSejour("//", p, ph, loc, "médicaments", "le patient est", "compte rendu du patient", "résultat des examens","opération du genou", "succes", "autorisé à sortir");
                
    }
}
