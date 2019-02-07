/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nf;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;







/**
 *
 * @author poite
 */
public class SIH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
     // pour la BD 
      //BDconnect bd = new BDconnect(); 
      //bd.getData2();

      //bd.ajouterDonnees();
//        SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567");
//        sm.ajouterNouveauPatient("37849205","poitevin","margaux",Sexe.FEMME,new Date(13,02,1997),"2 impasse","060504");
       RechercherInfo inf= new RechercherInfo();

//      Connexion cx = new Connexion();
//      cx.seConnecter("HYVES","YHEGEY");
//     
//        SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567","pomme");
//        Date d1 = new Date((1997-1900),(8-1),11);
//        sm.ajouterNouveauPatient("37849205","poitevin","margaux",Sexe.FEMME,d1,"2 impasse","060504");
//       

        System.out.println(inf.recherchePatientNomPrenom("Abichacra", "Lauren"));

//           while (info.next()){
//               try{
//              // exemple d'accès aux mots de passe et le log in de la connexion 
//      String x = info.getString("nom"); 
//      System.out.println(x);
//      }catch(Exception ex){
//              System.out.println(ex);
//          }
//           }
        
}}

             
////        PH ph = new PH("RACAMIER", "Axel", "16458A", "cardio", "login", "kiwi");
////        ph.ajouterSejour("médicaments", "le patient est..", "compte rendu du patient", "résultat des examens",
////                "operation du genou", "succes", "autorisé à sortir");
////        
////        SecretaireMedicale smed = new SecretaireMedicale("ABI CHACRA", "Lauren", "131174L", "poire");
////        smed.ajouterSejour("S111", null, ph, null);
    

