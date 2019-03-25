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

/**
 *
 * @author racam
 */
public class Archivage {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    /**
     *
     */
    public Archivage() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

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
     * @param causeDeces
     * @param dateDeces
     */
    //  public void archiverPatient(String nom,String prenom,String dateNaissance,String causeDeces){
    public void archiverPatient(String ipp, String nom, String prenom, String dateNaissance, String dateDeces, String causeDeces) {
        /**
         * prend en entrée le nom, le prénom, la date de Naissance et la cause
         * du décès d'un patient et l'ajoute dans la table "patient_aarchive" de
         * la base de données, en le supprimant de la table "patients"
         */
       
        String sql = "insert into patient_archive(IPP,NOM_ARCHIVE,PRENOM_ARCHIVE,DATENAISSANCE_ARCHIVE,DATE_DECES,CAUSE_DECES) values (?,?,?,?,?,?)";
      
        try {
            PreparedStatement pstm = con.prepareStatement(sql); //c'est ici qu'on ajoute le patient dans la table "patients_decedes"

            pstm.setString(1, ipp);
            pstm.setString(2, nom);
            pstm.setString(3, prenom);
            pstm.setString(4, dateNaissance);
            pstm.setString(5, dateDeces);
            pstm.setString(6, causeDeces);
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        
        
        
        try {
            String requete2 = "delete from patients where IPP='" + ipp +"'";
          
            System.out.println("requete delete from patient " + requete2);
            st.executeUpdate(requete2); //on supprime le patient de la table "patients" de la bd
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        
        

        try {
            String requete = "INSERT archive SELECT * FROM ph where IPP_PATIENT='" + ipp + "'";
            st.executeUpdate(requete); //on supprime le lien entre le ph référent et le patient dans la table "ph_referent"

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
             
        
        
        try {
            String requete = "delete from ph where IPP_PATIENT='" + ipp + "'";
            st.executeUpdate(requete); //on supprime le séjour du patient dans la tale "localisation"
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    /**
     *
     * @param ipp
     * @return
     */
    public String infoMort(String ipp){
        String info="";
         try {
            String query = "select * from patient_archive where IPP='"+ipp+"'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String cause = rs.getString("CAUSE_DECES");
                
                String dateD = rs.getString("DATE_DECES");
               info = "Décédé le : "+ dateD +". Cause du décès : "+cause+".";
                
    }}
     catch (Exception ex) {
            System.out.println(ex);

        }
    return info;}
    
    /**
     *
     * @return
     */
    public ArrayList<Patient> afficherListePatientMort() {
        ArrayList<Patient> listePatient = new ArrayList<Patient>();

        try {
            String query = "select * from patient_archive"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String ipp = rs.getString("IPP");
                
                String nom = rs.getString("NOM_ARCHIVE");
                System.out.println(nom);
                String prenom = rs.getString("PRENOM_ARCHIVE");
                System.out.println(prenom);
                String date = rs.getString("DATENAISSANCE_ARCHIVE");
                System.out.println(prenom);
                Patient patient = new Patient(ipp, nom, prenom,date,Sexe.AUTRE);

                listePatient.add(patient);
              

                int i = 0;
                Boolean rep = false;
                while (i < listePatient.size() && rep == false) {
                    if (listePatient.get(i).getipp().equals(patient.getipp())) {
                        rep = true;
                        i++;
                    } else {
                        i++;
                    }
                }
                if (rep == false) {
                    listePatient.add(patient);
                }

            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return listePatient;

    }
}
