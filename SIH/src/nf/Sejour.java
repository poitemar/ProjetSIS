/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author poite
 */
public class Sejour {
    private String idSejour;
    private Patient patient;
    private PH phReferant;
    private List<String> listeObservations;
    private ArrayList<String> listePrescriptions;
    private List<String> listeTitreOperations;
    private List<String> listeDetailsOperations;
    private List<String> listeDeResultats;
    private List<String> listeDeCompteRenduRadio;
    private Date dateEntree;
    private Date dateSortie;
    private String lettreDeSortie;
    private Localisation localisation;
    private String observation; 
    Connection con; 
    ResultSet rs;
    Statement st;
    String IPP; 
    String phRef;
   //  Constructeur Sejour
    // creation sejour implique un patient, un idSejour(auto), un phReferant, localisation
    public Sejour(String idSejour, Patient patient, PH phReferant, Localisation localisation) {
        this.idSejour = idSejour;
        this.patient = patient;
        this.phReferant = phReferant;
        this.localisation = localisation;
    }
    
       public Sejour(String idSejour, String IPP, String phRef, Localisation localisation) {
        this.idSejour = idSejour;
        this.IPP=IPP; 
        this.phRef=phRef;
        this.localisation = localisation;
    }
//
//    /*deuxième constructeur de Sejour pour rentrer les informations d'un séjour : prescriptions, observations, compte rendu,
//     résultats, titre des opérations, détails des opérations, et une lettre de sortie marquant la fin du séjour */
//    public Sejour(String prescription, String observation, String compteRendu, String resultat, String titreOperation,
//            String detailsOperation, String lettreDeSortie) {
//        this.listePrescriptions.add(prescription);
//        this.listeObservations.add(observation);
//        this.listeDeCompteRenduRadio.add(compteRendu);
//        this.listeDeResultats.add(resultat);
//        this.listeTitreOperations.add(titreOperation);
//        this.listeDetailsOperations.add(detailsOperation);
//        this.lettreDeSortie = lettreDeSortie;
//    }

    //constructeru ajouter pour test d'affichage du sejour, à effacer une fois fini 11
    public Sejour( String obs){
        this.observation=obs;   
    }
    
    
 
    
    
    public Sejour(String idSejour, Patient patient, PH phReferant, Localisation localisation, String prescription, String observation, String compteRendu, String resultat, String titreOperation,String detailsOperation, String lettreDeSortie) {
        this.idSejour = idSejour;
        this.patient = patient;
        this.phReferant = phReferant;
        this.localisation = localisation;
        this.listePrescriptions.add(prescription);
        this.listeObservations.add(observation);
        this.listeDeCompteRenduRadio.add(compteRendu);
        this.listeDeResultats.add(resultat);
        this.listeTitreOperations.add(titreOperation);
        this.listeDetailsOperations.add(detailsOperation);
        this.lettreDeSortie = lettreDeSortie;
    }

    public Sejour(){
        
        
    }
    
    // getters et setters
    /**
     * @return the idSejour
     */
    public String getIdSejour() {
        return idSejour;
    }

    /**
     * @param idSejour the idSejour to set
     */
    public void setIdSejour(String idSejour) {
        this.idSejour = idSejour;
    }

    /**
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * @param patient the patient to set
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * @return the phReferant
     */
    public PH getPhReferant() {
        return phReferant;
    }

    /**
     * @param phReferant the phReferant to set
     */
    public void setPhReferant(PH phReferant) {
        this.phReferant = phReferant;
    }

    /**
     * @return the listeObservations
     */
    public List<String> getListeObservations() {
        return listeObservations;
    }

    /**
     * @param listeObservations the listeObservations to set
     */
    public void setListeObservations(List<String> listeObservations) {
        this.listeObservations = listeObservations;
    }

    /**
     * @return the listePrescriptions
     */
    public ArrayList<String> getListePrescriptions() {
        return listePrescriptions;
    }

    /**
     * @param listePrescriptions the listePrescriptions to set
     */
    public void setListePrescriptions(ArrayList<String> listePrescriptions) {
        this.listePrescriptions = listePrescriptions;
    }

    /**
     * @return the listeTitreOperations
     */
    public List<String> getListeTitreOperations() {
        return listeTitreOperations;
    }

    /**
     * @param listeTitreOperations the listeTitreOperations to set
     */
    public void setListeTitreOperations(List<String> listeTitreOperations) {
        this.listeTitreOperations = listeTitreOperations;
    }
  
    /**
     * @return the listeDetailsOperations
     */
    public List<String> getListeDetailsOperations() {
        return listeDetailsOperations;
    }

    /**
     * @param listeDetailsOperations the listeDetailsOperations to set
     */
    public void setListeDetailsOperations(List<String> listeDetailsOperations) {
        this.listeDetailsOperations = listeDetailsOperations;
    }

    /**
     * @return the listeDeResultats
     */
    public List<String> getListeDeResultats() {
        return listeDeResultats;
    }

    /**
     * @param listeDeResultats the listeDeResultats to set
     */
    public void setListeDeResultats(List<String> listeDeResultats) {
        this.listeDeResultats = listeDeResultats;
    }

    /**
     * @return the listeDeCompteRenduRadio
     */
    public List<String> getListeDeCompteRenduRadio() {
        return listeDeCompteRenduRadio;
    }

    /**
     * @param listeDeCompteRenduRadio the listeDeCompteRenduRadio to set
     */
    public void setListeDeCompteRenduRadio(List<String> listeDeCompteRenduRadio) {
        this.listeDeCompteRenduRadio = listeDeCompteRenduRadio;
    }

    /**
     * @return the dateEntree
     */
    public Date getDateEntree() {
        return dateEntree;
    }

    /**
     * @param dateEntree the dateEntree to set
     */
    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    /**
     * @return the dateSortie
     */
    public Date getDateSortie() {
        return dateSortie;
    }

    /**
     * @param dateSortie the dateSortie to set
     */
    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    /**
     * @return the lettreDeSortie
     */
    public String getLettreDeSortie() {
        return lettreDeSortie;
    }

    /**
     * @param lettreDeSortie the lettreDeSortie to set
     */
    public void setLettreDeSortie(String lettreDeSortie) {
        this.lettreDeSortie = lettreDeSortie;
    }

    /**
     * @return the localisation
     */
    public Localisation getLocalisation() {
        return localisation;
    }

    /**
     * @param localisation the localisation to set
     */
    public void setLocalisation(Localisation localisation) {
        this.localisation = localisation;
    }

    
    // Fonctions a coder en dessous
    public void ajouterIdSejour(String idSejour){
        this.setIdSejour(idSejour);
    }
    
    public void ajouterPhReferant(PH phReferant){
        this.setPhReferant(phReferant);
    }
    
    public void ajouterLocalisation(Localisation localisation){
        this.setLocalisation(localisation);
    }
    
    public void ajouterPatient(Patient patient){
        this.setPatient(patient);
    }
            
    public void ajouterPrescription(String prescription) {
        this.listePrescriptions.add(prescription);
    }

    public void ajouterObservation(String observation) {
        this.listeObservations.add(observation);
    }

    public void ajouterCompteRendu(String compteRendu) {
        this.listeDeCompteRenduRadio.add(compteRendu);
    }

    public void ajouterResultat(String resultat) {
        this.listeDeResultats.add(resultat);
    }

    public void ajouterTitreOperation(String titreOperation) {
        this.listeTitreOperations.add(titreOperation);
    }

    public void ajouterDetailsOperation(String detailsOperation) {
        this.listeDetailsOperations.add(detailsOperation);
    }

    public void ajouterLettreDeSortie(String lettreDeSortie) {
        this.setLettreDeSortie(lettreDeSortie);
    }
    
      public boolean sejourEnCours(String idSejour){
           Boolean rep=true;
         try {
           
        
            String query = "select LETTRE_SORTIE from ph where ID_SEJOUR='"+idSejour+"'"; // la query à entrer pour accéder aux données de nos tables 
             System.out.println(query);
            rs = st.executeQuery(query);
            while (rs.next() && rep==true) {
                String lettreS = rs.getString("LETTRE_SORTIE");
                if (lettreS.isEmpty()){
                
             
                }
                else {rep=false;}
                System.out.println(lettreS);
                System.out.println(rep);
            }
        } catch (Exception ex) {
            System.out.println(ex);
           
        }
         
         return rep;
    }

    
    //Completer la base de données avec les informations, cette fonction est pour un PH clinique
    public void completerSejour(String idSejour, String idMed,String Ipp, String observations,String titreOperations,String detailsOperations,String resultats,String prescriptions){
       if(sejourEnCours(idSejour)) {
           String sql ="insert into ph (ID_PH,IPP_PATIENT,ID_SEJOUR,DATE_SAISIE,OBSERVATION,RESULTAT,LETTRE_SORTIE,PRESCRIPTION,TITRE_OPERATION,OPERATION,COMPTE_RENDU) values (?,?,?,?,?,?,?,?,?,?,?)";
       
        System.out.println(sql);
        try {
            PreparedStatement pstm = con.prepareStatement(sql);
           Date maDate;
            SimpleDateFormat maDateLongue;
            maDate= new Date();
             maDateLongue= new SimpleDateFormat("dd/MM/yyyy HH:mm");
            //on insere les donnees dans la classe ph_referent ce qui correspond a la requete 1
            pstm.setString(1, idMed);
            pstm.setString(2, Ipp);
             pstm.setString(3, idSejour);
              pstm.setString(4,maDateLongue.format(maDate));
             System.out.println(maDateLongue.format(maDate));
             pstm.setString(5, observations);
             System.out.println(observations);
             ajouterObservation(observations);
             pstm.setString(6, resultats);
             listeDeResultats.add(resultats);
             pstm.setString(7, "");
              
             
             pstm.setString(8, prescriptions);
             listePrescriptions.add(prescriptions);
             pstm.setString(9, titreOperations);
             listeTitreOperations.add(titreOperations);
              pstm.setString(10, detailsOperations);
             listeDetailsOperations.add(detailsOperations);
                 pstm.setString(11, "");
           pstm.executeUpdate();
            
            
        } catch (Exception ex) {
            System.out.println(ex);
           
        }}
       else {System.out.println("Le séjour est clos et non modifiable");}
    }


//Retroune l'id du dernier sejour crée pour le patient
    public String idSejourPatientSelection(String iPP){
        
        String idSejour ="";
        String dateLaPlusRecente="01/01/0001";
        String date="";
        //On recherche le sejour le plus recent
        try {
            String query = "select DATE_CREATION_SEJOUR from ph_referent where IPP='"+iPP+"'";
            System.out.println(query);
            rs = st.executeQuery(query);
            
            while (rs.next()) {
               date = rs.getString("DATE_CREATION_SEJOUR");
               if(date.compareTo(dateLaPlusRecente)>0){
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
            String query = "select ID_SEJOUR from ph_referent where IPP='"+iPP+"' and DATE_CREATION_SEJOUR='"+dateLaPlusRecente+"'";
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
    
}
