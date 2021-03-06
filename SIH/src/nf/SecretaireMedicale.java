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
import java.util.Date;
import nf.Specialite;

/**
 *
 * @author PC
 */
public class SecretaireMedicale extends PersonnelMedical {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private Specialite specialite;
    
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
    public SecretaireMedicale(String idMed, String nom, String prenom, String login, String password, Specialite spe, Service service) {
        super(idMed, nom, prenom, login, password, spe, service);
        this.specialite = spe;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error :" + ex);
            ex.printStackTrace();

        }
    }

//On cree un nouveau sejour mais on ne le remplit pas, l'operation creer le sejour et le remplir ne se deroulent pas dans la meme methode

    /**
     *
     * @param idSejour
     * @param iPP
     * @param idPHReferent
     * @param localisation
     */
    public void ajouterSejour(String idSejour, String iPP, String idPHReferent, Localisation localisation) {
        /* On ajoute un nouveau séjour pour un patient à l'aide des paramètres en entrée **/
        Sejour s = new Sejour(idSejour, iPP, idPHReferent, localisation);
        String sql = "insert into ph_referent(ID_PHR,ID_SEJOUR,IPP,DATE_CREATION_SEJOUR) values (?,?,?,?)";
        String sql2 = "insert into localisation (ID_SEJOUR,SERVICE,ORIENTATION,ETAGE,CHAMBRE,LIT) values (?,?,?,?,?,?)";
        String sql3 = "insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);
            PreparedStatement pstm2 = con.prepareStatement(sql2);
            PreparedStatement pstm3 = con.prepareStatement(sql3);
            Date maDate = new Date();

            SimpleDateFormat maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            //on insere les donnees dans la classe ph_referent ce qui correspond a la requete 1
            pstm.setString(1, idPHReferent);
            pstm.setString(2, idSejour);
            pstm.setString(3, iPP);
            pstm.setString(4, maDateLongue.format(maDate));
            pstm.executeUpdate();

            //On insere les donnees dans le classe localisation ce qui correspond a la requete 2
            pstm2.setString(1, idSejour);
            pstm2.setString(2, localisation.getSpecialite().toString());

            pstm2.setString(3, localisation.getOrientation().toString());
            pstm2.setInt(4, localisation.getEtage());
            pstm2.setInt(5, localisation.getChambre());
            pstm2.setString(6, localisation.getLit().toString());

            pstm2.executeUpdate();
            //
            pstm3.setString(1, idPHReferent);

            pstm3.setString(2, iPP);

            pstm3.setString(3, idSejour);
            pstm3.setString(4, maDateLongue.format(maDate));

            pstm3.setString(5, "");
            pstm3.setString(6, "");
            pstm3.setString(7, "");
            pstm3.setString(8, "");
            pstm3.setString(9, "");
            pstm3.setString(10, "");
            pstm3.setString(11, "");
            pstm3.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);

            //AJOUTER LA LOCALISATION PAR DEFAUT
        }
    }

    //Retroune l'id du dernier sejour crée pour le patient

    /**
     *
     * @param iPP
     * @return
     */
    public String idSejourPatientSelection(String iPP) {
        /* On renvoie l'id du séjour d'un patient sélectionnée à l'aide son ipp en entrée **/

        String idSejour = "";
        String dateLaPlusRecente = "01/01/0001";
        String date = "";
        //On recherche le sejour le plus recent
        try {
            String query = "select DATE_CREATION_SEJOUR from ph_referent where IPP='" + iPP + "'";
            System.out.println(query);
            rs = st.executeQuery(query);

            while (rs.next()) {
                date = rs.getString("DATE_CREATION_SEJOUR");
                if (date.compareTo(dateLaPlusRecente) > 0) {
                    dateLaPlusRecente = date;

                }
                System.out.println(date);
                System.out.println(dateLaPlusRecente);
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        //On récupère l'id associé au sejour le plus ancien
        try {
            String query = "select ID_SEJOUR from ph_referent where IPP='" + iPP + "' and DATE_CREATION_SEJOUR='" + dateLaPlusRecente + "'";
            System.out.println(query);
            rs = st.executeQuery(query);

            while (rs.next()) {
                idSejour = rs.getString("ID_SEJOUR");

            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return idSejour;
    }

    //Méthode qui permet à la secrétaire médicale de mettre à jour la localisation d'un patient

    /**
     *
     * @param idSejour
     * @param loc
     */
    public void enregistrerLocalisation(String idSejour, Localisation loc) {
        /* On ajoute dans la bd les locations afin de garder un historique des localisations d'un patient **/
        String sql1 = "insert into historique_localisation (ID_SEJOUR,DATE,SERVICE,CODE_LOCALISATION) values (?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(sql1);

            Date maDate = new Date();
            SimpleDateFormat maDateLongue = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            //on insere les donnees dans la classe historique_localisation ce qui correspond a la requete 1
            pstm.setString(1, idSejour);
            pstm.setString(2, maDateLongue.format(maDate));
            pstm.setString(3, loc.getSpecialite().toString());
            pstm.setString(4, loc.codeLocalisation());
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     *
     * @param idSejour
     */
    public void supprimerLocalisation(String idSejour) {
        /* On ajoute dans la bd les locations afin de garder un historique des localisations d'un patient **/
        String sql1 = "delete from localisation where ID_SEJOUR='"+idSejour+"'";
        try {
            PreparedStatement pstm = con.prepareStatement(sql1);

           
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     *
     * @param idSejour
     * @param localisation
     */
    public void modifierLocalisation(String idSejour,Localisation localisation) {
        /* On update le localisation d'un patient à l'aide des paramètres en entrée **/
      
        String sql2 = "update localisation set SERVICE='" + localisation.getSpecialite()
                + "', ORIENTATION='" + localisation.getOrientation()
                + "', ETAGE='" +localisation.getEtage()
                + "', CHAMBRE='" + localisation.getChambre()
                + "', LIT='" + localisation.getLit()
                + "' where ID_SEJOUR='" + idSejour + "'";
        try {
            PreparedStatement pstm2 = con.prepareStatement(sql2);

            pstm2.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Fonction qui retourne l'idMed du medecin lu dans la liste

    /**
     *
     * @param lecture
     * @return
     */
    public String iPPMedecinListe(String lecture) {
        /* On renvoie l'ipp d'un médecin dans une lsite en fonction de ce qui est lu dans la liste **/
        String ipp = "";
        try {
            String nomLu = "";
            String prenomLu = "";
            String[] result = lecture.split("\\s\\s\\s\\s\\s\\s\\s\\s\\s");

            for (int x = 0; x < result.length; x++) {
                nomLu = result[0];
                prenomLu = result[1];

            }
            String query = "select * from personnel_medical where NOM='" + nomLu + "' and PRENOM='" + prenomLu + "'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getString("ID_P");
                System.out.println(ipp);

            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        return ipp;
    }

    /**
     *
     * @return
     */
    public String creationID_Sejour() {
        /*création séjour : sous le format YYMMxxxxx où YY est l’année de la consultation ou de
        l’hospitalisation sur deux positions, MM le mois et xxxxx un compteur aléatoire sur cinq positions.*/
        Date maDate;
        SimpleDateFormat maDateLongue;
        maDate = new Date();
        maDateLongue = new SimpleDateFormat("yyMM");
        //System.out.println("Date :"+ maDateLongue.format(maDate));

        int num5 = (int) Math.round(Math.random() * 10);
        int num6 = (int) Math.round(Math.random() * 10);
        int num7 = (int) Math.round(Math.random() * 10);
        int num8 = (int) Math.round(Math.random() * 10);
        int num9 = (int) (Math.random() * 10);

        String ID_Sejour = "" + maDateLongue.format(maDate) + Integer.valueOf(num5) + Integer.valueOf(num6) + Integer.valueOf(num7) + Integer.valueOf(num8) + Integer.valueOf(num9);
        return ID_Sejour;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    /**
     *
     * @return
     */
    public int nombrePatients() {
        /* renvoie le nombre de patients de la spécialité de la secrétaire médicale **/
        int compteur = 0;
        try {
            String query = "select * from patients join PH on (IPP=IPP_PATIENT) join personnel_medical on (ID_PH = ID_P) join localisation using (ID_SEJOUR) where LOCALISATION.service = 'Oncologie'"; // la query à entrer pour accéder aux données de nos tables 
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
     * @param spe
     * @return
     */
    public ArrayList<Patient> afficherListePatientParService(Specialite spe) {
        /* renvoie une liste de patients du service de la secrétaire médicale associée **/
        ArrayList<Patient> listePatient = new ArrayList<Patient>();

        try {
            String query = "select * from patients join ph_referent on (patients.IPP=ph_referent.IPP) join localisation using (ID_SEJOUR) where SERVICE ='" + spe + "' order by NOM"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                System.out.println(prenom);
                String date = rs.getString("DATENAISSANCE");
                //System.out.println(date);
                String idp = rs.getString("IPP");
                //System.out.println(idPatient);

                Sexe sexeLu = (Sexe) Enum.valueOf(Sexe.class, rs.getString("SEXE"));
                String adresse = rs.getString("ADRESSE");
                // System.out.println(Adresse);
                String tel = rs.getString("TELEPHONE");
                //System.out.println(tel);                 
                String nomC = rs.getString("NOMCONF");
                String prenomC = rs.getString("PRENOMCONF");
                String adresseC = rs.getString("ADRESSECONF");
                String telC = rs.getString("TELEPHONECONF");
                Patient patient = new Patient(idp, nom, prenom, sexeLu, date, adresse, tel, nomC, prenomC, adresseC, telC);

                int i = 0;
                Boolean rep = false;
                while (i < listePatient.size() && rep == false) {
                    if (listePatient.get(i).getIpp().equals(patient.getIpp())) {
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

    /**
     *
     * @param iPP
     * @return
     */
    public Patient recuperationPatient(String iPP) {
        /* On renvoie le patient associé à un IPP en entrée **/
        Patient patient = new Patient("","");
        try {
            String query = "select * from patients where IPP ='" + iPP + "'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                System.out.println(prenom);
                String date = rs.getString("DATENAISSANCE");
                //System.out.println(date);
                String idp = rs.getString("IPP");
                //System.out.println(idPatient);

                Sexe sexeLu = (Sexe) Enum.valueOf(Sexe.class, rs.getString("SEXE"));
                String adresse = rs.getString("ADRESSE");
                // System.out.println(Adresse);
                String tel = rs.getString("TELEPHONE");
                //System.out.println(tel);                 
                String nomC = rs.getString("NOMCONF");
                String prenomC = rs.getString("PRENOMCONF");
                String adresseC = rs.getString("ADRESSECONF");
                String telC = rs.getString("TELEPHONECONF");
                 patient = new Patient(idp, nom, prenom, sexeLu, date, adresse, tel, nomC, prenomC, adresseC, telC);
            }
        } catch (Exception ex) {
            System.out.println(ex);

        }
        return patient;
    }

    /**
     *
     * @param spe
     * @return
     */
    public ArrayList<PH> afficherListeMedecinParService(Specialite spe) {
        /* On renvoie une liste de ph de la spécialité de la secrétaire médicale **/
        ArrayList<PH> listeMed = new ArrayList<PH>();

        try {
            String query = "select * from personnel_medical where TYPE_P='DOCTEUR' and SPE='" + spe + "'"; // la query à entrer pour accéder aux données de nos tables 
            System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next()) {
                String nom = rs.getString("NOM");
                // System.out.println(nom);
                String prenom = rs.getString("PRENOM");
                //  System.out.println(prenom);
                String idMed = rs.getString("ID_P");
                //System.out.println(idMed);
                String login = rs.getString("LOGIN");
                // System.out.println(login);
                String mdp = rs.getString("MDP");
                //System.out.println(mdp);

                Specialite speLue = (Specialite) Enum.valueOf(Specialite.class,
                         rs.getString("SPE"));
                // System.out.println(speLue);
                Service servicelu = (Service) Enum.valueOf(Service.class,
                         rs.getString("SERVICE"));
                //   System.out.println(servicelu);
                PH docteur = new PH(idMed, nom, prenom, login, mdp, speLue, servicelu);
                //   System.out.println(docteur.getNom());
                //System.out.println(docteur.getSpecialite().toString());
                listeMed.add(docteur);

            }
        } catch (Exception ex) {
            System.out.println(ex);

        }

        return listeMed;

    }

    /**
     *
     * @param ipp
     * @param nom
     * @param prenom
     * @param sexe
     * @param dateNaissance
     * @param adresse
     * @param telephone
     * @param nomCONF
     * @param prenomCONF
     * @param adresseCONF
     * @param telCONF
     */
    public void modifierPatient(String ipp, String nom, String prenom, Sexe sexe, String dateNaissance, String adresse, String telephone, String nomCONF, String prenomCONF, String adresseCONF, String telCONF) {
       /* On modifie dans la bd les données d'un patient en fonction des données en entrée **/
        Patient patient = new Patient(ipp, nom, prenom, sexe, dateNaissance, adresse, telephone, nomCONF, prenomCONF, adresseCONF, telCONF);
       
        String sql = "update patients set NOM='" + nom
                + "', PRENOM='" + prenom
                + "', SEXE='" + sexe
                + "', DATENAISSANCE='" + dateNaissance
                + "', ADRESSE='" + adresse
                + "', TELEPHONE='" + telephone
                + "', NOMCONF='" + nomCONF
                + "', PRENOMCONF='" + prenomCONF
                + "', ADRESSECONF='" + adresseCONF
                + "', TELEPHONECONF='" + telCONF
                + "' where IPP='" + ipp + "'";
         System.out.println(sql);
        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    
    // Fonction qui permet de chercher si un patient existe dans la base de données et d'en donner l'identifiant si c'est le cas

    /**
     *
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @return
     */
     public String identitePatient(String nom,String prenom, String dateNaissance){
        String ipp="";
        Boolean rep =false;
         try {
            String query = "select * from patients where NOM='"+nom+"' and PRENOM='"+prenom+"' and DATENAISSANCE='"+dateNaissance+"'"; // la query à entrer pour accéder aux données de nos tables 
            rs = st.executeQuery(query);
            while (rs.next()) {
                rep=true;
                 ipp = rs.getString("IPP");
   
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
         if (rep==false){
             ipp="Nouveau patient";         }
         
         return ipp;
    }
}
