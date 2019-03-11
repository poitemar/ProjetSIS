/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import nf.Lit;
import nf.Orientation;
import nf.Patient;
import nf.Service;
import nf.Sexe;
import nf.Specialite;

/**
 *
 * @author Marine
 */
public class nouveauSejour extends javax.swing.JFrame {

    nf.SecretaireMedicale secretaireMedicaleCourante = new nf.SecretaireMedicale("null", "null", "null", "null", "null", Specialite.ONCOLOGIE, Service.CLINIQUE);
    ArrayList<nf.PH> listeMed = new ArrayList<nf.PH>();
     nf.Patient p = new nf.Patient("bluff", "bluff", "", Sexe.AUTRE, "bluff", "bluff", "bluff");
    nf.Patient patientConcerne;
    DefaultListModel DLM = new DefaultListModel();
    nf.PH PHSelectionne;
    nf.SecretaireAdministrative secrAdm = new nf.SecretaireAdministrative("null", "null", "null", "null", "null", Specialite.ACCUEIL, Service.ACCUEIL);
    nf.PersonnelMedical pm;
    /**
     * Creates new form nouveauSejour
     */
    public nouveauSejour(nf.PersonnelMedical perso) {
        initComponents();
        setSize(700, 600);
        //System.out.println(listePatient.getSelectedValue().toString());
       this.pm =perso;

        listeMed = secretaireMedicaleCourante.afficherListeMedecinParService(pm.getSpecialite());
        System.out.println(perso.getSpecialite().toString());
        for (int i = 0; i < listeMed.size(); i++) {
            String element = "" + listeMed.get(i).getNom() + "         " + listeMed.get(i).getPrenom() + "    ";
            DLM.addElement(element);
        }
        listeMedecinsService.setModel(DLM);
        listeMedecinsService.repaint();

        String[] liste2 = new String[secrAdm.nombrePatients()];
        liste2 = secrAdm.afficherListePatients();
        DefaultListModel DLM = new DefaultListModel();
        for (int i = 0; i < liste2.length; i++) {
            String element = liste2[i];
            DLM.addElement(element);

        }
        listePatient.setModel(DLM);
        listePatient.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listeMedecinsService = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        boxService = new javax.swing.JComboBox();
        boxOrientation = new javax.swing.JComboBox();
        boxLit = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        textEtage = new javax.swing.JTextField();
        textChambre = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listePatient = new javax.swing.JList<String>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 600));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("Enregistrement du patient");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel2.setText("Médecin référent");

        listeMedecinsService.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listeMedecinsServiceValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listeMedecinsService);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel3.setText("Localisation");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Service : ");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Orientation :");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Etage :");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Chambre :");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Lit : ");

        Specialite labels[] = Specialite.values();
        DefaultComboBoxModel model = new DefaultComboBoxModel(labels);
        boxService.setModel(model);
        boxService.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        boxService.setModel(model);

        Orientation labels2[] = Orientation.values();
        DefaultComboBoxModel model2 = new DefaultComboBoxModel(labels2);
        boxOrientation.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        boxOrientation.setModel(model2);

        Lit labels3[] = Lit.values();
        DefaultComboBoxModel model3 = new DefaultComboBoxModel(labels3);
        boxLit.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        boxLit.setModel(model3);

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonEnregistrerPatient(evt);
            }
        });

        listePatient.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listePatient);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel9.setText("Patient :");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dossMed_logo_1.PNG"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(boxService, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(boxOrientation, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(textEtage))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textChambre, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(boxLit, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton1))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(136, 136, 136))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(193, 193, 193)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(52, 52, 52)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boxLit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(boxService, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(boxOrientation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textEtage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(textChambre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
                .addComponent(jLabel10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void boutonEnregistrerPatient(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonEnregistrerPatient

        //récupération de la localisation du patient en fonction des données entrées par la Secretaire Médicale dans l'interface
        nf.Localisation localisationCourante = new nf.Localisation((nf.Specialite) boxService.getSelectedItem(), (nf.Orientation) boxOrientation.getSelectedItem(), Integer.parseInt(textEtage.getText()), Integer.parseInt(textChambre.getText()), (nf.Lit) boxLit.getSelectedItem());

        //attribution d'un nouvel identifiant de séjour dès l'enregistrement du patient dans le service
        String nouvel_ID_Sejour = new String();
        nouvel_ID_Sejour = secretaireMedicaleCourante.creationID_Sejour();
        String phSelection = listeMedecinsService.getSelectedValue().toString();
        
         String ipppatientConcerne =p.ippPatientListe(listePatient.getSelectedValue());
         System.out.println(listePatient.getSelectedValue());
        System.out.println(ipppatientConcerne);
      //   System.out.println(p.ippPatientListe(listePatient.getSelectedValue().toString()));
        secretaireMedicaleCourante.ajouterSejour(nouvel_ID_Sejour, ipppatientConcerne, secretaireMedicaleCourante.iPPMedecinListe(phSelection), localisationCourante);
        p = new Patient(ipppatientConcerne, p.getNom(), p.getPrenom(), localisationCourante);
        
        this.dispose();
    }//GEN-LAST:event_boutonEnregistrerPatient

    private void listeMedecinsServiceValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listeMedecinsServiceValueChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_listeMedecinsServiceValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(nouveauSejour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nouveauSejour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nouveauSejour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nouveauSejour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                nf.Patient p = new nf.Patient("bluff", "bluff");
                nf.PersonnelMedical perso = new nf.PersonnelMedical("null", "null", "null", "null", "null", Specialite.ANESTHESIE, Service.MEDICO_TECHNIQUE);
                new nouveauSejour(perso).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxLit;
    private javax.swing.JComboBox boxOrientation;
    private javax.swing.JComboBox boxService;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listeMedecinsService;
    private javax.swing.JList<String> listePatient;
    private javax.swing.JTextField textChambre;
    private javax.swing.JTextField textEtage;
    // End of variables declaration//GEN-END:variables
}
