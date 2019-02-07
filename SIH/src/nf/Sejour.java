/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf;

import java.util.Date;
import java.util.List;

/**
 *
 * @author poite
 */
public class Sejour {
    private String idSejour;
    private Patient patient;
    private PH phReferant;
    private List<String> listeObservations;
    private List<String> listePrescriptions;
    private List<String> listeTitreOperations;
    private List<String> listeDetailsOperations;
    private List<String> listeDeResultats;
    private List<String> listeDeCompteRenduRadio;
    private Date dateEntree;
    private Date dateSortie;
    private String lettreDeSortie;
    private Localisation localisation;

    // Constructeur Sejour
    // creation sejour implique un patient, un idSejour(auto), un phReferant, localisation
    public Sejour(String idSejour, Patient patient, PH phReferant, Localisation localisation) {
        this.idSejour = idSejour;
        this.patient = patient;
        this.phReferant = phReferant;
        this.localisation = localisation;
    }

    /*deuxième constructeur de Sejour pour rentrer les informations d'un séjour : prescriptions, observations, compte rendu,
     résultats, titre des opérations, détails des opérations, et une lettre de sortie marquant la fin du séjour */
    public Sejour(String prescription, String observation, String compteRendu, String resultat, String titreOperation,
            String detailsOperation, String lettreDeSortie) {
        this.listePrescriptions.add(prescription);
        this.listeObservations.add(observation);
        this.listeDeCompteRenduRadio.add(compteRendu);
        this.listeDeResultats.add(resultat);
        this.listeTitreOperations.add(titreOperation);
        this.listeDetailsOperations.add(detailsOperation);
        this.lettreDeSortie = lettreDeSortie;
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
    public List<String> getListePrescriptions() {
        return listePrescriptions;
    }

    /**
     * @param listePrescriptions the listePrescriptions to set
     */
    public void setListePrescriptions(List<String> listePrescriptions) {
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
    //
}
