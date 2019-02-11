/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

//import java.util.Date;
import java.sql.Date;
import java.sql.SQLException;




/**
 *
 * @author poite
 */
public class SIH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
      //pour la BD 
//      BDconnect bd = new BDconnect(); 
//      bd.ajouterSejour();
//      bd.getData2();
//      Connexion cx = new Connexion();
//      cx.seConnecter("HYVES","YHEGEY");
     
//        SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567","pomme");
//        Date d1 = new Date((1997-1900),(8-1),11);
//        sm.ajouterNouveauPatient("1212122","HELLE","margaux",Sexe.FEMME,d1,"2 impasse","060504");
    
//    Patient p = new Patient("21","Longchamp","Juliette",Sexe.FEMME,new Date(15,01,1996),"78 boulevard","46000");
//        System.out.println(p.creationIPP_pour_ajout_patient());
     // pour la BD 
       BDconnect bd = new BDconnect(); 
     bd.getData2();
     // bd.ajouterDonnees();
      // SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567","123");
       // sm.ajouterNouveauPatient("521","Longchamp","Juliette",Sexe.FEMME,new Date(15,01,1996),"78 boulevard","46000");
//      
//        RechercherInfo inf= new RechercherInfo();
//       
//       System.out.println(inf.recherchePatientNomPrenom("Longchamp", "Juliette"));
//      Patient p= new Patient ("21","Longchamp","Juliette",Sexe.FEMME,new Date(15,01,1996),"78 boulevard","46000");
      //p.getNom();
////           while (info.next()){
//               try{
//              // exemple d'accès aux mots de passe et le log in de la connexion 
//      String x = info.getString("nom"); 
//      System.out.println(x);
//      }catch(Exception ex){
//              System.out.println(ex);
//          }
//           }
        
       
//        Localisation loc = new Localisation(Service.CLINIQUE, Orientation.EST, 7, 56, Lit.FENETRE);
////        Patient p = new Patient("378021405","poitevin","margaux",Sexe.FEMME,d1,"2 impasse","060504");
//        
//        SecretaireMedicale smed = new SecretaireMedicale("ABI CHACRA", "Lauren", "131174L", "poire");
//        System.out.println(smed.idMed);
//        PH ph = new PH("RACAMIER", "Axel", "16458A", "cardio", "login", "kiwi");
//        System.out.println(ph.nom);
//        smed.ajouterSejour("S111", p, ph, loc, "//", "//", "//", "//","//", "//", "//");
//        ph.ajouterSejour("//", p, ph, loc, "médicaments", "le patient est", "compte rendu du patient", "résultat des examens","opération du genou", "succes", "autorisé à sortir");
          SecretaireMedicale test = new SecretaireMedicale("null","null","null","null","null");
          test.afficherListePatients();
    }
}
