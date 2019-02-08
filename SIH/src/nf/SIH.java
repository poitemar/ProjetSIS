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
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
     // pour la BD 
//       BDconnect bd = new BDconnect(); 
//     bd.getData2();
     // bd.ajouterDonnees();
      // SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567","123");
       // sm.ajouterNouveauPatient("521","Longchamp","Juliette",Sexe.FEMME,new Date(15,01,1996),"78 boulevard","46000");
      
        RechercherInfo inf= new RechercherInfo();
       
       System.out.println(inf.recherchePatientNomPrenom("Longchamp", "Juliette"));
      //Patient p= new Patient ("21","Longchamp","Juliette",Sexe.FEMME,new Date(15,01,1996),"78 boulevard","46000");
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
        
}}
