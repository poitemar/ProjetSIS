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
import java.util.Date;

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

    public void ajouterSejour(String idSejour, Patient patient, PH phReferant, Localisation localisation, String prescription, String observation, String compteRendu, String resultat, String titreOperation, String detailsOperation, String lettreDeSortie) {
        Sejour s = new Sejour(idSejour, patient, phReferant, localisation, prescription, observation, compteRendu, resultat, titreOperation, detailsOperation, lettreDeSortie);        
        s.setIdSejour(idSejour);
        s.setPatient(patient);
        s.setPhReferant(phReferant);
        s.setLocalisation(localisation);

        String sql = "insert into ph(ID_PH,IPP_PATIENT,ID_SEJOUR,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,OPERATION,TITRE_OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, phReferant.getIdMed());
            pstm.setString(2, patient.getIpp());
            pstm.setString(3, idSejour);
            pstm.setString(4, observation);
            pstm.setString(5, resultat);
            pstm.setString(6, lettreDeSortie);
            pstm.setString(7, prescription);
            pstm.setString(8, detailsOperation);
            pstm.setString(9, titreOperation);
            pstm.setString(10, compteRendu);
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
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

    public String[] afficherListePatients() {
        int compteur = nombrePatients();
        int i=0;
        String[] listePatients = new String[compteur];
        try {
            String query = "select * from PATIENTS join PH on (IPP=IPP_PATIENT) join PERSONNEL_MEDICAL on (ID_PH = ID_P) join LOCALISATION using (ID_SEJOUR) where LOCALISATION.service = '"+getSpecialite()+"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePatients[i]=nomEntier;
                System.out.println(listePatients[i]);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return listePatients;
    }
    
    public int nombrePH() {
        int compteur = 0;
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P = 'DOCTEUR' and SPE = 'ONCOLOGIE'"; // la query à entrer pour accéder aux données de nos tables 
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
    
    public String[] afficherListePH() {
        int compteur = nombrePH()+1;
        int i=1;
        String[] listePH = new String[compteur];
        listePH[0]="test";
        try {
            String query = "select * from PERSONNEL_MEDICAL where TYPE_P = 'DOCTEUR' and SPE = 'ONCOLOGIE'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                String prenom = rs.getString("PRENOM");
                String nomEntier = nom + " " + prenom;
                listePH[i]=nomEntier;
                System.out.println(listePH[i]);
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        return listePH;
    }
}
