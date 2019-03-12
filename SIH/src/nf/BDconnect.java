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

/**
 *
 * @author PC
 */
public class BDconnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public BDconnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

          //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
           con = DriverManager.getConnection("jdbc:mysql://mysql-lauren.alwaysdata.net:3306/lauren_bd", "lauren", "70228679"); 
          st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }

    }

//Exemple d’ajout de données 
    public void ajouterPatient() {

        String sql = "insert into patients(IPP,NOM,PRENOM,SEXE,DATENAISSANCE,ADRESSE,TELEPHONE) values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, "654389");
            pstm.setString(2, "Abichacra");
            pstm.setString(3, "Lauren");
            pstm.setString(4, "FEMME");
            pstm.setDate(5, new Date(11, 8, 2012));
            pstm.setString(6, "44 rue polytech");
            pstm.setString(7, "0123456789");
            pstm.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    public void ajouterSejour() {

        String sql = "insert into ph(ID_PH,IPP_PATIENT,ID_SEJOUR,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,OPERATION,TITRE_OPERATION,COMPTE_RENDU,) values (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, "12345689");
            pstm.setString(2, "987654321");
            pstm.setString(3, "1597536248");            
            pstm.setString(4, "une observation");
            pstm.setString(5, "résultat pour un séjour");
            pstm.setString(6, "le patient sort aujd");
            pstm.setString(7, "un médicament");
            pstm.setString(8, "opération compliquée");
            pstm.setString(9, "opération du genou");
            pstm.setString(10, "succes");
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

//code d’extraction de données 
    public void getData2() {
        try {
            String query = "select * from PH "; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            System.out.println("contenue de la base de donnée");
            while (rs.next()) {

                // exemple d'accès aux mots de passe et le log in de la connexion 
//                String ipp = rs.getString("IPP");
//                  String nom= rs.getString("nom");// pour avoir accès a la colonne de ma table 
//                  String prenom = rs.getString("prenom");
//                  String sexe= rs.getString("sexe");
//                  String datenaissance = rs.getString("datenaissance");
//                  String adresse = rs.getString("adresse");
//                  String role = rs.getString("telephone");
//                  System.out.println( "ipp : " + ipp + "nom :" + nom + "prenom :" + prenom + "sexe :" + sexe);
              String prescr = rs.getString("PRESCRIPTION");
//            
//                System.out.println(ipp);
                System.out.println(prescr);

            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace(); 
        }

    }

}
