/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import static java.lang.Math.random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;
 
/**
 *
 * @author poite
 */
public class Patient {

    private String ipp;
    private String nom;
    private String prenom;
    private Sexe sexe;
    private String dateDeNaissance;
    private String adresse;
    private String telephone;

    private Connection con;
    private Statement st;
    private ResultSet rs; 
    
    
    public Patient() {

    }

    //Constructeur nom prenom pour rechercher le patient par nom et prenom 
    public Patient(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }

    }}
    
    //Constructeur utilisé pour rechercher les patients par nom, prenom et date --> pour bien gérer les doublons
     public Patient(String nom, String prenom, String date) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance=date;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }

    }}
     //Fonction qui retourne l'id du patient lu dans une liste
    public String ippPatientListe(String lecture){
        String p ="";
         try {
             String nomLu ="";
             String prenomLu="";
             String dateLue="";
             String[] result = lecture.split("\\s\\s\\s\\s\\s\\s\\s\\s\\s");
             
        for (int x=0; x<result.length; x++){
         nomLu =result[0];
            System.out.println(nomLu);
         prenomLu = result[1];
         System.out.println(prenomLu);
         dateLue =result[2];
         System.out.println(dateLue);
         
        }
            String query = "select * from patients where NOM='"+nomLu+"' and PRENOM='"+prenomLu+"'and DATENAISSANCE='"+dateLue+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                p = rs.getString("IPP");
                
        
               
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        return p;
    }

    // Constructeur de Patient
    public Patient(String ipp, String nom, String prenom, Sexe sexe, String dateDeNaissance, String adresse, String telephone){
        this.nom = nom;
        this.ipp = ipp;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.telephone = telephone;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }

        }
    }
    //Cette methode genere un ipp pour l'ajout d'un nouveau patient
    public String creationIPP_pour_ajout_patient (){
 
    Date maDate;
    SimpleDateFormat maDateLongue;
    maDate= new Date();
    maDateLongue= new SimpleDateFormat("yy");
    System.out.println("Année :"+ maDateLongue.format(maDate));

        
        int num3 = (int) Math.round(Math.random()*10);
        int num4 = (int) Math.round(Math.random()*10);
        int num5 = (int) Math.round(Math.random()*10);
        int num6 = (int) Math.round(Math.random()*10);
        int num7 = (int) Math.round(Math.random()*10);
        int num8 = (int) Math.round(Math.random()*10);
        int num9 = (int) (Math.random()*10);
        
        String IPP = ""+maDateLongue.format(maDate)+Integer.valueOf(num3)+Integer.valueOf(num4)+Integer.valueOf(num5)+Integer.valueOf(num6)+Integer.valueOf(num7)+Integer.valueOf(num8)+Integer.valueOf(num9);
     
        //System.out.println(IPP);
        return IPP;
    }

    // getters et setters
    /**
     * @return the ipp
     */
    public String getIpp() {
     
        try{
              String query = "select ipp from PATIENTS "; // la query à entrer pour accéder aux données de nos tables 
              rs= st.executeQuery(query);
              System.out.println("contenue de la base de donnée"); 
              while (rs.next()){
              String ipp = rs.getString("IPP");
              }
               }catch(Exception ex){
              System.out.println(ex);
          }

        return ipp;
    }

    /**
     * @param ipp the ipp to set
     */
    public void setIpp(String ipp) {
        this.ipp = ipp;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;  
    }
    

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;

    }

    /**
     * @return the prenom
     */
    public String getPrenom() {

        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {

        this.prenom = prenom;

    }

    /**
     * @return the sexe
     */
    public Sexe getSexe() {
        return sexe;
    }

    /**
     * @param sexe the sexe to set
     */
    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    /**
     * @return the dateDeNaissance
     */
    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    /**
     * @param dateDeNaissance the dateDeNaissance to set
     */
    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    /**
     * @return the address
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the address to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    //Fonctions à coder en dessous
}
