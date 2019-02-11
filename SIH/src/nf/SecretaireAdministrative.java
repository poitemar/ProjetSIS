/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class SecretaireAdministrative extends PersonnelMedical{
     private Connection con;
      private Statement st; 
     
    public SecretaireAdministrative(String nom, String prenom, String idMed,String password) {
        super(nom, prenom, idMed,password);
         try{
            Class.forName("com.mysql.jdbc.Driver");
            
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/bd","root",""); // chacun à un localHost different à voir pour chacun, 
            st = con.createStatement();
            
        }catch(Exception ex) {
            System.out.println("error :" +  ex );
            
        }
    }
    

    
    }

