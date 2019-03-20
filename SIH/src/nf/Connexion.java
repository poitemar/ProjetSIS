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

    /**
     *
     */
    public Connexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
                   st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);

        }
    }

    /**
     *
     * @param idLogin
     * @param passwordLogin
     * @return
     */
    public String seConnecter(String idLogin, String passwordLogin) {
        /** on prend en entrée le login et le mot de passe de l'utilisateur qui se connecte,
         on compare les valeurs d'entrées avec celles de la base de données, et si elles concordent,
         le type de personnel médical (docteur, secrétaire médicale ...) est renvoyé*/
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
                    System.out.println("login bon"); // si le login correspond ...
                    if (passwordLogin.equals(listPassword.get(i))) {
                        System.out.println("password bon"); // ... et le mot de passe aussi ...
                        System.out.println("c'est un(e)" + listTypeMed.get(i)); // ... c'est bon, et on
                        //renvoie le type du personnel médical aussi
                        return listTypeMed.get(i);
                    }
                } 
                 
            }
               
         
        } catch (Exception ex) {
            
               System.out.println(ex);
            return "ERREUR"; //si ça ne fonctionne pas, on renvoie "erreur"
         
        }
        
        return "";
        
    }

    /**
     *
     * @param id
     * @return
     */
    public String nomPersonnel(String id) {
         /** on prend en entrée l'id du personnel pour le comparer à ceux dans la table "personnel_medical"
           dans la base de données, et on renvoie le nom associé à l'id */
        try {
            String nomQuery = "select * from personnel_medical where ID_P='" + id + "'"; // la query à entrer pour accéder aux données de nos tables 
            st = con.createStatement();
            rs = st.executeQuery(nomQuery);
            while (rs.next()) {
                String nom = rs.getString("nom");
                return nom;
            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }
     
    /**
     *
     * @param id
     * @return
     */
    public String prenomPersonnel(String id) {
         /** on prend en entrée l'id du personnel pour le comparer à ceux dans la table "personnel_medical"
           dans la base de données, et on renvoie le prénom associé à l'id */
        try {
            String nomQuery = "select * from personnel_medical where ID_P='" + id + "'"; // la query à entrer pour accéder aux données de nos tables 
            st = con.createStatement();
            rs = st.executeQuery(nomQuery);
            System.out.println(nomQuery);
            while (rs.next()) {
                String prenom = rs.getString("prenom");
                return prenom;
            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
    }
 
    /**
     *
     * @param id
     * @return
     */
    public Service ServicePersonnel(String id) {
    /** on prend en entrée l'id du personnel pour le comparer à ceux dans la table "personnel_medical"
           dans la base de données, et on renvoie le service associé à l'id */
    try {
            String nomQuery = "select * from personnel_medical where ID_P='" + id + "'"; // la query à entrer pour accéder aux données de nos tables 
            st = con.createStatement();
            rs = st.executeQuery(nomQuery);
            System.out.println(nomQuery);
            while (rs.next()) {
                Service service = (Service) Enum.valueOf(Service.class, rs.getString("SERVICE")); //on cast en "service" le service qu'on récupère dans la bd sous la forme "String"
                return service;
            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return Service.ACCUEIL;
    }
    
    /**
     *
     * @param login
     * @param mdp
     * @return
     */

    public String choixPersonnel(String login, String mdp) {
        /** on prend en entrée le login et le mdp du personnel pour le comparer à ceux dans la table "personnel_medical"
           dans la base de données, et on renvoie l'id associé au login et au mdp */
        List<String> listLogin = new ArrayList<String>();
        List<String> listPassword = new ArrayList<String>();
        List<String> listId = new ArrayList<String>();
        try {
            String loginQuery = "select * from personnel_medical"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(loginQuery);
            System.out.println("contenu table personnel_medical");
            while (rs.next()) {

                // exemple d'accès aux mots de passe et le log in de la connexion 
                String log = rs.getString("LOGIN");
                listLogin.add(log);
                String password = rs.getString("MDP");
                listPassword.add(password);
                String id = rs.getString("ID_P");
                listId.add(id);

            }
            for (int i = 0; i < listLogin.size(); i++) {

                if (login.equals(listLogin.get(i))) { //si le login est bon ...
                    System.out.println("login bon");
                    if (mdp.equals(listPassword.get(i))) { //et que le mdp aussi ../
                        System.out.println("password bon");

                        return listId.get(i); // on renvoie l'id de la personne connectée
                    }
                }

            }
            System.out.println("Identifiant ou mot de passe incorrect");
            return "Identifiant ou mot de passe incorrect"; //sinon, on empêche la connexion

        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Erreur de connexion");
        return "Erreur de connexion";

    }

    /**
     *
     * @param id
     * @return
     */
    public Specialite spePersonnel(String id) {
        /** on prend en entrée l'id du personnel pour le comparer à ceux dans la table "personnel_medical"
           dans la base de données, et on renvoie la spécialité associé à l'id */
        try {
            String nomQuery = "select * from personnel_medical where ID_P='" + id + "'"; // la query à entrer pour accéder aux données de nos tables 
            st = con.createStatement();
            rs = st.executeQuery(nomQuery);
            System.out.println(nomQuery);
            while (rs.next()) {
               
                Specialite spe = (Specialite) Enum.valueOf(Specialite.class, rs.getString("SPE")); // on cast en type "Specialite" la spécialité récupérée dans la bd sous la forme "String"
                System.out.println(spe.toString());
                return spe;
            }

            rs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return Specialite.ACCUEIL;
    }
}
