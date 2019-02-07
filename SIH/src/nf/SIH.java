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
        BDconnect bd = new BDconnect(); 
      //bd.getData2();
      //bd.ajouterDonnees();
//        SecretaireAdministrative sm = new SecretaireAdministrative("MAURIOL", "Marine","087567");
//        sm.ajouterNouveauPatient("37849205","poitevin","margaux",Sexe.FEMME,new Date(13,02,1997),"2 impasse","060504");
       RechercherInfo inf= new RechercherInfo();
       
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
