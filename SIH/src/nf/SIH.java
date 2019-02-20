/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

//import java.util.Date;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author poite
 */
public class SIH {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String idSejour = "190221999";
        String iPP = "idPatient";
        String idPH ="idphtestonc";
        Patient p =new Patient("bluff","bluff");
        Localisation localisationbluff = new Localisation(Specialite.CARDIOLOGIE,Orientation.CENTRE,1,123,Lit.FENETRE);
       Sejour sejourbluff = new Sejour("idSejour","idPatient","idPhReferent",localisationbluff);
       
       sejourbluff.ajouterOperations(idSejour, idPH, iPP, "titre", "details");
     //  sejourbluff.completerSejour(idSejour, idPH, iPP, "ff", "ff", "fff", "nn", "hnh");
     SecretaireMedicale sm = new SecretaireMedicale("bluff", "bluff", "bluff", "bluff", "bluff", Specialite.ACCUEIL, Service.CLINIQUE);
        PH perso = new PH("bluff", "bluff", "bluff", "bluff", "bluff", Specialite.ACCUEIL, Service.CLINIQUE); 
   //     System.out.println(perso.idSejourPatientSelection("1234"));
             ArrayList <Patient> listeP = new ArrayList <Patient>();
               listeP = sm.afficherListePatientParService(Specialite.ONCOLOGIE);
                      for(int i =0; i<listeP.size();i++){
                          System.out.println(listeP.get(i).getNom());
                      }
     
//               ArrayList <PH> liste = new ArrayList <PH>();
//               liste = perso.afficherListeMedecinParService(Specialite.ANESTHESIE);
//               
//                      for(int i =0; i<liste.size();i++){
//                          System.out.println(liste.get(i).getNom());
//                      }
//        perso.iPPMedecinListe("CALABRESE         Julie");
           
        //System.out.println(p.ippPatientListe("TEST         ONCOLOGIE         1980-06-06"));
    
    
     //sejourbluff.sejourEnCours("190221999");
}}
