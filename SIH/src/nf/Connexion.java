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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class Connexion {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public Connexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", ""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);

        }
    }

    public String seConnecter(String idLogin, String passwordLogin) {
        List<String> listLogin = new ArrayList<String>();
        List<String> listPassword = new ArrayList<String>();
        List<String> listTypeMed = new ArrayList<String>();
        try {
            String loginQuery = "select * from personnel_medical"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(loginQuery);
            System.out.println("contenu table personnel_medical");
            while (rs.next()) {

                // exemple d'accès aux mots de passe et le log in de la connexion 
                String login = rs.getString("LOGIN");
                listLogin.add(login);
                String password = rs.getString("MDP");
                listPassword.add(password);
                String typeMed = rs.getString("TYPE_P");
                listTypeMed.add(typeMed);
                  
            }
            for (int i = 0; i < listLogin.size(); i++) {
               
                if (idLogin.equals(listLogin.get(i))) {
                    System.out.println("login bon");
                    if (passwordLogin.equals(listPassword.get(i))) {
                        System.out.println("password bon");
                        System.out.println("c'est un(e)" + listTypeMed.get(i));
                        return listTypeMed.get(i);
                    }
                } 
                 
            }
               System.out.println("Identifiant ou mot de passe incorrect");
            return "Identifiant ou mot de passe incorrect";
         
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Erreur de connexion");
        return "Erreur de connexion";
        
    }
}
