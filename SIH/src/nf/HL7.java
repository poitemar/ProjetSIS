package nf;

import library.interfaces.ClientHL7;
import library.interfaces.MessageInterface;
import library.structure.groupe.messages.Message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public class HL7 {

    private static String hostServeur = "localhost";

    private MyServeurHL7 serveurHL7;
    private ClientHL7 clientHL7;
    private Patient patient;
    private Thread thread;
    private PropertyChangeSupport callback;

    private static int portServeur = 6517;

    public static final int ADMIT_PATIENT = 0;
    public static final int END_PAT = 1;
    public static final int TRANS_PAT = 2;

    public HL7() {
        patient = null;
        thread = null;
        callback = new PropertyChangeSupport(this);
    }

    public void ecouterMsg() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("CONNEXION OKK++++++++++++++++++++++++++");
                serveurHL7 = new MyServeurHL7();
                serveurHL7.connection(portServeur);
                serveurHL7.ecoute();

                String messageHL7 = serveurHL7.protocole();

                library.interfaces.Patient p = serveurHL7.getPatient();

                GregorianCalendar dateNaissance = new GregorianCalendar();
                dateNaissance.setTime(p.getBirth());

                patient = new Patient(p.getID().toString(), p.getFamillyName(), p.getFirstName(), dateNaissance.toString(), Sexe.valueOf(p.getSex()));
                Message message = serveurHL7.getMessage();
                serveurHL7.fermer();
                callback.firePropertyChange("New Hl7 message", null, patient);
            }
        });
        thread.setName("ServeurHL7");
        thread.start();
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        callback.addPropertyChangeListener(propertyChangeListener);
    }

    public void deconnection() {
        serveurHL7.fermer();
    }

    public Patient getPatient() {
        return this.patient;
    }

    public String sendMessage(Patient patientEnvoi, int type) {
//       try{ 
        clientHL7 = new ClientHL7();
        clientHL7.connexion(hostServeur, portServeur);
        
        library.interfaces.Patient patientHL7 = new library.interfaces.Patient(Integer.parseInt(patientEnvoi.getipp()), patientEnvoi.getNom(), 'N');
        patientHL7.setFirstName(patientEnvoi.getPrenom());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dateN = new Date();
        String dateNS = "";
        try {
            dateNS = patientEnvoi.getDateDeNaissance();

            dateN = format.parse(dateNS);
        } catch (Exception ex) {

        }
        patientHL7.setBirth(dateN);
        //patientHL7.setSex('M');
        //patientHL7.setDeath(false);

        if (type == ADMIT_PATIENT) {
            clientHL7.admit(patientHL7);
        } else if (type == END_PAT) {
            clientHL7.endPat(patientHL7);
        } else {
            clientHL7.transPat(patientHL7);
        }

        MessageInterface accuse = clientHL7.getMsg();
        clientHL7.close();
        return "ok";

    }

    public void setPortSeveur(int portSeveur) {
        HL7.portServeur = portSeveur;
    }
}
