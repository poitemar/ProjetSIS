/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

//import java.util.Date;
import java.sql.Date;
import java.sql.SQLException;
import static nf.Specialite.ANESTHESIE;

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
//        String idSejour = "190221999";
//        String iPP = "idPatient";
//        String idPH ="idphtestonc";
//        Patient p =new Patient("bluff","bluff");
//        Localisation localisationbluff = new Localisation(Specialite.CARDIOLOGIE,Orientation.CENTRE,1,123,Lit.FENETRE);
//       Sejour sejourbluff = new Sejour("idSejour","idPatient","idPhReferent",localisationbluff);
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
  //     BDconnect bd = new BDconnect(); 
//    // bd.getData2();
      
    
       //        System.out.println(sejourbluff.listeSejour("526341897"));
     
//        System.out.println(sejourbluff.listeSaisie("190248662"));
    
    
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
        
//     
//        System.out.println(smed.idMed);
        //PH ph = new PH("RACAMIER", "Axel", "16458A", "cardio", "login", Specialite.ACCUEIL,Service.ACCUEIL);
      
           
//        System.out.println(ph.nom);
//        smed.ajouterSejour("S111", p, ph, loc, "//", "//", "//", "//","//", "//", "//");
//        ph.ajouterSejour("//", p, ph, loc, "médicaments", "le patient est", "compte rendu du patient", "résultat des examens","opération du genou", "succes", "autorisé à sortir");
//          PH test = new PH("bono","jean","hello","i am","here",Specialite.ANESTHESIE,Service.MEDICO_TECHNIQUE);
//          Sejour sej = new Sejour(); 
//          sej.idSejourPatientSelection("37849205");
//          test.ajouterPrestations("123","456","789","101112");
        //test.ajouterPrestations();
//         PH ph=  new nf.PH("null", "null", "null", "null", "null", nf.Specialite.ONCOLOGIE, nf.Service.CLINIQUE);
//         ph.iPPMedecinListe("HACHE");
////          String s= "123456789";
//         ph.iPPMedecinListe(s);
//         System.out.println(ph.iPPMedecinListe(s));
//         if(ph.iDPH(ph.iPPMedecinListe(s))==ph.iPPMedecinListe(s)){
//             System.out.println("yes--------------------------------------");
//    }
//       sejourbluff.ajouterOperations(idSejour, idPH, iPP, "titre", "details");
//     //  sejourbluff.completerSejour(idSejour, idPH, iPP, "ff", "ff", "fff", "nn", "hnh");
     SecretaireMedicale sm = new SecretaireMedicale("bluff", "bluff", "bluff", "bluff", "bluff", Specialite.ACCUEIL, Service.CLINIQUE);
     Patient p = sm.recuperationPatient("098765123");
     //   p.getLocalisation("098765123");
//        PH perso = new PH("bluff", "bluff", "bluff", "bluff", "bluff", Specialite.ACCUEIL, Service.CLINIQUE); 
//       ArrayList<Patient> listePatient = perso.afficherListePatientPrestation("238478562");
//        System.out.println(listePatient.size());
//        for (int i = 0; i < listePatient.size(); i++) {
//            String element = "" + listePatient.get(i).getNom() + "         " + listePatient.get(i).getPrenom() + "         " + listePatient.get(i).getDateDeNaissance();
//            System.out.println(element);
//
//            //Verifier que le dernier sejour du patient soit en cours avant de lafficher
//            nf.Localisation lbluff = new nf.Localisation(Specialite.ACCUEIL, Orientation.OUEST, 1,12, Lit.PORTE);
//            nf.Sejour sejourBluff = new nf.Sejour("", "", "", lbluff);
//            nf.PH ph = new nf.PH("", "", "", "", "", Specialite.ACCUEIL, Service.URGENCE);
//            String ipp = p.ippPatientListe(element);
//            String idDernierSejour = ph.idSejourPatientSelection(ipp);
//
//            if (sejourBluff.sejourEnCours(idDernierSejour)) {
//                System.out.println("OOOOK");
//            }
           
        }
//   //     System.out.println(perso.idSejourPatientSelection("1234"));
//             ArrayList <Patient> listeP = new ArrayList <Patient>();
//               listeP = sm.afficherListePatientParService(Specialite.ONCOLOGIE);
//                      for(int i =0; i<listeP.size();i++){
//  0                        System.out.println(listeP.get(i).getNom());
//                      }
//               ArrayList <PH> liste = new ArrayList <PH>();
//               liste = perso.afficherListeMedecinParService(Specialite.ANESTHESIE);
//               
//                      for(int i =0; i<liste.size();i++){
//                          System.out.println(liste.get(i).getNom());
//                      }
//        perso.iPPMedecinListe("CALABRESE         Julie");
        //System.out.println(p.ippPatientListe("TEST         ONCOLOGIE         1980-06-06"));
    
    
     //sejourbluff.sejourEnCours("190221999");
}
