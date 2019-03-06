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
    public static ArrayList<String> listePatientsUrgence= new ArrayList<>();;

    public SecretaireMedicaleUrgence(String idMed, String nom, String prenom, String login, String password, Specialite spe, Service service) {
        super(idMed, nom, prenom, login, password, spe, service);
        service = service.URGENCE;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();
            st1 = con.createStatement();
            st2 = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

    public void ajouterPatient(String ipp,String nom, String prenom, String dateNaissance) {
        String sql = "insert into PATIENTS_TEMP(IPP_TEMP,NOM,PRENOM,DATE_NAISSANCE,DATE_ENTREE) values (?,?,?,?,?)";
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
        

    }

    public int nombrePatientsActuels() {
        int compteur = 0;
        try {
            String query = "select * from PATIENTS_TEMP"; // la query à entrer pour accéder aux données de nos tables 
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

    public String[] afficherListePatientsActuels() {
        int compteur = nombrePatientsActuels();
        int i = 0;
        String[] listePatientsActuels = new String[compteur];
        try {
            String query = "select * from PATIENTS_TEMP"; // la query à entrer pour accéder aux données de nos tables 
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

    public int nombrePatients() {
        return listePatientsUrgence.size();
    }
    
    public void MajListe(String nomEntier){
        String[] liste = new String[listePatientsUrgence.size()];
        for (int i = 0; i < listePatientsUrgence.size(); i++) {
            liste[i] = (String) listePatientsUrgence.get(i);
            System.out.println(liste[i]);
        }
    }

    public String[] afficherListePatients() {
        System.out.println(listePatientsUrgence.size());
        String[] liste = new String[listePatientsUrgence.size()];
        for (int i = 0; i < listePatientsUrgence.size(); i++) {
            liste[i] = (String) listePatientsUrgence.get(i);
            System.out.println(liste[i]);
        }
        return liste;
    }

    public void affecterPatient(String IPP_temp, Sexe sexe, String adresse, String numTel) {
        String IPP;
        String nom;
        String prenom;
        String dateNaissance;
        boolean estDedans = false;
        try {
            String query = "select * from PATIENTS"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                IPP = rs.getString("IPP");
                System.out.println("IPP =" + IPP);
                System.out.println("IPP TEMP =" + IPP_temp);
                if (IPP == IPP_temp) {
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
                String requete = "select * from PATIENTS_TEMP where IPP_TEMP=" + IPP_temp;
                rs1 = st1.executeQuery(requete);
                while (rs1.next()) {
                    nom = rs1.getString("NOM");
                    prenom = rs1.getString("PRENOM");
                    dateNaissance = rs1.getString("DATE_NAISSANCE");
                    ajouterNouveauPatient(IPP_temp, nom, prenom, sexe, dateNaissance, adresse, numTel);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
            try {
                String requete2 = "delete from PATIENTS_TEMP where IPP_TEMP=" + IPP_temp;
                st2.executeUpdate(requete2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        

    }
    
    public void ajouterNouveauPatient(String ipp, String nom, String prenom, Sexe sexe, String dateDeNaissance, String adresse, String telephone){
        
        Patient p = new Patient(ipp,nom,prenom,sexe,dateDeNaissance,adresse,telephone);
        DMA dma = new DMA(p);
        DM dm = new DM(p);
        
              String sql="insert into patients(IPP,NOM,PRENOM,SEXE,DATENAISSANCE,ADRESSE,TELEPHONE) values (?,?,?,?,?,?,?)";
           
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
        
            pstm.setString(1,ipp);
            pstm.setString(2,nom);
            pstm.setString(3,prenom);
            pstm.setString(4,sexe.toString());            
            pstm.setString(5, dateDeNaissance);
            pstm.setString(6, adresse);
            pstm.setString(7, telephone);
            pstm.executeUpdate();
            
            
           }
           catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public String getIPP_temp(String nom,String prenom,String dateNaissance){
        String resultat="";
        try {
            String query = "select * from PATIENTS_TEMP where NOM='"+nom+"' and PRENOM='"+prenom+"' and DATE_NAISSANCE='"+dateNaissance+"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                resultat = rs.getString("IPP_TEMP");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        return resultat;
    }
    
    public String getDateNaissance(String nom, String prenom){
        String resultat="";
        try {
            String query = "select * from PATIENTS_TEMP where NOM='"+nom+"' and PRENOM='"+prenom+"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                resultat = rs.getString("DATE_NAISSANCE");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        return resultat;
    }
}
