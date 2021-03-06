/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author racam
 */
public class SecretaireMedicaleUrgence extends PersonnelMedical {

    private Connection con;
    private Statement st;
    private Statement st1;
    private Statement st2;
    private ResultSet rs;
    private ResultSet rs1;
    private ResultSet rs2;

    /**
     *
     */
    public static ArrayList<String> listePatientsUrgence= new ArrayList<>();

    /**
     *
     * @param idMed
     * @param nom
     * @param prenom
     * @param login
     * @param password
     * @param spe
     * @param service
     */
    public SecretaireMedicaleUrgence(String idMed, String nom, String prenom, String login, String password, Specialite spe, Service service) {
        super(idMed, nom, prenom, login, password, spe, service);
        service = service.CLINIQUE;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
            st = con.createStatement();
            st1 = con.createStatement();
            st2 = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    /**
     *
     * @param ipp
     * @param nom
     * @param prenom
     * @param dateNaissance
     */
    public void ajouterPatient(String ipp,String nom, String prenom, String dateNaissance) {
        /* On ajoute un patient dans la table de patients temporaires (en urgences) **/
        String sql = "insert into patients_temp(IPP_TEMP,NOM,PRENOM,DATE_NAISSANCE,DATE_ENTREE) values (?,?,?,?,?)";
        java.util.Date maDate;
            SimpleDateFormat maDateLongue;
            maDate= new java.util.Date();
             maDateLongue= new SimpleDateFormat("dd/MM/yyyy");
        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, ipp);
            pstm.setString(2, nom);
            pstm.setString(3, prenom);
            pstm.setString(4, dateNaissance);
            pstm.setString(5, maDateLongue.format(maDate));
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
       
                String nomEntier = nom + " " + prenom;
                listePatientsUrgence.add(nomEntier);

    }

    /**
     *
     * @return
     */
    public int nombrePatientsActuels() {
        /* On renvoie le nombre de patients actuellement aux urgences **/
        int compteur = 0;
        try {
            String query = "select * from patients_temp"; // la query à entrer pour accéder aux données de nos tables 
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

    /**
     *
     * @return
     */
    public String[] afficherListePatientsActuels() {
        /* On renvoie un tableau de String composé des noms et des prénoms concaténés des patients actuellement aux urgences  **/
        int compteur = nombrePatientsActuels();
        int i = 0;
        String[] listePatientsActuels = new String[compteur];
        try {
            String query = "select * from patients_temp"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePatientsActuels[i] = nomEntier;
                System.out.println(listePatientsActuels[i]);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return listePatientsActuels;
    }

    /**
     *
     * @return
     */
    public int nombrePatients() {
        return listePatientsUrgence.size();
    }
    
    /**
     *
     * @param nomEntier
     */
    public void MajListe(String nomEntier){
        String[] liste = new String[listePatientsUrgence.size()];
        for (int i = 0; i < listePatientsUrgence.size(); i++) {
            liste[i] = (String) listePatientsUrgence.get(i);
            System.out.println(liste[i]);
        }
    }

    /**
     *
     * @return
     */
    public String[] afficherListePatients() {
        System.out.println(listePatientsUrgence.size());
        String[] liste = new String[listePatientsUrgence.size()];
        for (int i = 0; i < listePatientsUrgence.size(); i++) {
            liste[i] = (String) listePatientsUrgence.get(i);
            System.out.println(liste[i]);
        }
        return liste;
    }

    /**
     *
     * @param IPP_temp
     * @param sexe
     * @param adresse
     * @param numTel
     * @param nomC
     * @param prenomC
     * @param adresseC
     * @param telC
     */
    public void affecterPatient(String IPP_temp, Sexe sexe, String adresse, String numTel, String nomC,String prenomC,String adresseC,String telC) {
        String IPP;
        String NOM="";
        String nom="";
        String PRENOM="";
        String prenom="";
        String DATENAISSANCE="";
        String dateNaissance="";
        boolean estDedans = false;
        try {
            String query = "select * from patients_temp where IPP_TEMP = '"+IPP_temp+"'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                NOM = rs.getString("NOM");
                PRENOM = rs.getString("PRENOM");
                DATENAISSANCE = rs.getString("DATE_NAISSANCE");
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        try {
            String query = "select * from patients"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                nom = rs.getString("NOM");
                prenom = rs.getString("PRENOM");
                dateNaissance = rs.getString("DATENAISSANCE");
                if (NOM.equals(nom) && PRENOM.equals(prenom) && DATENAISSANCE.equals(dateNaissance)) {
                    estDedans = true;
                    System.out.println("il est dedans !");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        if (estDedans == false) {
            System.out.println("il est pas dedans du coup");
            try {
                String requete = "select * from patients_temp where IPP_TEMP=" + IPP_temp;
                rs1 = st1.executeQuery(requete);
                while (rs1.next()) {
                    nom = rs1.getString("NOM");
                    prenom = rs1.getString("PRENOM");
                    dateNaissance = rs1.getString("DATE_NAISSANCE");
                    ajouterNouveauPatient(IPP_temp, nom, prenom, sexe, dateNaissance, adresse, numTel,nomC,prenomC,adresseC,telC);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
            try {
                String requete2 = "delete from patients_temp where IPP_TEMP=" + IPP_temp;
                st2.executeUpdate(requete2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        

    }
    
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
    public void ajouterNouveauPatient(String ipp, String nom, String prenom, Sexe sexe, String dateDeNaissance, String adresse, String telephone,String nomCONF,String prenomCONF, String adresseCONF,String telCONF){
        
        Patient p = new Patient(ipp,nom,prenom,sexe,dateDeNaissance,adresse,telephone,nomCONF,prenomCONF,adresseCONF,telCONF);
        DMA dma = new DMA(p);
        DM dm = new DM(p);
        
     String sql="insert into patients(IPP,NOM,PRENOM,SEXE,DATENAISSANCE,ADRESSE,TELEPHONE,NOMCONF,PRENOMCONF,ADRESSECONF,TELEPHONECONF) values (?,?,?,?,?,?,?,?,?,?,?)";
           
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
        
            pstm.setString(1,ipp);
            pstm.setString(2,nom);
            pstm.setString(3,prenom);
            pstm.setString(4,sexe.toString());            
            pstm.setString(5, dateDeNaissance);
            pstm.setString(6, adresse);
            pstm.setString(7, telephone);
            pstm.setString(8, nomCONF);
            pstm.setString(9, prenomCONF);
            pstm.setString(10, adresseCONF);
            pstm.setString(11, telCONF);
            pstm.executeUpdate(); 
            
            
           }
           catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    /**
     *
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @return
     */
    public String getIPP_temp(String nom,String prenom,String dateNaissance){
        String resultat="";
        String NOM="";
        String PRENOM="";
        String DATENAISSANCE="";
        boolean dedans=false;
        try {
            String query = "select * from patients where NOM='"+nom+"' and PRENOM='"+prenom+"' and DATENAISSANCE='"+dateNaissance+"'";
            rs = st.executeQuery(query);
            while (rs.next()){
                NOM = rs.getString("NOM");
                PRENOM = rs.getString("PRENOM");
                DATENAISSANCE = rs.getString("DATENAISSANCE");
                if (NOM == nom && PRENOM == prenom && DATENAISSANCE == dateNaissance){
                    dedans = true;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        if (dedans==false){
        try {
            String query = "select * from patients_temp where NOM='"+nom+"' and PRENOM='"+prenom+"' and DATE_NAISSANCE='"+dateNaissance+"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                resultat = rs.getString("IPP_TEMP");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        }
        
        else if (dedans==true){
            try {
            String query = "select * from patients where NOM='"+nom+"' and PRENOM='"+prenom+"' and DATENAISSANCE='"+dateNaissance+"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                resultat = rs.getString("IPP");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        }
        return resultat;
    }
    
    /**
     *
     * @param nom
     * @param prenom
     * @return
     */
    public String getDateNaissance(String nom, String prenom){
        String resultat="";
        try {
            String query = "select * from patients_temp where NOM='"+nom+"' and PRENOM='"+prenom+"'"; // la query à entrer pour accéder aux données de nos tables 
            rs2 = st.executeQuery(query);
            while (rs2.next()) {
                resultat = rs2.getString("DATE_NAISSANCE");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        return resultat;
    }
}
