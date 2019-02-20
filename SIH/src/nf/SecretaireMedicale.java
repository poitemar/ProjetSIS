/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import nf.Specialite;

/**
 *
 * @author PC
 */
public class SecretaireMedicale extends PersonnelMedical {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Specialite specialite;

    public SecretaireMedicale(String idMed, String nom, String prenom,String login, String password,Specialite spe,Service service) {
        super(idMed,nom, prenom, login, password,spe,service);
        this.specialite = spe;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }
   
//On cree un nouveau sejour mais on ne le remplit pas, l'operation creer le sejour et le remplir ne se deroulent pas dans la meme methode
    public void ajouterSejour(String idSejour, String iPP, String idPHReferent, Localisation localisation) {
       Sejour s = new Sejour(idSejour, iPP, idPHReferent, localisation);        
       String sql = "insert into ph_referent(ID_PHR,ID_SEJOUR,IPP,DATE_CREATION_SEJOUR) values (?,?,?,?)";
       String sql2 ="insert into localisation (ID_SEJOUR,SERVICE,ORIENTATION,ETAGE,CHAMBRE,LIT) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            PreparedStatement pstm2 = con.prepareStatement(sql2);
            Date maDate = new Date();
            SimpleDateFormat maDateLongue= new SimpleDateFormat("dd/MM/yyyy");
            
            //on insere les donnees dans la classe ph_referent ce qui correspond a la requete 1
            pstm.setString(1, idPHReferent);
            pstm.setString(2, idSejour);
            pstm.setString(3, iPP);
            pstm.setString(4, maDateLongue.format(maDate));
            pstm.executeUpdate();
            
            //On insere les donnees dans le classe localisation ce qui correspond a la requete 2
             pstm2.setString(1, idSejour);
            pstm2.setString(2, localisation.getService().toString());
           
             pstm2.setString(3, localisation.getOrientation().toString());
              pstm2.setInt(4, localisation.getEtage());
            pstm2.setInt(5, localisation.getChambre());
            pstm2.setString(6, localisation.getLit().toString());
           
            pstm2.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
    }
    
    //Fonction qui retourne l'idMed du medecin lu dans la liste
    public String iPPMedecinListe(String lecture){
        String ipp="";
         try {
             String nomLu ="";
             String prenomLu="";
             String[] result = lecture.split("\\s\\s\\s\\s\\s\\s\\s\\s\\s");
             
        for (int x=0; x<result.length; x++){
         nomLu =result[0];
         prenomLu = result[1];
         
        }
            String query = "select * from PERSONNEL_MEDICAL where NOM='"+nomLu+"' and PRENOM='"+prenomLu+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getString("ID_P");
                System.out.println(ipp);
             
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        return ipp;
    }
    
    public String creationID_Sejour (){
        /*création séjour : sous le format YYMMxxxxx où YY est l’année de la consultation ou de
        l’hospitalisation sur deux positions, MM le mois et xxxxx un compteur aléatoire sur cinq positions.*/
    Date maDate;
    SimpleDateFormat maDateLongue;
    maDate= new Date();
    maDateLongue= new SimpleDateFormat("yyMM");
    //System.out.println("Date :"+ maDateLongue.format(maDate));
        
        int num5 = (int) Math.round(Math.random()*10);
        int num6 = (int) Math.round(Math.random()*10);
        int num7 = (int) Math.round(Math.random()*10);
        int num8 = (int) Math.round(Math.random()*10);
        int num9 = (int) (Math.random()*10);
        
        String ID_Sejour = ""+maDateLongue.format(maDate)+Integer.valueOf(num5)+Integer.valueOf(num6)+Integer.valueOf(num7)+Integer.valueOf(num8)+Integer.valueOf(num9);
        return ID_Sejour;
    }
    
    
    public Specialite getSpecialite() {
        return specialite;
    }

    public int nombrePatients() {
        int compteur = 0;
        try {
            String query = "select * from PATIENTS join PH on (IPP=IPP_PATIENT) join PERSONNEL_MEDICAL on (ID_PH = ID_P) join LOCALISATION using (ID_SEJOUR) where LOCALISATION.service = 'Oncologie'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                compteur++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println("compteur = " + compteur);
        return compteur;
    }

   
  

   
    
    public ArrayList<Patient> afficherListePatientParService(Specialite spe){
        ArrayList<Patient> listePatient = new ArrayList<Patient>();
        
         try {
            String query = "select * from PATIENTS join PH_REFERENT on (PATIENTS.IPP=PH_REFERENT.IPP) join LOCALISATION using (ID_SEJOUR) where SERVICE ='"+spe+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
              System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                System.out.println(prenom);
                  String date = rs.getString("DATENAISSANCE");
                     //System.out.println(date);
                String idp = rs.getString("IPP");
                   //System.out.println(idPatient);
                
                     
                   Sexe sexeLu = (Sexe) Enum.valueOf(Sexe.class, rs.getString("SEXE"));
                  String adresse = rs.getString("ADRESSE");
                   // System.out.println(Adresse);
                  String tel = rs.getString("TELEPHONE");
                     //System.out.println(tel);                 
                     
                 Patient patient = new Patient(idp,nom,prenom,sexeLu,date,adresse,tel);
                   //   System.out.println(docteur.getNom());
                 //System.out.println(docteur.getSpecialite().toString());
                 listePatient.add(patient);
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        
        return listePatient;
     
}
   
    
    
    public ArrayList<PH> afficherListeMedecinParService(Specialite spe){
        ArrayList<PH> listeMed = new ArrayList<PH>();
        
         try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P='DOCTEUR' and SPE='"+spe+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
               // System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                 //  System.out.println(prenom);
                String idMed = rs.getString("ID_P");
                   //System.out.println(idMed);
                 String login = rs.getString("LOGIN");
                   // System.out.println(login);
                  String mdp = rs.getString("MDP");
                     //System.out.println(mdp);
                     
                   Specialite speLue = (Specialite) Enum.valueOf(Specialite.class, rs.getString("SPE"));
                                               // System.out.println(speLue);
                 Service servicelu =(Service) Enum.valueOf(Service.class, rs.getString("SERVICE"));
                 //   System.out.println(servicelu);
                 PH docteur = new PH(idMed,nom,prenom,login,mdp,speLue,servicelu);
                   //   System.out.println(docteur.getNom());
                 //System.out.println(docteur.getSpecialite().toString());
                 listeMed.add(docteur);
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        
        return listeMed;
     
}
}