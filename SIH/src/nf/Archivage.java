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
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @param causeDeces
     */
    public void ajouterPatient(String nom,String prenom,String dateNaissance,String causeDeces){
        /** prend en entrée le nom, le prénom, la date de Naissance et la cause du décès d'un patient
         et l'ajoute dans la table "patients_decedes" de la base de données, en le supprimant de la table
         "patients" */
        String idPHR="";
        String specialite="";
        String idSejour="";
        String ipp="";
        java.util.Date maDate;
        SimpleDateFormat maDateLongue;
        maDateLongue= new SimpleDateFormat("dd/MM/yyyy");
        maDate= new java.util.Date();
        try {
            String query = "select * from PATIENTS natural join PH_REFERENT natural join LOCALISATION"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query); // on va récupérer l'id du ph référent, sa spécialité, l'id du séjour et l'ipp du patient dans la base de données
            while (rs.next()) {
              idPHR = rs.getString("ID_PHR");
              specialite=rs.getString("SERVICE");
              idSejour=rs.getString("ID_SEJOUR");
              ipp=rs.getString("IPP");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace(); 
        }
        
        String sql = "insert into PATIENTS_DECEDES(IPP,NOM,PRENOM,DATE_NAISSANCE,ID_PHR,SPECIALITE,DATE_DECES,CAUSE_DECES) values (?,?,?,?,?,?,?,?)";
        System.out.println("Nom : "+nom);
        System.out.println("Prenom : "+prenom);
        System.out.println("Date naissance : "+dateNaissance);
        System.out.println("idPHR : "+idPHR);
        System.out.println("Spe : "+specialite);
        System.out.println("Date dc : "+maDateLongue.format(maDate));
        System.out.println("Cause dc : "+causeDeces);
        try {
            PreparedStatement pstm = con.prepareStatement(sql); //c'est ici qu'on ajoute le patient dans la table "patients_decedes"
            
            pstm.setString(1, ipp);    
            pstm.setString(2, nom);
            pstm.setString(3, prenom);
            pstm.setString(4, dateNaissance);            
            pstm.setString(5, idPHR);
            pstm.setString(6, specialite);
            pstm.setString(7, maDateLongue.format(maDate));
            pstm.setString(8, causeDeces);
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }       
        try {
                String requete2 = "delete from PATIENTS where NOM='" + nom+"' and PRENOM ='"+prenom+"' and DATENAISSANCE='"+dateNaissance+"'";
                st.executeUpdate(requete2); //on supprime le patient de la table "patients" de la bd
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        try {
                String requete = "delete from PH_REFERENT where ID_PHR='"+ idPHR+"'";
                 st.executeUpdate(requete); //on supprime le lien entre le ph référent et le patient dans la table "ph_referent"
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
        try {
                String requete = "delete from LOCALISATION where ID_SEJOUR='"+ idSejour+"'";
                 st.executeUpdate(requete); //on supprime le séjour du patient dans la tale "localisation"
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
        
    }
}
