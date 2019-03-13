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
    
    public void ajouterPatient(String nom,String prenom,String dateNaissance,String causeDeces){
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
            rs = st.executeQuery(query);
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
            PreparedStatement pstm = con.prepareStatement(sql);
            
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
                st.executeUpdate(requete2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        try {
                String requete = "delete from PH_REFERENT where ID_PHR='"+ idPHR+"'";
                 st.executeUpdate(requete);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
        try {
                String requete = "delete from LOCALISATION where ID_SEJOUR='"+ idSejour+"'";
                 st.executeUpdate(requete);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
        
    }
}
