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
import java.util.Objects;
 
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
    private Localisation localisation;
     private String nomC;
    private String prenomC;
     private String adresseC;
    private String telC;

    /**
     *
     */
    public Patient() {

    }
   
    //Constructeur nom prenom pour rechercher le patient par nom et prenom 

    /**
     *
     * @param nom
     * @param prenom
     */
    public Patient(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        try {
            Class.forName("com.mysql.jdbc.Driver");

             con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
             st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }

    }}
    
        
        
    //Constructeur utilisé pour rechercher les patients par nom, prenom et date --> pour bien gérer les doublons

    /**
     *
     * @param nom
     * @param prenom
     * @param date
     */
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

    /**
     *
     * @param lecture
     * @return
     */
    public String ippPatientListe(String lecture){
        /** on prend en entrée le nom et le prénom d'un patient, séparés par un espace et concaténés 
         dans un String et on renvoie son IPP associé*/
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

    /**
     *
     * @param ipp
     * @return
     */
    public Patient getPatient(String ipp){
          /** on prend en entrée l'ipp d'un patient, et on renvoie un patient en sortie */
        String nomp,prenomp,datep,ippp ="";
        Patient p = new Patient("","","");
         try {
             
        
            String query = "select * from patients where IPP='"+ipp+"'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                ippp = rs.getString("IPP");
                nomp = rs.getString("NOM");
                prenomp = rs.getString("PRENOM");
                datep = rs.getString("DATENAISSANCE");
                Sexe sexep = Sexe.valueOf(rs.getString("SEXE"));
        
               p = new Patient(ipp,nomp,prenomp,datep,sexep);
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
        return p;
    }
    
    
        //Fonction qui retourne le nom et le prenom du patient lu dans une liste

    /**
     *
     * @param lecture
     * @return
     */
    public String patientListe(String lecture){
        /** On prend en entrée le nom et le prénom d'un patient séparés par un espace et
         concaténés dans un String et on renvoie le nom et le prénom de ce patient séparés par un espace
         et concaténés dans un String */
        String p ="";
         
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
         
         p = nomLu+" " + prenomLu;
      
        
    }
    return p;}
    
    /**
     *
     * @param ipp
     * @param nom
     * @param prenom
     * @param date
     * @param sexe
     */
    public Patient(String ipp, String nom, String prenom,String date, Sexe sexe){
        this.nom = nom;
        this.ipp = ipp;
        this.prenom = prenom;
        this.dateDeNaissance =date;
        this.sexe=sexe;
        }
        
    // Constructeur de Patient

    /**
     *
     * @param ipp
     * @param nom
     * @param prenom
     * @param sexe
     * @param dateDeNaissance
     * @param adresse
     * @param telephone
     * @param nomCONF
     * @param prenomCONF
     * @param adresseCONF
     * @param telCONF
     */
    public Patient(String ipp, String nom, String prenom, Sexe sexe, String dateDeNaissance, String adresse, String telephone,String nomCONF,String prenomCONF,String adresseCONF,String telCONF){
        this.nom = nom;
        this.ipp = ipp;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.nomC = nomCONF;
        this.prenomC = prenomCONF;
        this.adresseC = adresseCONF;
        this.telC = telCONF;
        
               

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
    
    /**
     *
     * @param ipp
     * @param nom
     * @param prenom
     * @param localisation
     */
    public Patient(String ipp, String nom, String prenom, Localisation localisation){
        this.ipp = ipp;
        this.nom = nom;
        this.prenom = prenom;
        this.localisation = localisation;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://mysql-dossmed.alwaysdata.net:3306/dossmed_bd", "dossmed", "projetsis"); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {

            {
                System.out.println("error :" + ex);

            }

        }
    }
    //Cette methode genere un ipp pour l'ajout d'un nouveau patient

    /**
     *
     * @return
     */
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
        if (num9>9){
            num9 = num9-1;
        }
        String IPP = ""+maDateLongue.format(maDate)+Integer.valueOf(num3)+Integer.valueOf(num4)+Integer.valueOf(num5)+Integer.valueOf(num6)+Integer.valueOf(num7)+Integer.valueOf(num8)+Integer.valueOf(num9);
     
        //System.out.println(IPP);
        return IPP;
    }

    /**
     *
     * @return
     */
    public String getipp() {
        return this.ipp;
    }
    // getters et setters
    /**
     * @return the ipp
     */
    public String getIpp() {
     
        try{
              String query = "select ipp from patients "; // la query à entrer pour accéder aux données de nos tables 
              rs= st.executeQuery(query);
              System.out.println("contenu de la base de donnée"); 
              while (rs.next()){
              String ipp = rs.getString("IPP");
              }
               }catch(Exception ex){
              System.out.println(ex);
          }

        return ipp;
    }
    
    /**
     *
     * @param iPP
     * @param idSejour
     * @return
     */
    public String getLocalisation(String iPP,String idSejour) {
         /** on prend en entrée l'ipp et l'id séjour d'un patient et on renvoie dans un String le service
          dans lequel il se trouve, son orientation, son étage, sa chambre et son lit*/
     String loc="";
        try{
              String query = "select * from localisation join ph_referent using(ID_SEJOUR) join patients where patients.IPP='"+iPP+"' and ID_SEJOUR='"+idSejour+"'"; // la query à entrer pour accéder aux données de nos tables 
              System.out.println(query);
              rs= st.executeQuery(query);
           
              while (rs.next()){
            
                String service = rs.getString("SERVICE");// pour avoir accès a la colonne de ma table 
                String orientation = rs.getString("ORIENTATION");
                String chambre = rs.getString("CHAMBRE");
                String etage = rs.getString("ETAGE");
                String lit = rs.getString("LIT");   
                loc = service +" "+orientation+" "+etage+" "+chambre+" "+lit;
              }
               }catch(Exception ex){
              System.out.println(ex);
          }

        return loc;
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
    
    /**
     * @return the telephone
     */
    public Localisation getLocalisation() {
        return localisation;
    }

    /**
     * @param localisation
     */
    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    //Fonctions à coder en dessous

    /**
     *
     * @return
     */
    
    public String servicePatient(){
        String service="";
        
        
        return service; 
    }
    
    /**
     *
     * @param IPP
     * @return
     */
    public String afficherPersonneConfiance(String IPP){
        /** on prend en entrée l'ipp d'un patient et on renvoie le nom, le prénom, l'adresse et le téléphone
         de la personne de confiance associée dans un seul String*/
        String persConf = "";
        String adresse="";
        String tel="";
        try {
            String query = "select * from patients where IPP='" + IPP + "'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOMCONF");
                String prenom = rs.getString("PRENOMCONF");
                adresse = rs.getString("ADRESSECONF");
                tel = rs.getString("TELEPHONECONF");
                persConf = nom + " " + prenom;
                
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        System.out.println(persConf + "\n" + adresse +"\n"+tel);
     
        return persConf + "\n" + adresse +"\n"+tel;
    }

    /**
     * @return the nomC
     */
    public String getNomC() {
        return nomC;
    }

    /**
     * @param nomC the nomC to set
     */
    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    /**
     * @return the prenomC
     */
    public String getPrenomC() {
        return prenomC;
    }

    /**
     * @param prenomC the prenomC to set
     */
    public void setPrenomC(String prenomC) {
        this.prenomC = prenomC;
    }

    /**
     * @return the adresseC
     */
    public String getAdresseC() {
        return adresseC;
    }

    /**
     * @param adresseC the adresseC to set
     */
    public void setAdresseC(String adresseC) {
        this.adresseC = adresseC;
    }

    /**
     * @return the telC
     */
    public String getTelC() {
        return telC;
    }

    /**
     * @param telC the telC to set
     */
    public void setTelC(String telC) {
        this.telC = telC;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.ipp);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.ipp, other.ipp)) {
            return false;
        }
        return true;
    }
   
}
